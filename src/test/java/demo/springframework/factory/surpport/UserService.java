package demo.springframework.factory.surpport;

public class UserService {
    private String name;
    private UserDAO userDAO;
    public UserService(){
        super();
    }
    public UserService(String name){
        this.name = name;
    }
    public void queryUserInfo() {
        final String s = userDAO.queryVal(name);
        System.out.println("query result:" + s);
    }
}
