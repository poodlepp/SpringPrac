package demo.springframework.factory.surpport.demo;

import cn.hutool.core.util.ClassUtil;
import org.junit.jupiter.api.Test;

import java.net.URL;

public class ResourceDEMO {
    @Test
    public void test1(){
        final URL resource = ResourceDEMO.class.getResource("EnhancerDemo.class");
        final ClassLoader classLoader = ClassUtil.getClassLoader();
        final URL resource1 = classLoader.getResource("");
        System.out.println(resource.toString());
    }
}