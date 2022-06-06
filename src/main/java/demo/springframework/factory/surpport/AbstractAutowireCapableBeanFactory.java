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

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    public Object createBean(String name, BeanDefinition beanDenition, Object[] args) {
        //            final Object bean = beanDenition.getClazz().newInstance();
        Object bean = null;
        try {
            bean = createBeanInstance(beanDenition,name,args);
            appPropertyValues(name,bean,beanDenition);
            bean = initializeBean(name,bean,beanDenition);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BeansException("instantiation of bean fail",e);
        }
        registerDisposableBeanIfNecessary(name,bean,beanDenition);
        if(beanDenition.isSingleton()){
            registerSingleton(name,bean);
        }
        return bean;
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
