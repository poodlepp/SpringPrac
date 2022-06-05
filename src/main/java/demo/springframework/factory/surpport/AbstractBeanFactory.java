package demo.springframework.factory.surpport;

import demo.springframework.beans.factory.FactoryBean;
import demo.springframework.beans.factory.surpport.FactoryBeanRegistrySurpport;
import demo.springframework.factory.ConfigurableBeanFactory;
import demo.springframework.factory.config.BeanDefinition;
import demo.springframework.factory.config.BeanPostProcessor;
import demo.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBeanFactory extends FactoryBeanRegistrySurpport implements ConfigurableBeanFactory {
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();
    private ClassLoader beanClassLoader = ClassUtils.getClassLoader();

    @Override
    public Object getBean(String name) {
        return doGetBean(name,null);
    }

    @Override
    public Object getBean(String name,Object... args) {
        return doGetBean(name,args);
    }

    public <T> T getBean(String name,Class<T> requiredType){
        return (T)getBean(name);
    }

    private Object getObject(String name, Object[] args) {
        final Object singleton = getSingleton(name);
        if(singleton != null){
            return singleton;
        }

        BeanDefinition beanDenition = getBeanDefinition(name);
        Object bean = createBean(name,beanDenition, args);
        addSingleton(name,bean);
        return bean;
    }

    /**
     * factorybean专用
     */
    protected <T> T doGetBean(final String name, Object[] args){
        Object singleton = getSingleton(name);
        if(singleton != null){
            return (T)getObjectForBeanInstance(singleton,name);
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        Object bean = createBean(name, beanDefinition, args);
        return (T)getObjectForBeanInstance(bean,name);
    }

    private Object getObjectForBeanInstance(Object bean, String name) {
        if(!(bean instanceof FactoryBean)){
            return bean;
        }

        Object object = getCachedObjectFromFactoryBean(name);
        if(object == null){
            object = getObjectFromFactoryBean((FactoryBean) bean, name);
        }
        return object;
    }

    protected abstract Object createBean(String name, BeanDefinition beanDenition, Object[] args);

    protected abstract BeanDefinition getBeanDefinition(String name);

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }
}
