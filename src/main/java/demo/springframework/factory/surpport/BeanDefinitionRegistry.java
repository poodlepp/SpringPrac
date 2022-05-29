package demo.springframework.factory.surpport;

import demo.springframework.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {
    void registerBeanDefinition(String beanName, BeanDefinition beanDenition);
    boolean containsBeanDefinition(String beanName);
}
