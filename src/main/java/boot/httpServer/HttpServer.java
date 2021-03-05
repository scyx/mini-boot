package boot.httpServer;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;

public class HttpServer {
    public void run () {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    }
}
