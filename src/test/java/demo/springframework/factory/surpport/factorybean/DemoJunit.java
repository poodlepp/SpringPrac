package demo.springframework.factory.surpport.factorybean;

import demo.springframework.context.support.ClassPathXmlApplicationContext;
import demo.springframework.factory.surpport.UserService;
import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

public class DemoJunit {

    @Test
    public void test(){
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:factorybean.xml");
        context.registerShutdownHook();
        final IUserService userService1 = context.getBean("userService", IUserService.class);
        final IUserService userService2 = context.getBean("userService", IUserService.class);

        System.out.println("print userservice");
        System.out.println(userService1);
        System.out.println(userService2);

        // 4. 打印十六进制哈希
        System.out.println(userService1 + " 十六进制哈希：" + Integer.toHexString(userService1.hashCode()));
        System.out.println(ClassLayout.parseInstance(userService1).toPrintable());

    }

    @Test
    public void test2(){
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:factorybean.xml");
        context.registerShutdownHook();
        final IUserService userService = context.getBean("userService", IUserService.class);
        String s = userService.queryUserInfo();
        System.out.println("query result: " + s);

    }

}
