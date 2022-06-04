package demo.springframework.factory.surpport;

import demo.springframework.factory.ConfigurableBeanFactory;
import demo.springframework.factory.config.BeanDefinition;
import demo.springframework.factory.config.BeanPostProcessor;
import demo.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();
    private ClassLoader beanClassLoader = ClassUtils.getClassLoader();

    @Override
    public Object getBean(String name) {
        return getObject(name, null);
    }

    @Override
    public Object getBean(String name,Object... args) {
        return getObject(name, args);
    }

    public <T> T getBean(String name,Class<T> requiredType){
        return (T)getBean(name);
    }

    private Object getObject(String name, Object[] args) {
        final Object singleton = getSingleton(name);
        if(singleton != null){
            return singleton;
        }

        BeanDefinition beanDenition = getBeanDefinition(name);
        Object bean = createBean(name,beanDenition, args);
        addSingleton(name,bean);
        return bean;
    }

    protected abstract Object createBean(String name, BeanDefinition beanDenition, Object[] args);

    protected abstract BeanDefinition getBeanDefinition(String name);

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }
}
