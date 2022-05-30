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
}
