package demo.springframework.context;

import demo.springframework.BeansException;

public interface ConfigurableApplicationContext extends ApplicationContext{
    void refresh() throws BeansException;
    void registerShutdownHook();
    void close();
}
