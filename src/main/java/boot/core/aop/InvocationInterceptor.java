package boot.core.aop;

import boot.annotation.aop.After;
import boot.annotation.aop.Before;
import boot.annotation.aop.PointCut;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class InvocationInterceptor implements Interceptor {
    private Object bean;
    private final Set<String> expressionsUrl = new HashSet<>();
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
}
