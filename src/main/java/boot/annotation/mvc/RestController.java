package boot.annotation.mvc;

import java.lang.annotation.*;


/**
 * @author cyx
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RestController {
    String value() default "";
}
