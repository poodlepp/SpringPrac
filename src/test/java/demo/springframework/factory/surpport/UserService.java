package demo.springframework.factory.surpport;

public class UserService {
    private String name;
    private UserDao UserDao;
    public UserService(){
        super();
    }
    public UserService(String name){
        this.name = name;
    }
    public String queryUserInfo() {
        final String s = UserDao.queryVal(name);
        System.out.println("query result:" + s);
        return s;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserDao getUserDao() {
        return UserDao;
    }

    public void setUserDao(UserDao UserDao) {
        this.UserDao = UserDao;
    }
}
