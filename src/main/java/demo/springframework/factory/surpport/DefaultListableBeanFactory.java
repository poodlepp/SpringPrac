package demo.springframework.factory.surpport;

import demo.springframework.BeansException;
import demo.springframework.factory.ConfigurableListableBeanFactory;
import demo.springframework.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

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
    public void preInstantiateSingletons() {
        for (String beanName : beanDefinitionMap.keySet()) {
            getBean(beanName);
        }
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDenition){
        beanDefinitionMap.put(name,beanDenition);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        Map<String,T> result = new HashMap<>();
        beanDefinitionMap.forEach((beanName,beanDefinition) -> {
            final Class clazz = beanDefinition.getClazz();
            if(type.isAssignableFrom(clazz)){
                result.put(beanName, (T) getBean(beanName));
            }
        });
        return result;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }
}
