package demo.springframework.factory.surpport.event;

import demo.springframework.context.ApplicationListener;

import java.util.Date;

public class CustomEventListener implements ApplicationListener<CustomEvent> {
    @Override
    public void onApplicationEvent(CustomEvent event) {
        System.out.println("收到消息 " + event.getSource() + "   时间：" + new Date());
        System.out.println("消息：" + event.getId() + ":" + event.getMessage());
    }
}
