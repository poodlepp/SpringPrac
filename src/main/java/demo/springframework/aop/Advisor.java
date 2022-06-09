package demo.springframework.aop;

import org.aopalliance.aop.Advice;

/**
 * 访问者
 */
public interface Advisor {
    Advice getAdvice();
}
