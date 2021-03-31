package boot.core.store;

import io.netty.handler.codec.http.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author cyx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HandlerMethod {

    /**
     *
     */
    private Method method;

    /**
     *
     */
    private Map<String, String> pathVariblesMap;

    /**
     *
     */
    private Map<String, String> requestParamsMap;

    /**
     * RequestBody
     */
    private String body;

    public void init(String url, HttpMethod httpMethod) {
        Map<String, String> urlMap = UrlAndMethodMapping.getUrlMapByHttpMethod(httpMethod);
        Map<String, Method> methodMap = UrlAndMethodMapping.getMethodMapByHttpMethod(httpMethod);
        for (Map.Entry<String, String> entry : urlMap.entrySet()) {
            Pattern pattern = Pattern.compile(entry.getKey());
            if (pattern.matcher(url).find()) {
                setMethod(methodMap.get(entry.getKey()));
                buildPathVariblesMap(url, entry.getValue());
                break;
            }
        }
        if (getMethod() == null) {
            throw new IllegalArgumentException("no Method match");
        }
    }

    /**
     * xx/{id} -> xx/id
     * xx/id -> xx/1
     * xx -> xx  id -> 1
     */
    public void buildPathVariblesMap(String path, String origin) {
        Map<String, String> map = new HashMap<>();
        origin = origin.replace("{", "");
        origin = origin.replace("}", "");
        String[] urlArr = path.split("/");
        String[] originArr = origin.split("/");
        for (int i = 0; i < urlArr.length; i++) {
            map.put(originArr[i], urlArr[i]);
        }
        setPathVariblesMap(map);
    }
}
