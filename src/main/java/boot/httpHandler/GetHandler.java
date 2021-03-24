package boot.httpHandler;

import boot.core.ioc.BeansFactory;
import boot.core.store.UrlAndMethodMapping;
import boot.util.ReflectionUtil;
import boot.util.UrlUtil;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;


/**
 * @author cyx
 */
@Slf4j
public class GetHandler implements IHttpHandler {

    ResponseBuilder responseBuilder = new ResponseBuilder();

    @Override
    public FullHttpResponse handle(FullHttpRequest httpRequest) {
        String url = httpRequest.uri();
        log.info("request originUrl is {}",url);
        String path = UrlUtil.getRequestPath(url);
        Map<String,Method> map = UrlAndMethodMapping.getMapByHttpMethod(httpRequest.method());
        System.out.println(map.toString());
//        if (!map.containsKey(url)) {
//            throw new RuntimeException("url not Exist");
//        }
        Method method = map.get(path);
        if (method == null) {
            return responseBuilder.buildeNoMethodResponse();
        }
        String beanName = ReflectionUtil.getBeanName(method.getDeclaringClass());
        Object obj = BeansFactory.BEANS.get(beanName);
        return responseBuilder.buildeSuccessfulResponse(method,obj);
    }
}