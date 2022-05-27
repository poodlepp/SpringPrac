package demo.springframework.factory.surpport;

public class UserService {
    private String name;
    public UserService(String name){
        this.name = name;
    }
    public void queryUserInfo(){
        System.out.println("user name: " + this.name);
    }
}
