package demo.springframework.beans.factory;

/**
 * 实现此接口，可以感知所属的beanname
 */
public interface BeanNameAware extends Aware{
    void setBeanName(String name);
}
