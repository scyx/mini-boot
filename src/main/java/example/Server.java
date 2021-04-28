package example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketOption;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * @author cyx
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1",10086));
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while(true) {
            selector.select();
            Iterator iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey)iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel) key.channel();

                    SocketChannel socketChannel = serverSocketChannel1.accept();

                    socketChannel.configureBlocking(false);


                    System.out.println("server is acceptable");
//                    socketChannel.write(ByteBuffer.wrap("accept".getBytes(StandardCharsets.UTF_8)));

                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    read(key);
                }
            }
        }
    }

    public static void read(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        socketChannel.read(byteBuffer);

        byte[] array = byteBuffer.array();
        String msg = new String(array);
        System.out.println("服务端收到消息" + msg);
        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
        socketChannel.write(outBuffer);
    }
}
