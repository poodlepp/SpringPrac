package demo.springframework.factory.surpport;

import demo.springframework.BeansException;
import demo.springframework.beans.factory.ApplicationContextAware;
import demo.springframework.beans.factory.BeanClassLoaderAware;
import demo.springframework.beans.factory.BeanFactoryAware;
import demo.springframework.beans.factory.BeanNameAware;
import demo.springframework.context.ApplicationContext;
import demo.springframework.factory.BeanFactory;
import demo.springframework.factory.DisposableBean;
import demo.springframework.factory.InitializingBean;
import lombok.Data;

@Data
public class UserService implements InitializingBean, DisposableBean, BeanNameAware, BeanClassLoaderAware, ApplicationContextAware, BeanFactoryAware {
    private String uid;
    private String company;
    private String location;
    private UserDao UserDao;
    private ApplicationContext applicationContext;
    private BeanFactory beanFactory;

    public UserService(){
        super();
    }
    public UserService(String uid){
        this.uid = uid;
    }
    public String queryUserInfo() {
        return UserDao.queryVal(uid) +"++"+ company +"+++"+ location;
    }


    @Override
    public void destroy() throws Exception {
        System.out.println("userservice 执行destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("userservice 执行initialization");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("classloader:" + classLoader);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("beanname is:" + name);
    }
}
