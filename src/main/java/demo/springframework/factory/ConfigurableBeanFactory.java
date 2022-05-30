package demo.springframework.factory;

import demo.springframework.factory.config.BeanPostProcessor;

public interface ConfigurableBeanFactory extends HierarchicalBeanFactory{
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor value);
}
