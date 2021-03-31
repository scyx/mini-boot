package boot.httpHandler.resolver;

import boot.core.store.HandlerMethod;

import java.lang.reflect.Parameter;

/**
 * @author cyx
 */
public interface ParamterResolver {
    /**
     * 处理参数
     * @param handlerMethod
     * @param parameter
     * @return
     */
    Object resolve(HandlerMethod handlerMethod, Parameter parameter);
}
