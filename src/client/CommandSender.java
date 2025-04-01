package client;

import common.commands.Command;
import common.commands.command_util.Request;
import common.input.ConsoleInputGetter;
import common.input.InputParser;
import common.util.AccountCard;
import common.util.CustomPair;
import common.util.Serializer;

import java.io.IOException;
import java.util.NoSuchElementException;

import static common.util.Util.*;

public class CommandSender {
    private final InputParser inputParser = new InputParser(new ConsoleInputGetter(), getClientCommands());
    private final Serializer<CustomPair<Command, Request>> commandSerializer = new Serializer<>();
    private final ClientConnectionManager connectionManager;

    public CommandSender(ClientConnectionManager parent) {
        connectionManager = parent;
    }

    public void sendNextCommand(AccountCard card) throws NoSuchElementException {
        try {
            sendCommand(readHandleCommand(inputParser, card));
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException();
        } catch (Exception e) {
            System.out.println("unable to send command: " + e.getMessage());
        }

    }

    public void sendCommand(CustomPair<Command, Request> command) {
        try {
            connectionManager.fillBuffer(commandSerializer.serialize(command));
            connectionManager.flipBuffer();
            connectionManager.send();
            connectionManager.clearBuffer();
        } catch (IOException e) {
            System.out.println("unable to send command: " + e.getMessage());
        }

    }
}
