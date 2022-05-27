package demo.springframework.factory.surpport;

import demo.springframework.beans.BeanReference;
import demo.springframework.beans.PropertyValue;
import demo.springframework.beans.PropertyValues;
import demo.springframework.factory.config.BeanDenition;
import org.junit.Test;

public class AbstractBeanFactoryTest {

    @Test
    public void getBean() {
        final DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        factory.registerBeanDefinition("userDAO",new BeanDenition(UserDAO.class));
        final PropertyValues propertyValues = new PropertyValues();
        propertyValues.addProperty(new PropertyValue("name","02"));
        propertyValues.addProperty(new PropertyValue("userDAO",new BeanReference("userDAO")));
        final BeanDenition userSerDefinition = new BeanDenition(UserService.class, propertyValues);
        factory.registerBeanDefinition("userService",userSerDefinition);

        UserService userService = (UserService) factory.getBean("userService");
        userService.queryUserInfo();
        System.out.println(userService.toString());

    }
}