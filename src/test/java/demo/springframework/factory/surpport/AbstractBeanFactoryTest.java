package demo.springframework.factory.surpport;

import demo.springframework.factory.config.BeanDenition;
import org.junit.Test;

public class AbstractBeanFactoryTest {

    @Test
    public void getBean() {
        final DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        factory.registerBeanDefinition("userService",new BeanDenition(UserService.class));
        UserService userService = (UserService) factory.getBean("userService","userName");
        userService.queryUserInfo();
        System.out.println(userService.toString());


        userService = (UserService) factory.getBean("userService");
        userService.queryUserInfo();
        System.out.println(userService.toString());
    }
}