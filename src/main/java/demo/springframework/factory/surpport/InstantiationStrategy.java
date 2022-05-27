package demo.springframework.factory.surpport;

import demo.springframework.BeansException;
import demo.springframework.factory.config.BeanDenition;

import java.lang.reflect.Constructor;

public interface InstantiationStrategy {
    Object instantiate(BeanDenition beanDenition, String beanName, Constructor constructor,Object[] args) throws BeansException;
}
