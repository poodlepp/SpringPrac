package demo.springframework.factory;

import demo.springframework.BeansException;

public interface ObjectFactory<T> {
    T getObject() throws BeansException;
}
