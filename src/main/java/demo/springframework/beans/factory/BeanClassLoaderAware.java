package demo.springframework.beans.factory;

/**
 * 实现此接口，能感知到所属的classloader
 */
public interface BeanClassLoaderAware extends Aware{
    void setBeanClassLoader(ClassLoader classLoader);
}
