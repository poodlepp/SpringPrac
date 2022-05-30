package demo.springframework.context.support;

import demo.springframework.BeansException;
import demo.springframework.context.ConfigurableApplicationContext;
import demo.springframework.core.io.DefaultResourceLoader;
import demo.springframework.factory.ConfigurableListableBeanFactory;
import demo.springframework.factory.config.BeanFactoryPostProcessor;
import demo.springframework.factory.config.BeanPostProcessor;

import java.util.Map;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    @Override
    public void refresh() throws BeansException {
        refreshBeanFactory();
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        invokeBeanFactoryPostProcessor(beanFactory);
        registerBeanPostProcessor(beanFactory);
        beanFactory.preInstantiateSingletons();
    }

    private void registerBeanPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        final Map<String, BeanPostProcessor> processors = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor value : processors.values()) {
            beanFactory.addBeanPostProcessor(value);
        }
    }

    private void invokeBeanFactoryPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        final Map<String, BeanFactoryPostProcessor> beansOfType = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        beansOfType.forEach((String,processor) -> {
            processor.postProcessBeanFactory(beanFactory);
        });
    }

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    protected abstract void refreshBeanFactory();

    @Override
    public Object getBean(String name) {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name,args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) {
        return getBeanFactory().getBean(name,requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public void registerShutdownHook(){
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close(){
        getBeanFactory().destroySingletons();
    }
}
