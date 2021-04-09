package boot.annotation.aop;

import java.lang.annotation.*;

/**
 * @author cyx
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PointCut {
    String value();
}
