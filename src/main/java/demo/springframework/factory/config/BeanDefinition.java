package demo.springframework.factory.config;

import demo.springframework.beans.PropertyValues;

public class BeanDefinition {
    private Class clazz;

    private PropertyValues propertyValues;

    public BeanDefinition(Class clazz){
        this.clazz = clazz;
        this.propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class clazz, PropertyValues propertyValues) {
        this.clazz = clazz;
        this.propertyValues = propertyValues;
    }

    public Class getClazz() {
        return this.clazz;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }
}
