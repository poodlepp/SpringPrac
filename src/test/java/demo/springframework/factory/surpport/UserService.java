package demo.springframework.factory.surpport;

import demo.springframework.factory.DisposableBean;
import demo.springframework.factory.InitializingBean;
import lombok.Data;

@Data
public class UserService implements InitializingBean, DisposableBean {
    private String uid;
    private String company;
    private String location;
    private UserDao UserDao;
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
}
