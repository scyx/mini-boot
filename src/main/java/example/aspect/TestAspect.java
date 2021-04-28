package example.aspect;

import boot.annotation.aop.After;
import boot.annotation.aop.Aspect;
import boot.annotation.aop.Before;
import boot.annotation.aop.PointCut;
import boot.core.aop.JoinPoint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author cyx
 */
@Aspect
public class TestAspect {

    @PointCut("example.service.*ServiceImpl")
    public void PointCut() {

    }

    @Before()
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("service before");
    }

    @After
    public void doAfter(JoinPoint joinPoint) {
        System.out.println("service after");
    }














}
