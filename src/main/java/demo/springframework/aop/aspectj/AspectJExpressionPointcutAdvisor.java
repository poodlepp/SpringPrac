package demo.springframework.aop.aspectj;

import demo.springframework.aop.Pointcut;
import demo.springframework.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

/**
 * 切面拦截器
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    private AspectJExpressionPointcut pointcut;
    private Advice advice;
    private String expression;

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public Pointcut getPointcut() {
        if(null == pointcut){
            pointcut = new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }
}
