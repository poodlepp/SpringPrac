package demo.springframework.factory.surpport;

import cn.hutool.core.bean.BeanUtil;
import demo.springframework.BeansException;
import demo.springframework.beans.BeanReference;
import demo.springframework.beans.PropertyValue;
import demo.springframework.beans.PropertyValues;
import demo.springframework.factory.config.BeanDenition;

import java.lang.reflect.Constructor;
import java.util.function.Consumer;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{
    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    public Object createBean(String name, BeanDenition beanDenition,Object[] args) {
        //            final Object bean = beanDenition.getClazz().newInstance();
        final Object bean = createBeanInstance(beanDenition,name,args);
        appPropertyValues(name,bean,beanDenition);
        addSingleton(name,bean);
        return bean;

    }

    private void appPropertyValues(String name, Object bean, BeanDenition beanDenition) {
        final PropertyValues propertyValues = beanDenition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
            final String pname = propertyValue.getName();
            Object pvalue = propertyValue.getValue();
            if(pvalue instanceof BeanReference){
                BeanReference beanReference = (BeanReference) pvalue;
                pvalue = getBean(beanReference.getName());
            }
            BeanUtil.setFieldValue(bean,pname,pvalue);
        }
    }

    private Object createBeanInstance(BeanDenition beanDenition, String name, Object[] args) {
        Constructor constructor = null;
        final Class clazz = beanDenition.getClazz();
        final Constructor[] declaredConstructors = clazz.getDeclaredConstructors();
        for (Constructor declaredConstructor : declaredConstructors) {
            if(null != args && declaredConstructor.getParameterTypes().length == args.length){
                constructor = declaredConstructor;
                break;
            }
        }
        return getInstantiationStrategy().instantiate(beanDenition,name,constructor,args);
    }



}
