package server;

import common.commands.Command;
import common.commands.command_util.Request;
import common.commands.command_util.Response;
import common.util.CustomPair;
import common.util.Serializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;

import static common.util.Util.handleLoginCommand;


public class QueryHandler {
    private final Serializer<CustomPair<Command, Request>> commandSerializer = new Serializer<>();
    private final ServerConnectionManager connectionManager;

    public QueryHandler(ServerConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public Response executeCommandFromBuffer(Receiver receiver) {
        try {
            CustomPair<Command, Request> command = readPrepareCommandFromBuffer(receiver);
            return executeCommand(command);
        } catch (Exception e) {
            System.out.println("failed to execute command: " + e.getMessage());
            return new Response("failed to execute command: " + e.getMessage());
        }
    }

    private CustomPair<Command, Request> readPrepareCommandFromBuffer(Receiver receiver) throws IOException, ClassNotFoundException {
        connectionManager.flipBuffer();
        CustomPair<Command, Request> command = commandSerializer.deserialize(connectionManager.getByteArray());
        connectionManager.clearBuffer();
        command.getSecond().setReceiver(receiver);
        return command;
    }

    private Response executeCommand(CustomPair<Command, Request> command) {
        Response response = command.getFirst().execute(command.getSecond());
        System.out.println("executed command: " + command.getFirst());
        return response;
    }

    public CustomPair<DatagramChannel, InetSocketAddress> getClientDataAndFillBuffer(SelectionKey key) {
        try {
            connectionManager.clearBuffer();
            DatagramChannel dataChannel = (DatagramChannel) key.channel();
            InetSocketAddress clientAddress = connectionManager.receiveResponseOnChannel(dataChannel);
            return new CustomPair<>(dataChannel, clientAddress);
        } catch (IOException e) {
            System.out.println("failed to recieve command: " + e.getMessage());
            return null;
        }

    }

    public void handleServerConsoleCommand(CustomPair<Command, Request> command) {
        Response response = command.getFirst().execute(command.getSecond());
        handleLoginCommand(response, command.getSecond().getCard());
        System.out.println(response.getText());
    }
}
