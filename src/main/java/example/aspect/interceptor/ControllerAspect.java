package example.aspect.interceptor;

import boot.annotation.aop.After;
import boot.annotation.aop.Aspect;
import boot.annotation.aop.Before;
import boot.annotation.aop.PointCut;
import boot.core.aop.JoinPoint;

/**
 * @author cyx
 */
@Aspect
public class ControllerAspect {

    @PointCut("example.controller.*Controller")
    public void pointcut() {

    }
    @Before
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("doBefore");
    }
    @After
    public void doAfter(JoinPoint joinPoint) {
        System.out.println("doAfter");
    }

}
