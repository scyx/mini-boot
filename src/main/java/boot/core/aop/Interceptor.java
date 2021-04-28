package boot.core.aop;

/**
 * @author cyx
 */
public interface Interceptor {
    Object intercept(MethodInvocation methodInvocation);

    default boolean support(Object bean){
        return false;
    }
}
