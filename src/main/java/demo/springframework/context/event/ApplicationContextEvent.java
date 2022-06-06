package demo.springframework.context.event;

import demo.springframework.context.ApplicationContext;
import demo.springframework.context.ApplicationEvent;

public class ApplicationContextEvent extends ApplicationEvent {
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    public final ApplicationContext getApplicationContext(){
        return (ApplicationContext) getSource();
    }
}