package boot.annotation.mvc;

import java.lang.annotation.*;

/**
 * @author cyx
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PathVariable {

    String value() default "";

    boolean required() default true;
}