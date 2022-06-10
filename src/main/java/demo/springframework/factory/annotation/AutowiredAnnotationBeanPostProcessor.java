package demo.springframework.factory.annotation;

import cn.hutool.core.bean.BeanUtil;
import demo.springframework.BeansException;
import demo.springframework.beans.PropertyValues;
import demo.springframework.beans.factory.BeanFactoryAware;
import demo.springframework.factory.BeanFactory;
import demo.springframework.factory.ConfigurableListableBeanFactory;
import demo.springframework.factory.config.InstantiationAwareBeanPostProcessor;
import demo.springframework.util.ClassUtils;

import java.lang.reflect.Field;

public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    /**
     * 属性配置
     * @param propertyValues
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues propertyValues, Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Value annotation = declaredField.getAnnotation(Value.class);
            if(annotation != null){
                String value = annotation.value();
                String result = beanFactory.resolveEmbeddedValue(value);
                BeanUtil.setFieldValue(bean,declaredField.getName(),result);
            }
        }

        for (Field declaredField : declaredFields) {
            Autowired annotation = declaredField.getAnnotation(Autowired.class);
            if(annotation != null){
                Qualifier qualifier = declaredField.getAnnotation(Qualifier.class);
                if(qualifier != null){
                    BeanUtil.setFieldValue(bean,declaredField.getName(),beanFactory.getBean(qualifier.value(),declaredField.getType()));
                }else{
                    BeanUtil.setFieldValue(bean,declaredField.getName(),beanFactory.getBean(declaredField.getType()));
                }

            }
        }
        return propertyValues;
    }
}
