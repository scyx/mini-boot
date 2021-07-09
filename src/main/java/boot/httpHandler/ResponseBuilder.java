package boot.httpHandler;

import boot.core.ioc.BeansFactory;
import boot.core.store.HandlerMethod;
import boot.httpHandler.resolver.ParamterResolver;
import boot.httpHandler.resolver.ParamterResolverFactory;
import boot.util.ReflectionUtil;
import boot.util.SerializerUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.AsciiString;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_ENCODING;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author cyx
 *
 */
@Slf4j
public class ResponseBuilder {

    private static final AsciiString CONNECTION = AsciiString.cached("Connection");
    private static final AsciiString KEEP_ALIVE = AsciiString.cached("keep-alive");
    private static final AsciiString CONTENT_TYPE = AsciiString.cached("Content-Type");

    public FullHttpResponse buildSuccessfulResponse(HandlerMethod handlerMethod) {
        Method method = handlerMethod.getMethod();
        String beanName = ReflectionUtil.getBeanName(handlerMethod.getMethod().getDeclaringClass());
        Object obj = BeansFactory.SINGLETONS.get(beanName);
        List<Object> params = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            ParamterResolver paramterResolver = ParamterResolverFactory.getParamterResolver(parameter);
            if (paramterResolver == null) throw new RuntimeException("no such parameterResolver match");
            Object object = paramterResolver.resolve(handlerMethod,parameter);
            params.add(object);
        }
        try {
            Object result = method.invoke(obj,params.toArray());
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
        httpResponse.headers().set(CONTENT_TYPE,"text/html;charset=UTF-8");
    }

    public FullHttpResponse buildNoMethodResponse() {
        byte[] content = "no mapping method".getBytes(StandardCharsets.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(content));
        addHeader(response);
        return response;
    }
}
