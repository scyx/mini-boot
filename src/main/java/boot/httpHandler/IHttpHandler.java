package boot.httpHandler;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @author dell
 */
public interface IHttpHandler {
    /**
     * 处理http请求
     * @param httpRequest
     * @return
     */
    FullHttpResponse handle(FullHttpRequest httpRequest);
}
