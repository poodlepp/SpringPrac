package demo.springframework.factory.surpport;

import demo.springframework.factory.BeanFactory;
import demo.springframework.factory.config.BeanDenition;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    @Override
    public Object getBean(String name) {
        final Object singleton = getSingleton(name);
        if(singleton != null){
            return singleton;
        }

        BeanDenition beanDenition = getBeanDefinition(name);
        Object bean = createBean(name,beanDenition);
        addSingleton(name,bean);
        return bean;
    }

    protected abstract Object createBean(String name, BeanDenition beanDenition);

    protected abstract BeanDenition getBeanDefinition(String name);
}
