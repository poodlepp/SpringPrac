package demo.springframework.factory.config;

import demo.springframework.BeansException;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{
    Object postProcessBeforeInstantiation(Class<?> beanClass,String beanName) throws BeansException;
}
