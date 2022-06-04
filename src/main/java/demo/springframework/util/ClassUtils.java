package demo.springframework.util;

public class ClassUtils {
    /**
     * 获取类加载器
     * @return
     */
    public static ClassLoader getClassLoader(){
        try {
            return Thread.currentThread().getContextClassLoader();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ClassUtils.class.getClassLoader();
    }
}
