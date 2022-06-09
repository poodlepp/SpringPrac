package demo.springframework.factory.surpport.proxy;

import demo.springframework.aop.AdvisedSupport;
import demo.springframework.aop.TargetSource;
import demo.springframework.aop.aspectj.AspectJExpressionPointcut;
import demo.springframework.aop.framework.Cglib2AopProxy;
import demo.springframework.aop.framework.JdkDynamicAopProxy;
import demo.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

import java.lang.reflect.Method;

public class DemoJunit {
    @Test
    public void test1() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* demo.springframework.factory.surpport.proxy.IProxyService.*(..))");
        Class<IProxyService> clazz = IProxyService.class;
        Method method = clazz.getDeclaredMethod("printSomething");
        System.out.println(pointcut.matches(clazz));
        System.out.println(pointcut.matches(method,clazz));
    }

    @Test
    public void test2(){
        ProxyService proxyService = new ProxyService();

        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(proxyService));
        advisedSupport.setMethodInterceptor(new ProxyServiceInterceptor());
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* demo.springframework.factory.surpport.proxy.IProxyService.*(..))"));

        IProxyService proxy_jdk = (IProxyService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        System.out.println("jdk  result:" + proxy_jdk.printSomething());

        System.out.println("-------------------------分割线-----------------------------");

        IProxyService proxy_cglib = (IProxyService) new Cglib2AopProxy(advisedSupport).getProxy();
        System.out.println("cglib resutl:" + proxy_cglib.printSomething());

    }


    @Test
    public void test3(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:proxyspring.xml");
        IProxyService proxyService = applicationContext.getBean("proxyService", IProxyService.class);
        System.out.println("测试结果：" + proxyService.printSomething());

    }
}
