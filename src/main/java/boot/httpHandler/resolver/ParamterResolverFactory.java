package boot.httpHandler.resolver;

import boot.annotation.mvc.PathVariable;
import boot.annotation.mvc.RequestBody;
import boot.annotation.mvc.RequestParam;

import java.lang.reflect.Parameter;

/**
 * @author cyx
 */
public class ParamterResolverFactory {
    private static ParamterResolver pathVaribleResolver = new PathVaribleResolver();
    private static ParamterResolver requestParamResolver = new RequestParamResolver();
    private static ParamterResolver requestBodyResolver = new RequestBodyResolver();


    public static ParamterResolver getParamterResolver(Parameter parameter) {
        if (parameter.isAnnotationPresent(PathVariable.class)) {
            return pathVaribleResolver;
        }
        if (parameter.isAnnotationPresent(RequestParam.class)) {
            return requestParamResolver;
        }
        if (parameter.isAnnotationPresent(RequestBody.class)) {
            return requestBodyResolver;
        }
        return null;
    }
}
