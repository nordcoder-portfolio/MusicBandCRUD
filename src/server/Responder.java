package server;

import common.commands.command_util.Response;
import common.util.CustomPair;
import common.util.Serializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;

public class Responder {
    private final Serializer<Response> responseSerializer = new Serializer<>();
    private final ServerConnectionManager connectionManager;

    public Responder(ServerConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void respondToClient(CustomPair<DatagramChannel, InetSocketAddress> clientData, Response response) {
        try {
            connectionManager.fillBuffer(responseSerializer.serialize(response));
            connectionManager.flipBuffer();
            connectionManager.sendOnChannelAndAddress(clientData.getFirst(), clientData.getSecond());
            connectionManager.clearBuffer();
        } catch (IOException e) {
            System.out.println("failed to send response to client: " + e.getMessage());
        }
    }
}
