package demo.springframework.factory.surpport;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import demo.springframework.BeansException;
import demo.springframework.beans.BeanReference;
import demo.springframework.beans.PropertyValue;
import demo.springframework.beans.PropertyValues;
import demo.springframework.beans.factory.Aware;
import demo.springframework.beans.factory.BeanClassLoaderAware;
import demo.springframework.beans.factory.BeanFactoryAware;
import demo.springframework.beans.factory.BeanNameAware;
import demo.springframework.factory.AutowireCapableBeanFactory;
import demo.springframework.factory.DisposableBean;
import demo.springframework.factory.InitializingBean;
import demo.springframework.factory.config.BeanDefinition;
import demo.springframework.factory.config.BeanPostProcessor;
import demo.springframework.factory.config.InstantiationAwareBeanPostProcessor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    /**
     * 目前发现如果cglib逻辑叠加 cglib+cglib invocationException => null
     */
    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    @Override
    public Object createBean(String name, BeanDefinition beanDenition, Object[] args) {
        Object bean = resolveBeforeInstantiation(name,beanDenition);
        if(null != bean){
            return bean;
        }
        return doCreateBean(name,beanDenition,args);
    }

    private Object doCreateBean(String name, BeanDefinition beanDenition, Object[] args) {
        Object bean = null;
        try {
            //实例化bean
            bean = createBeanInstance(beanDenition,name,args);
            if(beanDenition.isSingleton()){
                Object finalBean = bean;
                addSingletonFactory(name,() -> getEarlyBeanReference(name,beanDenition,finalBean));
            }

            //某些实例不需要进行
            boolean isContinue = applybeanPostProcessorsAfterInstantiation(name,bean);
            if(!isContinue){
                return bean;
            }

            //beanPostProcessor 修改属性值
            applyBeanPostProcessorBeforeApplyingPropertyValues(name,bean,beanDenition);

            //填充属性（目前的实现  property优先级高于@value）
            appPropertyValues(name,bean,beanDenition);

            //执行初始化方法 以及beanPostProcessor 前置+后置方法
            bean = initializeBean(name,bean,beanDenition);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BeansException("instantiation of bean fail",e);
        }
        //注册disposableBeans
        registerDisposableBeanIfNecessary(name,bean,beanDenition);

        //初始化单例对象
        Object exposedObject = bean;
        if(beanDenition.isSingleton()){
            exposedObject = getSingleton(name);
            registerSingleton(name,exposedObject);
        }
        return bean;
    }

    private Object getEarlyBeanReference(String name, BeanDefinition beanDenition, Object bean) {
        Object exposedObject = bean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if(beanPostProcessor instanceof InstantiationAwareBeanPostProcessor){
                exposedObject = ((InstantiationAwareBeanPostProcessor)beanPostProcessor).getEarlyBeanReference(exposedObject,name);
                if(null == exposedObject){
                    return exposedObject;
                }
            }
        }
        return exposedObject;
    }

    private void applyBeanPostProcessorBeforeApplyingPropertyValues(String name, Object bean, BeanDefinition beanDenition) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if(beanPostProcessor instanceof InstantiationAwareBeanPostProcessor){
                PropertyValues propertyValues = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessPropertyValues(beanDenition.getPropertyValues(), bean, name);
                if(null != propertyValues){
                    for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                        beanDenition.getPropertyValues().addPropertyValue(propertyValue);
                    }
                }
            }
        }
    }


    private boolean applybeanPostProcessorsAfterInstantiation(String name, Object bean) {
        boolean isContinue = true;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if(beanPostProcessor instanceof InstantiationAwareBeanPostProcessor){
                InstantiationAwareBeanPostProcessor instantiationAware = (InstantiationAwareBeanPostProcessor) beanPostProcessor;
                if(!instantiationAware.postProcessAfterInstantiation(bean, name)){
                    isContinue = false;
                    return isContinue;
                }
            }
        }
        return isContinue;
    }

    /**
     * 当前逻辑，只要走代理逻辑，就不能走正常初始化逻辑，不完美
     * @param name
     * @param beanDenition
     * @return
     */
    private Object resolveBeforeInstantiation(String name, BeanDefinition beanDenition) {
        Object bean = applyBeanPostProcessorBeforeinstantiation(beanDenition.getClazz(),name);
        if(null != bean){
            bean = applyBeanPostProcessorAfterInitialization(bean,name);
        }
        return bean;
    }

    /**
     * 创建代理，但是现在有局限性，只能支持一层
     * @param clazz
     * @param name
     * @return
     */
    private Object applyBeanPostProcessorBeforeinstantiation(Class clazz, String name) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if(beanPostProcessor instanceof InstantiationAwareBeanPostProcessor){
                Object result = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessBeforeInstantiation(clazz, name);
                if(null != result) {
                    return result;
                }
            }
        }
        return null;
    }

    protected void registerDisposableBeanIfNecessary(String name, Object bean, BeanDefinition beanDenition) {
        if(beanDenition.isSingleton()){
            return;
        }
        if(bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDenition.getDestroyMethodName())){
            registerDisposableBean(name,new DisposableBeanAdapter(bean,name,beanDenition));
        }
    }

    private Object initializeBean(String name, Object bean, BeanDefinition beanDenition) throws Exception {
        if(bean instanceof Aware){
            if(bean instanceof BeanFactoryAware){
                ((BeanFactoryAware)bean).setBeanFactory(this);
            }
            if(bean instanceof BeanClassLoaderAware){
                ((BeanClassLoaderAware)bean).setBeanClassLoader(getBeanClassLoader());
            }
            if(bean instanceof BeanNameAware){
                ((BeanNameAware)bean).setBeanName(name);
            }
        }

        Object wrappedBean = applyBeanPostProcessorBeforeInitialization(bean, name);
        invokeinitMethods(name,wrappedBean,beanDenition);
        wrappedBean = applyBeanPostProcessorAfterInitialization(bean,name);
        return wrappedBean;
    }

    private void invokeinitMethods(String name, Object bean, BeanDefinition beanDenition) throws Exception {
        if(bean instanceof InitializingBean){
            ((InitializingBean)bean).afterPropertiesSet();
        }

        final String initMethodName = beanDenition.getInitMethodName();
        if(StrUtil.isNotEmpty(initMethodName)){
            final Method method = beanDenition.getClazz().getMethod(initMethodName);
            if(null == method){
                throw new BeansException("could not find an init method");
            }
            method.invoke(bean);
        }
    }

    private void appPropertyValues(String name, Object bean, BeanDefinition beanDenition) {
        final PropertyValues propertyValues = beanDenition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
            final String pname = propertyValue.getName();
            Object pvalue = propertyValue.getValue();
            if(pvalue instanceof BeanReference){
                BeanReference beanReference = (BeanReference) pvalue;
                pvalue = getBean(beanReference.getName());
            }
            BeanUtil.setFieldValue(bean,pname,pvalue);
        }
    }

    private Object createBeanInstance(BeanDefinition beanDenition, String name, Object[] args) {
        Constructor constructor = null;
        final Class clazz = beanDenition.getClazz();
        final Constructor[] declaredConstructors = clazz.getDeclaredConstructors();
        for (Constructor declaredConstructor : declaredConstructors) {
            if(null != args && declaredConstructor.getParameterTypes().length == args.length){
                constructor = declaredConstructor;
                break;
            }
        }
        return getInstantiationStrategy().instantiate(beanDenition,name,constructor,args);
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    @Override
    public Object applyBeanPostProcessorBeforeInitialization(Object existingBean,String beanName) throws BeansException{
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessBeforeInitialization(result,beanName);
            if(null == current) return result;
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorAfterInitialization(Object existingBean,String beanName) throws BeansException {
         Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            final Object current = beanPostProcessor.postProcessAfterInitialization(result, beanName);
            if(null == current){
                return result;
            }
            result = current;
        }
        return result;
    }
}
