package demo.springframework.factory.surpport;

import demo.springframework.BeansException;
import demo.springframework.factory.config.BeanDenition;
import demo.springframework.factory.surpport.AbstractAutowireCapableBeanFactory;
import demo.springframework.factory.surpport.BeanDefinitionRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {

    private Map<String, BeanDenition> beanDenitionMap = new ConcurrentHashMap<>();

    @Override
    public BeanDenition getBeanDefinition(String name) {
        final BeanDenition beanDenition = beanDenitionMap.get(name);
        if(beanDenition == null){
            throw new BeansException("no expect beanDefinition");
        }
        return beanDenition;
    }

    @Override
    public void registerBeanDefinition(String name,BeanDenition beanDenition){
        beanDenitionMap.put(name,beanDenition);
    }
}
