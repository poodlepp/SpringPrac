package demo.springframework.context.annotation;

import java.lang.annotation.*;

/**
 * @author lipengyi
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {
    String value() default "singleton";
}
