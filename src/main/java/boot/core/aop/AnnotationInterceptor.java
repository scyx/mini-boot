package boot.core.aop;

import boot.annotation.aop.After;
import boot.annotation.aop.Before;
import boot.annotation.aop.PointCut;
import boot.util.PatternMatchUtil;
import boot.util.ReflectionUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author cyx
 */
@Slf4j
@Data
@NoArgsConstructor
public class AnnotationInterceptor implements Interceptor{
    private Object bean;
//    private final Set<String> expressionsUrl = new HashSet<>();
    private final Set<String> annotationNames = new HashSet<>();
    private final List<Method> beforeMethods = new ArrayList<>();
    private final List<Method> afterMethods = new ArrayList<>();

    public AnnotationInterceptor(Object bean) {
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
                annotationNames.add(value.substring(12,value.length() - 1));
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
    public Object intercept(MethodInvocation methodInvocation) {
        Annotation[] annotations = methodInvocation.getTargetMethod().getDeclaredAnnotations();
        boolean methodHasAnnotations = false;
        if (annotations.length > 0) {
            for (Annotation annotation : annotations) {
                    String name = annotation.getClass().getName();
                    for (String pattern : annotationNames) {
                        if (PatternMatchUtil.simpleMatch(pattern,name)) {
                            methodHasAnnotations = true;
                        }
                    }
            }
            if (methodHasAnnotations) {
                JoinPoint joinPoint = new JoinPoint(bean,methodInvocation.getTargetObject(),methodInvocation.getArgs());
                for (Method method : beforeMethods) {
                    ReflectionUtil.executeTargetMethodNoResult(bean,method,joinPoint);
                }
                Object result = methodInvocation.proceed();

                for (Method method : afterMethods) {
                    ReflectionUtil.executeTargetMethodNoResult(bean,method,joinPoint);
                }
                return result;
            }
        }
        return methodInvocation.proceed();
    }

    @Override
    public boolean support(Object bean) {
        for (Method method : bean.getClass().getDeclaredMethods()) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            if (annotations.length > 0){
                for (Annotation annotation : annotations) {
                    String name = annotation.getClass().getName();
                    for (String pattern : annotationNames) {
                        if (PatternMatchUtil.simpleMatch(pattern,name)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
