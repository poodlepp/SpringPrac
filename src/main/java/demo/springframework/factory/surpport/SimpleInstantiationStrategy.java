package demo.springframework.factory.surpport;

import demo.springframework.BeansException;
import demo.springframework.factory.config.BeanDenition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SimpleInstantiationStrategy implements InstantiationStrategy {
    @Override
    public Object instantiate(BeanDenition beanDenition, String beanName, Constructor constructor, Object[] args) throws BeansException {
        final Class clazz = beanDenition.getClazz();

        try {
            if (null != constructor) {
                return clazz.getDeclaredConstructor(constructor.getParameterTypes()).newInstance(args);
            } else {
                return clazz.getDeclaredConstructor().newInstance();
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new BeansException("Failed to instantiate [" + clazz.getName()+"]");
        }
    }
}
