package demo.springframework.context.annotation;

import cn.hutool.core.util.ClassUtil;
import demo.springframework.factory.config.BeanDefinition;
import demo.springframework.starteotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

public class ClassPathScanningCandidateComponentProvider {
    /**
     * 获取意向类
     * @param basePackage 全路径
     * @return
     */
    public Set<BeanDefinition> findCandidateComponents(String basePackage){
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        Set<BeanDefinition> result = classes.stream().map(item -> new BeanDefinition(item)).collect(Collectors.toSet());
        return result;
    }
}
