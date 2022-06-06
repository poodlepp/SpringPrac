package demo.springframework.factory.surpport.event;

import demo.springframework.context.ApplicationListener;
import demo.springframework.context.event.ContextClosedEvent;

public class ContextClosedEventlistener implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("context close   " + event.toString());
    }
}
