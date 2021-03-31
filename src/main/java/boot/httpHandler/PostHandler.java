package boot.httpHandler;


import boot.core.store.HandlerMethod;
import boot.util.UrlUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import example.controller.User;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Parameter;
import java.nio.charset.StandardCharsets;

/**
 * @author cyx
 */
@Slf4j
public class PostHandler implements IHttpHandler {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    ResponseBuilder responseBuilder = new ResponseBuilder();

    @Override
    public FullHttpResponse handle(FullHttpRequest httpRequest) {

        String url = httpRequest.uri();
        String path = UrlUtil.getRequestPath(url);
        HandlerMethod handlerMethod = new HandlerMethod();
        handlerMethod.setBody(httpRequest.content().toString(StandardCharsets.UTF_8));
        handlerMethod.init(url,path,httpRequest.method());

        return responseBuilder.buildeSuccessfulResponse(handlerMethod);
    }
}
