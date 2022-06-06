package demo.springframework.factory.surpport.event;

import demo.springframework.context.support.ClassPathXmlApplicationContext;
import demo.springframework.factory.surpport.factorybean.IUserService;
import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

public class DemoJunit {


    @Test
    public void test2(){
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:eventconfig.xml");
        context.publishEvent(new CustomEvent(context,123L,"success"));
        context.registerShutdownHook();
        System.out.println("over");
    }

}
