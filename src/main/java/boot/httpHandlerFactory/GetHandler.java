package boot.httpHandlerFactory;

import boot.core.ioc.BeansFactory;
import boot.core.store.urlRoutes;
import boot.util.ReflectionUtil;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import jdk.nashorn.api.scripting.URLReader;

import java.lang.reflect.Method;
import java.util.Map;


/**
 * @author dell
 */
public class GetHandler implements IHttpHandler {

    ResponseBuilder responseBuilder = new ResponseBuilder();

    @Override
    public FullHttpResponse handle(FullHttpRequest httpRequest) {
        String url = httpRequest.uri();
        System.out.println(url);
        Map<String,Method> map = urlRoutes.getMapByHttpMethod(httpRequest.method());
        System.out.println(map.toString());
        if (!map.containsKey(url)) {
            throw new RuntimeException("url not Exist");
        }
        Method method = map.get(url);
        String beanName = ReflectionUtil.getBeanName(method.getDeclaringClass());
        Object obj = BeansFactory.BEANS.get(beanName);
        return responseBuilder.buildeSuccessfulResponse(method,obj);
    }
}