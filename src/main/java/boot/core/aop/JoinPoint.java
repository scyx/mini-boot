package boot.core.aop;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author cyx
 */
@Getter
@AllArgsConstructor
public class JoinPoint {
    private final Object adviceBean;
    private final Object target;
    private Object[] args;


    public Object getAdviceBean() {
        return adviceBean;
    }


    public Object getTarget() {
        return target;
    }


    public Object[] getArgs() {
        if (args == null) {
            args = new Object[0];
        }
        Object[] argsCopy = new Object[args.length];
        System.arraycopy(args, 0, argsCopy, 0, args.length);
        return argsCopy;
    }
}
