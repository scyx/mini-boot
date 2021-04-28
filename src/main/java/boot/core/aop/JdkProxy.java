package boot.core.aop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author cyx
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JdkProxy extends BeanPostProcesser implements ProxyInterface, InvocationHandler {

    private Object object;
    private Interceptor interceptor;

    @Override
    public Object wrapBean(Object obj,Interceptor interceptor) {
        JdkProxy jdkProxy = new JdkProxy(obj,interceptor);
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(),jdkProxy);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MethodInvocation methodInvocation = new MethodInvocation(object,method,args);
        return interceptor.intercept(methodInvocation);
    }
}
