package demo.springframework.factory;

import demo.springframework.factory.config.BeanPostProcessor;
import demo.springframework.factory.config.SingletonBeanRegistry;
import demo.springframework.util.StringValueResolver;

public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor value);

    void destroySingletons();

    void addEmbeddedValueResolver(StringValueResolver valueResolver);
    String resolveEmbeddedValue(String value);
}
