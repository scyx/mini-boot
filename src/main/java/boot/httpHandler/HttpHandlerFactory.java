package boot.httpHandler;

import io.netty.handler.codec.http.HttpMethod;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dell
 */
public class HttpHandlerFactory {
    public static final Map<HttpMethod, IHttpHandler> map = new ConcurrentHashMap<>(128);

    static {
        map.put(HttpMethod.GET,new GetHandler());
    }

    public static IHttpHandler getHandlerByHttpMethod(HttpMethod httpMethod) {
        return map.get(httpMethod);
    }

}
