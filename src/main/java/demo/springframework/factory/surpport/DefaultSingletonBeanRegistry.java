package demo.springframework.factory.surpport;

import demo.springframework.BeansException;
import demo.springframework.factory.DisposableBean;
import demo.springframework.factory.ObjectFactory;
import demo.springframework.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    protected static final Object NULL_OBJECT = new Object();
    //一级缓存，普通对象
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<>();
    //二级缓存，提前暴露对象，没有完全实例化的对象
    protected final Map<String,Object> earlySingletonObjects = new HashMap<String,Object>();
    //三级缓存，存放代理对象
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<String,ObjectFactory<?>>();
    private final Map<String, DisposableBean> disposableBeans = new LinkedHashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        Object singletonObject = singletonObjects.get(beanName);
        if(null == singletonObject){
            singletonObject = earlySingletonObjects.get(beanName);
            if(null == singletonObject){
                ObjectFactory singletonFactory = singletonFactories.get(beanName);
                if(null != singletonFactory){
                    singletonObject = singletonFactory.getObject();
                    earlySingletonObjects.put(beanName,singletonObject);
                    singletonObjects.remove(beanName);
                }
            }
        }
        return singletonObject;
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
        earlySingletonObjects.remove(beanName,singletonObject);
        singletonFactories.remove(beanName);
    }

    protected void addSingletonFactory(String beanName,ObjectFactory<?> singleFactory){
        if(!this.singletonObjects.containsKey(beanName)){
            this.singletonFactories.put(beanName,singleFactory);
            this.earlySingletonObjects.remove(beanName);
        }
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
