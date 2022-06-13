package demo.springframework.factory.surpport.earlybean;

import demo.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class SpouseAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("advice切面:" + method);
    }
}
