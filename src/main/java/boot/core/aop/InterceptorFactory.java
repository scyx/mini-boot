package boot.core.aop;

import boot.annotation.aop.Aspect;
import boot.annotation.aop.PointCut;
import boot.core.store.ComponentStore;
import boot.util.ReflectionUtil;

import java.lang.annotation.Retention;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @author cyx
 */
public class InterceptorFactory {
    public static final Set<Interceptor> interceptors = new HashSet<>();

    public static void loadInterceptors(String[] packageName) {
        // 找到有@Aspect注解的类
        // Aspect set
        Set<Class<?>> set = ComponentStore.CLASS_MAP.get(Aspect.class);
        for (Class<?> clazz : set) {
            Object bean = ReflectionUtil.newInstance(clazz);
            // Aspect中有@PointCut @Before @After注解的方法
            InvocationInterceptor invocationInterceptor = new InvocationInterceptor(bean);
            interceptors.add(invocationInterceptor);
        }
    }

}
