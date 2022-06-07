package demo.springframework.factory.surpport.proxy;

public class ProxyService implements IProxyService{
    @Override
    public String printSomething() {
        System.out.println("hahaha print hahaha");
        return "hahaha";
    }
}
