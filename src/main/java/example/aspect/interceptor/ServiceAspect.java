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
public class ServiceAspect {
    @PointCut("example.service.A")
    public void pointa() {

    }
    @Before
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("example.service.A doBefore");
    }
    @After
    public void doAfter(JoinPoint joinPoint) {
        System.out.println("example.service.A doAfter");
    }
}
