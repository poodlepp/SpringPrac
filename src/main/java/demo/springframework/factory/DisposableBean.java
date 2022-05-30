package demo.springframework.factory;

public interface DisposableBean {
    void destroy() throws Exception;
}
