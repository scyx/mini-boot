package boot.httpHandler.resolver;

import boot.annotation.mvc.RequestParam;
import boot.core.store.HandlerMethod;
import boot.util.SerializerUtil;

import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * @author cyx
 */
public class RequestParamResolver implements ParamterResolver{

    @Override
    public Object resolve(HandlerMethod handlerMethod, Parameter parameter) {
        RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
        String name = parameter.getName();
        if (requestParam != null && !"".equals(requestParam.value())) {
            name = requestParam.value();
        }
        Map<String,String> map = handlerMethod.getRequestParamsMap();
        if (!map.containsKey(name)) {
            throw new IllegalArgumentException("缺少参数" + name);
        }
        return SerializerUtil.convert(parameter.getType(),map.get(name));
    }
}
