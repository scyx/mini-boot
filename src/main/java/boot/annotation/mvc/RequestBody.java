package boot.annotation.mvc;

import java.lang.annotation.*;

/**
 * @author cyx
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestBody {

    /**
     * Whether body content is required.
     * <p>Default is {@code true}, leading to an exception thrown in case
     * there is no body content. Switch this to {@code false} if you prefer
     * {@code null} to be passed when the body content is {@code null}.
     */
    boolean required() default true;

}
