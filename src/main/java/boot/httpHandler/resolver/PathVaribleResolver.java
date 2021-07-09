package boot.httpHandler.resolver;

import boot.annotation.mvc.PathVariable;
import boot.core.store.HandlerMethod;
import boot.util.SerializerUtil;

import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * @author cyx
 */
public class PathVaribleResolver implements ParamterResolver {

    @Override
    public Object resolve(HandlerMethod handlerMethod, Parameter parameter) {
        PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
        String name = pathVariable.value();
        Map<String, String> map = handlerMethod.getPathVariblesMap();
        if ("".equals(name)) {
            name = parameter.getName();
        }
        if (!map.containsKey(name)) {
            throw new IllegalArgumentException("缺少参数" + name);
        }
        return SerializerUtil.convert(parameter.getType(), map.get(name));
    }
}
