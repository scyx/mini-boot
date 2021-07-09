package boot.httpHandler.resolver;

import boot.core.store.HandlerMethod;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Parameter;

/**
 * @author cyx
 */
public class RequestBodyResolver implements ParamterResolver {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object resolve(HandlerMethod handlerMethod, Parameter parameter) {
        String body = handlerMethod.getBody();
        try {
            return objectMapper.readValue(body, parameter.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
