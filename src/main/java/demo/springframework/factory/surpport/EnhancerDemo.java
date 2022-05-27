package demo.springframework.factory.surpport;

public class EnhancerDemo {
    /**
     * 为非接口类型创建一个代理；创建子类拦截所有方法
     */
    public String sayHello(boolean throwEx) throws Exception {
        System.out.println("Hello");
        if(throwEx){
            throw new Exception("test ex");
        }
        return "123";
    }
}
