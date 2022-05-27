package demo.springframework.factory.config;

import demo.springframework.beans.PropertyValues;

public class BeanDenition {
    private Class clazz;

    private PropertyValues propertyValues;

    public BeanDenition(Class clazz){
        this.clazz = clazz;
        this.propertyValues = new PropertyValues();
    }

    public BeanDenition(Class clazz, PropertyValues propertyValues) {
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
