package demo.springframework.beans.factory.surpport;

import demo.springframework.BeansException;
import demo.springframework.beans.factory.FactoryBean;
import demo.springframework.factory.surpport.DefaultSingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FactoryBeanRegistrySurpport extends DefaultSingletonBeanRegistry {
    private final Map<String,Object> factoryBeanObjectCache = new ConcurrentHashMap<String,Object>();

    protected Object getCachedObjectFromFactoryBean(String beanName){
        Object o = this.factoryBeanObjectCache.get(beanName);
        return (o != NULL_OBJECT ? o : null);
    }

    protected Object getObjectFromFactoryBean(FactoryBean factoryBean,String beanName){
        if(factoryBean.isSingleton()){
            Object object = this.factoryBeanObjectCache.get(beanName);
            if(object == null){
                object = doGetObjectFromFactoryBean(factoryBean,beanName);
                this.factoryBeanObjectCache.put(beanName,(object != null ? object : NULL_OBJECT));
            }
            return (object != NULL_OBJECT ? object : null);
        }else{
            return doGetObjectFromFactoryBean(factoryBean,beanName);
        }
    }

    protected Object doGetObjectFromFactoryBean(FactoryBean factoryBean, String beanName) {
        try {
            return factoryBean.getObject();
        } catch (Exception e) {
            throw new BeansException("factorybean throw ex  beanname:" + beanName + "  ex msg:",e);
        }
    }
}
