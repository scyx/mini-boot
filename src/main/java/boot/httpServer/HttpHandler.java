package boot.httpServer;

import boot.httpHandler.HttpHandlerFactory;
import boot.httpHandler.IHttpHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final String FAVICON_ICO = "/favicon.ico";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) {
        if (fullHttpRequest.uri().equals(FAVICON_ICO)) {
            return;
        }
        log.info(fullHttpRequest.toString());
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