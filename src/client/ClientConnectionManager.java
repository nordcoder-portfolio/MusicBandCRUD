package client;

import common.AbstractConnectionManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import static client.ClientConsts.*;

public class ClientConnectionManager extends AbstractConnectionManager {
    private InetSocketAddress address;

    public ClientConnectionManager() {
        createConnection();
    }

    public void createConnection() {
        try {
            channel = DatagramChannel.open();
            channel.configureBlocking(false);
            address = new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT);
            buffer = ByteBuffer.allocate(BUFFER_SIZE);
        } catch (IOException e) {
            System.out.println("failed to create connection");
        }
    }

    public void send() throws IOException {
        send(address);
    }
}
