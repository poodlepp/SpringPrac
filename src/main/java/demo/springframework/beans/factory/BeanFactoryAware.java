package demo.springframework.beans.factory;

import demo.springframework.BeansException;
import demo.springframework.factory.BeanFactory;

public interface BeanFactoryAware extends Aware{
    /**
     * 实现此接口，即能感知到所属的beanFactory
     * @param beanFactory
     * @throws BeansException
     */
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
