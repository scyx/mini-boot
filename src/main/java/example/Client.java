package example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * @author cyx
 */
public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

        socketChannel.connect(new InetSocketAddress("127.0.0.1",10086));
        Selector selector = Selector.open();

        socketChannel.register(selector, SelectionKey.OP_CONNECT);

        while (true) {
            selector.select();

            Iterator iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                SelectionKey selectionKey = (SelectionKey)iterator.next();
                iterator.remove();
                if (selectionKey.isConnectable()) {
                    SocketChannel socketChannel1 = (SocketChannel) selectionKey.channel();
                    if (socketChannel1.isConnectionPending()) {
                        socketChannel1.finishConnect();
                    }
                    socketChannel1.configureBlocking(false);
                    System.out.println("客户端连接服务器成功");
//                    socketChannel1.write(ByteBuffer.wrap(new String("cyx").getBytes()));
                    // 在和服务端连接成功之后，为了可以接收到服务端的信息，需要给通道设置读的权限。
                    socketChannel1.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {
                    read(selectionKey);
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
        System.out.println("客户端收到消息" + msg);
        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
        socketChannel.write(outBuffer);
    }
}
