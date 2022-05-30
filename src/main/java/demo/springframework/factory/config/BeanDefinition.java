package demo.springframework.factory.config;

import demo.springframework.beans.PropertyValues;
import lombok.Data;

@Data
public class BeanDefinition {
    private Class clazz;

    private PropertyValues propertyValues;

    private String initMethodName;
    private String destroyMethodName;

    public BeanDefinition(Class clazz){
        this.clazz = clazz;
        this.propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class clazz, PropertyValues propertyValues) {
        this.clazz = clazz;
        this.propertyValues = propertyValues;
    }
}
