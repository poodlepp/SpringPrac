package demo.springframework.factory.surpport;

import demo.springframework.BeansException;
import demo.springframework.factory.config.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        final BeanDefinition beanDenition = beanDefinitionMap.get(name);
        if(beanDenition == null){
            throw new BeansException("no expect beanDefinition");
        }
        return beanDenition;
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDenition){
        beanDefinitionMap.put(name,beanDenition);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }
}
