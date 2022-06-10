package demo.springframework.factory;

import demo.springframework.BeansException;

public interface BeanFactory {
    Object getBean(String name);
    Object getBean(String name,Object... args) throws BeansException;
    <T> T getBean(String name , Class<T> requiredType);
    <T> T getBean(Class<T> requiredType);
}
