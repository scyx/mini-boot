package boot.core.aop;

import boot.annotation.aop.After;
import boot.annotation.aop.Before;
import boot.annotation.aop.PointCut;
import boot.util.PatternMatchUtil;
import boot.util.ReflectionUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author cyx
 */
public class InvocationInterceptor implements Interceptor {
    private Object bean;
    private final Set<String> expressionsUrl = new HashSet<>();
    private final Set<String> annotationNames = new HashSet<>();
    private final List<Method> beforeMethods = new ArrayList<>();
    private final List<Method> afterMethods = new ArrayList<>();

    public InvocationInterceptor(Object bean) {
        this.bean = bean;
        build();
    }

    private void build() {
        Class<?> clazz = bean.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            PointCut pointCut = method.getAnnotation(PointCut.class);
            if (pointCut != null) {
                String value = pointCut.value();
                expressionsUrl.add(value);
            }
            Before before = method.getAnnotation(Before.class);
            if (before != null) {
                beforeMethods.add(method);
            }
            After after = method.getAnnotation(After.class);
            if (after != null) {
                afterMethods.add(method);
            }
        }
    }

    @Override
    public boolean support(Object bean) {
        String className = bean.getClass().getName();
        for (String expression : expressionsUrl) {
            if (PatternMatchUtil.simpleMatch(expression,className)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public Object intercept(MethodInvocation methodInvocation) {
        JoinPoint joinPoint = new JoinPoint(bean,methodInvocation.getTargetObject(),methodInvocation.getArgs());
        for (Method method : beforeMethods) {
            ReflectionUtil.executeTargetMethodNoResult(bean,method, joinPoint);
        }
        Object res = methodInvocation.proceed();
        for (Method method : afterMethods) {
            ReflectionUtil.executeTargetMethodNoResult(bean,method, joinPoint);
        }
        return res;
    }
}
