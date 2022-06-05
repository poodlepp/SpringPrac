package demo.springframework.factory.surpport.factorybean;

public class IUserService {
    private String uid;
    private String company;
    private String location;
    private IUserDao userDao;

    public String queryUserInfo(){
        return  userDao.queryUserName(uid) + "," + company + "," + location;
    }
}
