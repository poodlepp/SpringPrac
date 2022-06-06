package demo.springframework.factory.surpport;

import demo.springframework.BeansException;
import demo.springframework.factory.DisposableBean;
import demo.springframework.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    protected static final Object NULL_OBJECT = new Object();
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<>();
    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

    public void registerDisposableBean(String beanName, DisposableBeanAdapter disposableBeanAdapter) {
        disposableBeans.put(beanName, disposableBeanAdapter);
    }

    public void destroySingletons() {
        try {
            for (DisposableBean value : disposableBeans.values()) {
                value.destroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BeansException("destroy method fail");
        }
    }
}
