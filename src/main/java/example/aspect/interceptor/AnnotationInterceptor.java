package example.aspect.interceptor;

import boot.annotation.aop.After;
import boot.annotation.aop.Before;
import boot.annotation.aop.PointCut;
import boot.core.aop.JoinPoint;

/**
 * @author cyx
 */
public class AnnotationInterceptor {
    @PointCut("@anootation(boot.annotation.mvc.*Mapping)")
    public void pointcut() {

    }
    @Before
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("before anootation boot.annotation.mvc.RestController");
    }
    @After
    public void doAfter(JoinPoint joinPoint) {
        System.out.println("after anootation boot.annotation.mvc.RestController");
    }
}
