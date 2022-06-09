package demo.springframework.factory.surpport.proxy;

import demo.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class ProxyServiceBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("拦截方法before:" + method.getName());
    }
}
