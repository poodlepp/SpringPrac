package demo.springframework.factory.config;

import demo.springframework.BeansException;

public interface BeanPostProcessor {
    Object postProcessBeforeInitialization(Object bean,String beanName) throws BeansException;
    Object postProcessAfterInitialization(Object bean,String beanName) throws BeansException;
}
