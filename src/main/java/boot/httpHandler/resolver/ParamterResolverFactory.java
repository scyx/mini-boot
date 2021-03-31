package boot.httpHandler.resolver;

import boot.annotation.mvc.PathVariable;
import boot.annotation.mvc.RequestBody;
import boot.annotation.mvc.RequestParam;

import java.lang.reflect.Parameter;

/**
 * @author cyx
 */
public class ParamterResolverFactory {
    private static ParamterResolver paramterResolver = new PathVaribleResolver();


    public static ParamterResolver getParamterResolver(Parameter parameter) {
        if (parameter.isAnnotationPresent(PathVariable.class)) {
            return paramterResolver;
        }
        return null;
    }
}
