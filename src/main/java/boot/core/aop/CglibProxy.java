package boot.core.aop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author cyx
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CglibProxy extends BeanPostProcesser implements ProxyInterface, MethodInterceptor  {
    private Object object;
    private Interceptor interceptor;

    @Override
    public Object wrapBean(Object object, Interceptor interceptor) {
        Class<?> rootClass = object.getClass();
        Class<?> proxySuperClass = rootClass;
        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(object.getClass().getClassLoader());
        enhancer.setSuperclass(proxySuperClass);
        enhancer.setCallback(new CglibProxy(object,interceptor));
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        MethodInvocation methodInvocation = new MethodInvocation(object,method,args);
        return interceptor.intercept(methodInvocation);
    }
}
