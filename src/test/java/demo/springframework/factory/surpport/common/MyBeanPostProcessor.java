package demo.springframework.factory.surpport.common;

import demo.springframework.BeansException;
import demo.springframework.factory.config.BeanPostProcessor;
import demo.springframework.factory.surpport.UserService;

public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if("userService".equals(beanName)){
            UserService userService = (UserService) bean;
            userService.setLocation("改为：北京");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
