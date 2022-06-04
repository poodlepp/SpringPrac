package demo.springframework.beans.factory;

import demo.springframework.BeansException;
import demo.springframework.context.ApplicationContext;

/**
 * 感知所属的applicationContext
 */
public interface ApplicationContextAware extends Aware{
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
