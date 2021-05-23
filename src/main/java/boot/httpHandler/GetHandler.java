package boot.httpHandler;

import boot.core.store.HandlerMethod;
import boot.util.UrlUtil;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import lombok.extern.slf4j.Slf4j;


/**
 * @author cyx
 */
@Slf4j
public class GetHandler implements IHttpHandler {

    ResponseBuilder responseBuilder = new ResponseBuilder();

    @Override
    public FullHttpResponse handle(FullHttpRequest httpRequest) {
        // 请求的url
        String url = httpRequest.uri();
        log.info("request originUrl is {}", url);
        // 不包含参数的url
        String path = UrlUtil.getRequestPath(url);
        log.info("request path is {}", path);

        HandlerMethod handlerMethod = new HandlerMethod();
        handlerMethod.init(url, path, httpRequest.method());

        return responseBuilder.buildSuccessfulResponse(handlerMethod);
    }
}