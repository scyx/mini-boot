package boot.core.aop;

import boot.annotation.aop.Aspect;
import boot.util.ReflectionUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;

/**
 * @author cyx
 */
@Getter
@AllArgsConstructor
public class MethodInvocation {
    //target object
    private final Object targetObject;
    //target method
    private final Method targetMethod;
    //the parameter of target method
    private final Object[] args;

    public Object proceed() {
        return ReflectionUtil.executeTargetMethod(targetObject, targetMethod, args);
    }
}
