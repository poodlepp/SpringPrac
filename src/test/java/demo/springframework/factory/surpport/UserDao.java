package demo.springframework.factory.surpport;

import java.util.HashMap;
import java.util.Map;

public class UserDao {
    private static Map<String,String> map = new HashMap<>();
    static {
        map.put("01","xiaoyi");
        map.put("02","xiaoer");
        map.put("03","xiaosan");
    }
    public String queryVal(String uid){
        return map.get(uid);
    }

    public void initDataMethod(){
        System.out.println("userdao 执行initmethod");
        map.put("10001","小夫");
        map.put("10002","八杯水");
        map.put("10003","阿毛");
    }

    public void destroyDataMethod(){
        System.out.println("userdao 执行destroy");
        map.clear();
    }
}
