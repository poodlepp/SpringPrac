package demo.springframework.factory.surpport.common;

import demo.springframework.BeansException;
import demo.springframework.beans.PropertyValue;
import demo.springframework.beans.PropertyValues;
import demo.springframework.factory.ConfigurableListableBeanFactory;
import demo.springframework.factory.config.BeanDefinition;
import demo.springframework.factory.config.BeanFactoryPostProcessor;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        final BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        final PropertyValues propertyValues = beanDefinition.getPropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("company","bytedance."));
    }
}
