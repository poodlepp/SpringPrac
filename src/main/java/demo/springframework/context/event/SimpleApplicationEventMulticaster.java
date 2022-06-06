package demo.springframework.context.event;

import demo.springframework.context.ApplicationEvent;
import demo.springframework.context.ApplicationListener;
import demo.springframework.factory.ConfigurableListableBeanFactory;

public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {
    public SimpleApplicationEventMulticaster(ConfigurableListableBeanFactory beanFactory) {
        this.setBeanFactory(beanFactory);
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {
        for (ApplicationListener applicationListener : getApplicationListener(event)) {
            applicationListener.onApplicationEvent(event);
        }
    }
}
