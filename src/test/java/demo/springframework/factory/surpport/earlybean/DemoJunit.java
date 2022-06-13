package demo.springframework.factory.surpport.earlybean;

import demo.springframework.context.support.ClassPathXmlApplicationContext;
import demo.springframework.factory.surpport.factorybean.IUserService;
import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

public class DemoJunit {


    @Test
    public void test(){
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:earlyspring.xml");
        context.registerShutdownHook();
        final Husband husband = context.getBean("husband", Husband.class);
        final Wife wife = context.getBean("wife", Wife.class);

        System.out.println(wife.queryHusband());
        System.out.println(husband.queryWife());


    }

}
