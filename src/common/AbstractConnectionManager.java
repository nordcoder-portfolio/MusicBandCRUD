package common;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class AbstractConnectionManager {
    protected DatagramChannel channel;
    protected ByteBuffer buffer;


    public void send(InetSocketAddress address) throws IOException {
        channel.send(buffer, address);
    }

    public void fillBuffer(byte[] bytes) {
        buffer.put(bytes);
    }

    public void flipBuffer() {
        buffer.flip();
    }

    public void clearBuffer() {
        buffer.clear();
    }


    public boolean responseReceived() {
        try {
            return receiveResponse() != null;
        } catch (IOException e) {
            System.out.println("error occurred while receiving response: " + e.getMessage());
            return false;
        }
    }

    public InetSocketAddress receiveResponse() throws IOException {
        return (InetSocketAddress) channel.receive(buffer);
    }

    public byte[] getByteArray() {
        return buffer.array();
    }
}