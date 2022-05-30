package demo.springframework.factory.surpport;

import cn.hutool.core.util.StrUtil;
import demo.springframework.BeansException;
import demo.springframework.factory.DisposableBean;
import demo.springframework.factory.config.BeanDefinition;

import java.lang.reflect.Method;

public class DisposableBeanAdapter implements DisposableBean {
    private final Object bean;
    private final String beanName;
    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        if(bean instanceof DisposableBean){
            ((DisposableBean)bean).destroy();
        }

        if(StrUtil.isNotEmpty(destroyMethodName) && !(bean instanceof DisposableBean && "destroy".equals(this.destroyMethodName))){
            final Method method = bean.getClass().getMethod(destroyMethodName);
            if(null == method){
                throw new BeansException("could not find a destroy method");
            }
            method.invoke(bean);
        }
    }
}
