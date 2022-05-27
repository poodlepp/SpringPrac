package demo.springframework.factory.surpport;

import demo.springframework.BeansException;
import demo.springframework.factory.config.BeanDenition;

import java.lang.reflect.Constructor;
import java.util.function.Consumer;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{
    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    public Object createBean(String name, BeanDenition beanDenition,Object[] args) {
        //            final Object bean = beanDenition.getClazz().newInstance();
        final Object bean = createBeanInstance(beanDenition,name,args);
        addSingleton(name,bean);
        return bean;

    }

    private Object createBeanInstance(BeanDenition beanDenition, String name, Object[] args) {
        Constructor constructor = null;
        final Class clazz = beanDenition.getClazz();
        final Constructor[] declaredConstructors = clazz.getDeclaredConstructors();
        for (Constructor declaredConstructor : declaredConstructors) {
            if(null != args && declaredConstructor.getParameterTypes().length == args.length){
                constructor = declaredConstructor;
                break;
            }
        }
        return getInstantiationStrategy().instantiate(beanDenition,name,constructor,args);
    }



}
