package demo.springframework.context.support;

import demo.springframework.factory.ConfigurableListableBeanFactory;
import demo.springframework.factory.surpport.DefaultListableBeanFactory;

public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext{
    private DefaultListableBeanFactory beanFactory;

    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    protected void refreshBeanFactory() {
        DefaultListableBeanFactory beanFactory = createBeanfactory();
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;

    }

    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

    private DefaultListableBeanFactory createBeanfactory() {
        return new DefaultListableBeanFactory();
    }
}
