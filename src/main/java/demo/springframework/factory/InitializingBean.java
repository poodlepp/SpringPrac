package demo.springframework.factory;

public interface InitializingBean {
    void afterPropertiesSet() throws Exception;
}
