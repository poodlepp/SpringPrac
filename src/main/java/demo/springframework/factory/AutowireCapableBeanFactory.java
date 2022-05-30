package demo.springframework.factory;

import demo.springframework.BeansException;

public interface AutowireCapableBeanFactory extends BeanFactory{
    Object applyBeanPostProcessorBeforeInitialization(Object existingBean,String beanName) throws BeansException;
    Object applyBeanPostProcessorAfterInitialization(Object existingBean,String beanName) throws BeansException;

}
