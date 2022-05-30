package demo.springframework.factory.surpport;

public class UserService {
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public UserDao getUserDao() {
        return UserDao;
    }

    public void setUserDao(UserDao UserDao) {
        this.UserDao = UserDao;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
