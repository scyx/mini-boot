package boot.core.aop;


/**
 * @author cyx
 */
public abstract class BeanPostProcesser {

    public Object wrap(Object bean) {
        Object t = bean;
        for (Interceptor interceptor : InterceptorFactory.interceptors) {
            if (interceptor.support(bean)) {
                t = wrapBean(t,interceptor);
            }
        }
        return t;
    }

    public abstract Object wrapBean(Object object,Interceptor interceptor) ;


    public static BeanPostProcesser getProxy(Class<?> clazz) {
        if (clazz.isInterface() || clazz.getInterfaces().length > 0) {
            return new JdkProxy();
        }
        return new CglibProxy();
    }
}
