package boot.core.store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author cyx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HandlerMethod {

    private Method method;

    private Map<String,Object> pathVariblesMap;

    private Map<String,Object> requestParamsMap;

    private String body;

//    public void init()
}
