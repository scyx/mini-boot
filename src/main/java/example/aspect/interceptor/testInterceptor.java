package example.aspect.interceptor;

import boot.annotation.aop.After;
import boot.annotation.aop.Aspect;
import boot.annotation.aop.Before;
import boot.annotation.aop.PointCut;

/**
 * @author cyx
 */
@Aspect
public class testInterceptor {

    @PointCut("com.example.service.ExampleService")
    public void pointcut() {

    }
    @Before
    public void doBefore() {
        System.out.println("doBefore");
    }
    @After
    public void doAfter() {
        System.out.println("doAfter");
    }

}
