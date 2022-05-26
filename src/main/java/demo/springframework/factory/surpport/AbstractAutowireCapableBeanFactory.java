package demo.springframework.factory.surpport;

import demo.springframework.BeansException;
import demo.springframework.factory.config.BeanDenition;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{
    @Override
    public Object createBean(String name, BeanDenition beanDenition) {
        try {
            final Object bean = beanDenition.getClazz().newInstance();
            addSingleton(name,bean);
            return bean;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new BeansException("bean init fail.",e);
        }

    }

}
