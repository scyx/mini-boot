package boot.httpServer;

import boot.httpHandlerFactory.HttpHandlerFactory;
import boot.httpHandlerFactory.IHttpHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.AsciiString;

import java.lang.reflect.InvocationTargetException;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;


public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final String FAVICON_ICO = "/favicon.ico";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) {
        if (fullHttpRequest.uri().equals(FAVICON_ICO)) {
            return;
        }
        IHttpHandler httpHandler = HttpHandlerFactory.getHandlerByHttpMethod(fullHttpRequest.method());
        FullHttpResponse response = httpHandler.handle(fullHttpRequest);

        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }


}