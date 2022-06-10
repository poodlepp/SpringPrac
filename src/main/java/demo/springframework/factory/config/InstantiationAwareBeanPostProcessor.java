package demo.springframework.factory.config;

import demo.springframework.BeansException;
import demo.springframework.beans.PropertyValue;
import demo.springframework.beans.PropertyValues;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{
    Object postProcessBeforeInstantiation(Class<?> beanClass,String beanName) throws BeansException;
    boolean postProcessAfterInstantiation(Object bean,String beanName) throws BeansException;
     PropertyValues postProcessPropertyValues(PropertyValues propertyValues,Object bean,String beanName) throws BeansException;

}
