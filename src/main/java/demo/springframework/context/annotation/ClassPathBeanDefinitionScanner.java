package demo.springframework.context.annotation;

import cn.hutool.core.util.StrUtil;
import demo.springframework.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import demo.springframework.factory.config.BeanDefinition;
import demo.springframework.factory.surpport.BeanDefinitionRegistry;
import demo.springframework.starteotype.Component;

import java.util.Set;

/**
 * basepackages -> beandefinition -> registry
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider{
    private BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void doScan(String... basePackages){
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidateComponents = findCandidateComponents(basePackage);
            for (BeanDefinition candidateComponent : candidateComponents) {
                String scope = resolveBeanScope(candidateComponent);
                if(StrUtil.isNotEmpty(scope)){
                    candidateComponent.setScope(scope);
                }
                registry.registerBeanDefinition(determineBeanName(candidateComponent),candidateComponent);
            }
        }

        registry.registerBeanDefinition("demo.springframework.factory.annotation.internalAutowiredAnnotationProcessor", new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class));
    }

    private String determineBeanName(BeanDefinition candidateComponent) {
        Class<?> clazz = candidateComponent.getClazz();
        Component annotation = clazz.getAnnotation(Component.class);
        String value = annotation.value();
        if(StrUtil.isNotEmpty(value)){
            return value;
        }else{
            return StrUtil.lowerFirst(clazz.getSimpleName());
        }
    }

    private String resolveBeanScope(BeanDefinition candidateComponent) {
        Class<?> clazz = candidateComponent.getClazz();
        Scope scope = clazz.getAnnotation(Scope.class);
        if(scope != null){
            return scope.value();
        }
        return StrUtil.EMPTY;
    }
}
