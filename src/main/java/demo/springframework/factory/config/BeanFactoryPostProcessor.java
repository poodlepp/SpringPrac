package demo.springframework.factory.config;

import demo.springframework.BeansException;
import demo.springframework.factory.ConfigurableListableBeanFactory;

public interface BeanFactoryPostProcessor {
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
