package demo.springframework.aop.framework;

import demo.springframework.aop.AdvisedSupport;

/**
 * 代理工厂
 * 自动选择代理方式
 */
public class ProxyFactory {
    private AdvisedSupport advisedSupport;

    public ProxyFactory(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    public Object getProxy(){
        return createAopProxy().getProxy();
    }

    private AopProxy createAopProxy() {
        if(advisedSupport.isProxyTargetClass()){
            return new Cglib2AopProxy(advisedSupport);
        }else{
            return new JdkDynamicAopProxy(advisedSupport);
        }
    }
}
