package demo.springframework.factory.surpport.factorybean;

import demo.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;

public class ProxyFactoryBean implements FactoryBean<IUserDao> {
    @Override
    public IUserDao getObject() throws Exception {
        InvocationHandler handler = (proxy,method,args) -> {
            HashMap<String, String> stringStringHashMap = new HashMap<>();
            stringStringHashMap.put("10001","xiaofu");
            stringStringHashMap.put("10002","xiaobei");
            stringStringHashMap.put("10003","xiaomao");
            return "你被代理了  " + method.getName() + "  " + stringStringHashMap.get(args[0]);
        };
        return (IUserDao) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),new Class[]{IUserDao.class},handler);
    }

    @Override
    public Class<?> getObjectType() {
        return IUserDao.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
