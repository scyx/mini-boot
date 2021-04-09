package boot.core.apo;

import boot.util.ReflectionUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * @author cyx
 */
public class InterceptorFactory {
    private static Set<Class<? extends Interceptor>> interceptors = new HashSet<>();

    public static void loadInterceptors(String[] packageName) {
        interceptors = ReflectionUtil.getSubClass(packageName,Interceptor.class);
    }

}
