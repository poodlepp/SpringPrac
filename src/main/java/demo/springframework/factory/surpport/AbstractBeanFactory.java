package demo.springframework.factory.surpport;

import demo.springframework.factory.BeanFactory;
import demo.springframework.factory.config.BeanDenition;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    @Override
    public Object getBean(String name) {
        return getObject(name, null);
    }

    @Override
    public Object getBean(String name,Object... args) {
        return getObject(name, args);
    }

    private Object getObject(String name, Object[] args) {
        final Object singleton = getSingleton(name);
        if(singleton != null){
            return singleton;
        }

        BeanDenition beanDenition = getBeanDefinition(name);
        Object bean = createBean(name,beanDenition, args);
        addSingleton(name,bean);
        return bean;
    }

    protected abstract Object createBean(String name, BeanDenition beanDenition,Object[] args);

    protected abstract BeanDenition getBeanDefinition(String name);
}
