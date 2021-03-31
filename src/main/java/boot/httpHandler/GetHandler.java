package boot.httpHandler;

import boot.core.ioc.BeansFactory;
import boot.core.store.HandlerMethod;
import boot.core.store.UrlAndMethodMapping;
import boot.util.ReflectionUtil;
import boot.util.UrlUtil;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
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
        log.info("request path is {}",path);

        HandlerMethod handlerMethod = new HandlerMethod();
        handlerMethod.init(url,httpRequest.method());

        Map<String,String> paramtersMap = UrlUtil.getParamters(url);
        handlerMethod.setRequestParamsMap(paramtersMap);
//        List<Object> paramtersList = new ArrayList<>();
//        if (!map.containsKey(url)) {
//            throw new RuntimeException("url not Exist");
//        }

        return responseBuilder.buildeSuccessfulResponse(handlerMethod);
    }
}