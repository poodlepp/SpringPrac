package demo.springframework.factory.surpport;

import demo.springframework.factory.config.BeanDenition;

public interface BeanDefinitionRegistry {
    void registerBeanDefinition(String beanName, BeanDenition beanDenition);
}
