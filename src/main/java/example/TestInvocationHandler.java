package example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author cyx
 */
@AllArgsConstructor
@Data
public class TestInvocationHandler implements InvocationHandler {
    private Object target;
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        if ("intercept".equals(method.getName())) {
            System.out.println("invoke test");
            result = method.invoke(target,args);
            System.out.println("end test");
        } else {
            result = method.invoke(target,args);
        }
        return result;
    }
}
