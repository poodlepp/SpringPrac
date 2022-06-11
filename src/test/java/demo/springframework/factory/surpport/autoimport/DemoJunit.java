package demo.springframework.factory.surpport.autoimport;

import demo.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

public class DemoJunit {


    @Test
    public void test2(){
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:autospring.xml");
        context.registerShutdownHook();
        final IUserservice userService = context.getBean("userService", IUserservice.class);
        String s = userService.queryUserInfo("uuuuu");
        System.out.println("test query result: " + s);

    }

}
