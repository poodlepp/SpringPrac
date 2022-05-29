package demo.springframework.factory.surpport;

import demo.springframework.BeansException;
import demo.springframework.factory.config.BeanDefinition;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;

public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {
    @Override
    public Object instantiate(BeanDefinition beanDenition, String beanName, Constructor constructor, Object[] args) throws BeansException {
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDenition.getClazz());
        enhancer.setCallback(new NoOp() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });

        if (null == constructor) return enhancer.create();
        return enhancer.create(constructor.getParameterTypes(), args);
    }
}
