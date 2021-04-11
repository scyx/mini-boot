package boot.core.aop;

import boot.annotation.aop.Aspect;
import boot.core.store.ComponentStore;
import boot.util.ReflectionUtil;

import java.lang.annotation.Retention;
import java.util.HashSet;
import java.util.Set;

/**
 * @author cyx
 */
public class InterceptorFactory {
    private static Set<Interceptor> interceptors = new HashSet<>();

    public static void loadInterceptors(String[] packageName) {
        // 找到有@Aspect注解的类
        Set<Class<?>> set = ComponentStore.CLASS_MAP.get(Aspect.class);
        for (Class<?> clazz : set) {
            Object bean = ReflectionUtil.newInstance(clazz);
            InvocationInterceptor interceptor = new InvocationInterceptor(bean);
            interceptors.add(interceptor);
        }
    }

}
