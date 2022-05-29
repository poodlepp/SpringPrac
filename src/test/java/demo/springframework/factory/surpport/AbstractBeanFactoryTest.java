package demo.springframework.factory.surpport;

import demo.springframework.beans.BeanReference;
import demo.springframework.beans.PropertyValue;
import demo.springframework.beans.PropertyValues;
import demo.springframework.factory.config.BeanDefinition;
import org.junit.Test;

public class AbstractBeanFactoryTest {

    @Test
    public void getBean() {
        final DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        factory.registerBeanDefinition("UserDao",new BeanDefinition(UserDao.class));
        final PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("name","02"));
        propertyValues.addPropertyValue(new PropertyValue("UserDao",new BeanReference("UserDao")));
        final BeanDefinition userSerDefinition = new BeanDefinition(UserService.class, propertyValues);
        factory.registerBeanDefinition("userService",userSerDefinition);

        UserService userService = (UserService) factory.getBean("userService");
        userService.queryUserInfo();
        System.out.println(userService.toString());

    }


    @Test
    public void test2(){
        final DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();

        final XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(defaultListableBeanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");
        UserService userService = defaultListableBeanFactory.getBean("userService", UserService.class);
        System.out.println(userService.queryUserInfo());
    }
}