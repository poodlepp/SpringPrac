package demo.springframework.factory;

import demo.springframework.factory.config.BeanDefinition;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory,ConfigurableBeanFactory,AutowireCapableBeanFactory{
    BeanDefinition getBeanDefinition(String beanName);

    void preInstantiateSingletons();
}
