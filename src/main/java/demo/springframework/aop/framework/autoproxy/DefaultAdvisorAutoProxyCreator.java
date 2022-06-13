package demo.springframework.aop.framework.autoproxy;

import demo.springframework.BeansException;
import demo.springframework.aop.*;
import demo.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import demo.springframework.aop.framework.ProxyFactory;
import demo.springframework.beans.PropertyValues;
import demo.springframework.beans.factory.BeanFactoryAware;
import demo.springframework.factory.BeanFactory;
import demo.springframework.factory.config.InstantiationAwareBeanPostProcessor;
import demo.springframework.factory.surpport.DefaultListableBeanFactory;
import demo.springframework.util.ClassUtils;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor,BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    /**
     * 包含代理功能的半成品
     */
    private final Set<Object> earlyProxyReferences = Collections.synchronizedSet(new HashSet<Object>());

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!earlyProxyReferences.contains(beanName)) {
            return wrapIfNecessary(bean, beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues propertyValues, Object bean, String beanName) {
        return propertyValues;
    }

    @Override
    public Object getEarlyBeanReference(Object bean, String name){
        earlyProxyReferences.add(name);
        return wrapIfNecessary(bean,name);
    }

    private Object wrapIfNecessary(Object bean, String name) {
        if(isInfrastructureClass(bean.getClass())){
            return bean;
        }

        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();
        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();
            if(!classFilter.matches(bean.getClass())){
                continue;
            }

            AdvisedSupport advisedSupport = new AdvisedSupport();
            advisedSupport.setTargetSource(new TargetSource(bean));
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            //这里写死true,反之如果使用jdk代理，会有copyValue 格式化问题
            advisedSupport.setProxyTargetClass(true);
            //创建代理对象
            return new ProxyFactory(advisedSupport).getProxy();
        }
        return bean;
    }

    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass) || Pointcut.class.isAssignableFrom(beanClass) || Advisor.class.isAssignableFrom(beanClass);
    }
}
