package demo.springframework.util;

import demo.springframework.context.ApplicationListener;

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

    public static boolean isCglibProxyClass(Class<?> clazz) {
        return (clazz != null && isCglibProxyClassName(clazz.getName()));
    }

    public static boolean isCglibProxyClassName(String className) {
        return (className != null && className.contains("$$"));
    }
}
