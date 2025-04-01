package server;

import common.AbstractConnectionManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

import static server.ServerConsts.BUFFER_SIZE;
import static server.ServerConsts.LISTENING_PORT;

public class ServerConnectionManager extends AbstractConnectionManager {
    private Selector selector;


    public ServerConnectionManager() {
        startServer();
    }

    private void startServer() {
        try {
            selector = Selector.open();
            channel = DatagramChannel.open();
            channel.bind(new InetSocketAddress(LISTENING_PORT));
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
            buffer = ByteBuffer.allocate(BUFFER_SIZE);
        } catch (IOException e) {
            System.out.println("failed to start server: " + e.getMessage());
        }
    }

    public SelectionKey getNextSelectionKey() {
        try {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            SelectionKey key = iterator.next();
            iterator.remove();
            return key;
        } catch (IOException e) {
            System.out.println("failed to select key: " + e.getMessage());
            return null;
        }
    }

    public void sendOnChannelAndAddress(DatagramChannel channel, InetSocketAddress address) throws IOException {
        channel.send(buffer, address);
    }

    public InetSocketAddress receiveResponseOnChannel(DatagramChannel ch) throws IOException {
        return (InetSocketAddress) ch.receive(buffer);
    }
}
