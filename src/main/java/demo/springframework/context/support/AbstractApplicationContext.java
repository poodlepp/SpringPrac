package demo.springframework.context.support;

import demo.springframework.BeansException;
import demo.springframework.context.ApplicationEvent;
import demo.springframework.context.ApplicationListener;
import demo.springframework.context.ConfigurableApplicationContext;
import demo.springframework.context.event.*;
import demo.springframework.core.io.DefaultResourceLoader;
import demo.springframework.factory.ConfigurableListableBeanFactory;
import demo.springframework.factory.config.BeanFactoryPostProcessor;
import demo.springframework.factory.config.BeanPostProcessor;

import java.util.Collection;
import java.util.Map;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";
    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void refresh() throws BeansException {
        refreshBeanFactory();
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
        invokeBeanFactoryPostProcessor(beanFactory);
        registerBeanPostProcessor(beanFactory);

        // 注册事件发布者
        initApplicationEventMulticaster();
        // 注册事件监听器
        registerListeners();

        beanFactory.preInstantiateSingletons();
        // 发布容器刷新完成事件
        finishRefresh();

    }

    private void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
    }

    private void registerListeners() {
        Collection<ApplicationListener> listeners = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener listener : listeners) {
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME,applicationEventMulticaster);
    }

    private void registerBeanPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        final Map<String, BeanPostProcessor> processors = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor value : processors.values()) {
            beanFactory.addBeanPostProcessor(value);
        }
    }

    private void invokeBeanFactoryPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        final Map<String, BeanFactoryPostProcessor> beansOfType = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        beansOfType.forEach((string,processor) -> {
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
        publishEvent(new ContextClosedEvent(this));
        getBeanFactory().destroySingletons();
    }
}
