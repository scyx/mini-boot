package boot.httpHandler;

import boot.util.SerializerUtil;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.AsciiString;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author cyx
 *
 */
public class ResponseBuilder {

    private static final AsciiString CONNECTION = AsciiString.cached("Connection");
    private static final AsciiString KEEP_ALIVE = AsciiString.cached("keep-alive");
    private static final AsciiString CONTENT_TYPE = AsciiString.cached("Content-Type");

    public FullHttpResponse buildeSuccessfulResponse(Method method, Object obj) {
        try {
            Object result = method.invoke(obj);
            return buildSuccessfulResponse(result);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public FullHttpResponse buildSuccessfulResponse(Object o) {
        byte[] content = SerializerUtil.serialize(o);
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(content));
        addHeader(response);
        return response;
    }

    public void addHeader(FullHttpResponse httpResponse) {
        httpResponse.headers().setInt(CONTENT_LENGTH, httpResponse.content().readableBytes());
        httpResponse.headers().set(CONNECTION, KEEP_ALIVE);
    }

    public FullHttpResponse buildeNoMethodResponse() {
        byte[] content = "no mapping method".getBytes(StandardCharsets.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(content));
        addHeader(response);
        return response;
    }
}
