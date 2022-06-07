package demo.springframework.aop;

import java.lang.reflect.Method;

/**
 * 匹配方法
 */
public interface MethodMatcher {
    boolean matches(Method method, Class<?> targetClass);
}
