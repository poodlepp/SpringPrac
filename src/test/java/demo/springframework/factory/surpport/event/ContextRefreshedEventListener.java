package demo.springframework.factory.surpport.event;

import demo.springframework.context.ApplicationListener;
import demo.springframework.context.event.ContextRefreshedEvent;

public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("context refresh  " + event.toString());
    }
}
