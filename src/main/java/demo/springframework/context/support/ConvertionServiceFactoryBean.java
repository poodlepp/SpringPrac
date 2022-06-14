package demo.springframework.context.support;

import demo.springframework.beans.factory.FactoryBean;
import demo.springframework.core.convert.ConversionService;
import demo.springframework.factory.InitializingBean;

public class ConvertionServiceFactoryBean implements FactoryBean<ConversionService>, InitializingBean {
    @Override
    public ConversionService getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
