package demo.springframework.factory.surpport.autoimport;

import demo.springframework.factory.annotation.Autowired;
import demo.springframework.factory.annotation.Value;
import demo.springframework.starteotype.Component;
import lombok.Data;

@Data
@Component("userService")
public class UserService {
    @Value("${token}")
    private String token;
    @Autowired
    private UserDao userDao;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String queryUserInfo(String uid){
        System.out.println("queryuserinfo  uid:" + uid);
        System.out.println("query result : " + "123");
        System.out.println("token:" + token);
        System.out.println("userdao query 01 " + userDao.queryVal("01"));
        System.out.println("userdao query 10001 " + userDao.queryVal("10001"));
        return "123";
    }
}
