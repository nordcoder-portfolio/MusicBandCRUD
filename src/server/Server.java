package server;

import common.commands.Command;
import common.commands.command_util.Request;
import common.commands.command_util.Response;
import common.input.ConsoleInputGetter;
import common.input.InputParser;
import common.util.AccountCard;
import common.util.CustomPair;

import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.util.NoSuchElementException;
import java.util.concurrent.*;

import static common.util.Util.*;
import static server.db.DatabaseOperations.getAllWorkersFromDB;

public class Server {
    private ServerConnectionManager connectionManager;
    private QueryHandler queryHandler;
    private Responder responder;

    private Receiver receiver;
    private AccountCard accountCard;

    private ExecutorService connectionThreadPool;
    private ExecutorService queryThreadPool;
    private ExecutorService responderThreadPool;

    public Server() {
        init();
        startSeverConsoleThread();
        runServer();
    }

    private void init() {
        initCollection();
        connectionManager = new ServerConnectionManager();
        queryHandler = new QueryHandler(connectionManager);
        responder = new Responder(connectionManager);
        accountCard = new AccountCard();

        connectionThreadPool = Executors.newCachedThreadPool();
        queryThreadPool = Executors.newCachedThreadPool();
        responderThreadPool = Executors.newFixedThreadPool(5);
    }

    private void runServer() {
        while (true) {
            Future<SelectionKey> futureKey = connectionThreadPool.submit(() -> connectionManager.getNextSelectionKey());
            Future<CustomPair<CustomPair<DatagramChannel, InetSocketAddress>, Response>> queryExecutorData = queryThreadPool.submit(() -> {
                CustomPair<DatagramChannel, InetSocketAddress> clientData = queryHandler.getClientDataAndFillBuffer(getFutureData(futureKey));
                Response response = queryHandler.executeCommandFromBuffer(receiver);
                return new CustomPair<>(clientData, response);
            });
            CustomPair<CustomPair<DatagramChannel, InetSocketAddress>, Response> clientDataAndResponse = getFutureData(queryExecutorData);
            responderThreadPool.submit(() -> responder.respondToClient(clientDataAndResponse.getFirst(), clientDataAndResponse.getSecond()));
        }
    }

    private <T> T getFutureData(Future<T> future) {
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            System.out.println("multithread exception, can't get data: " + e.getMessage());
            return null;
        }
    }

    private void initCollection() {
        receiver = new Receiver();
        try {
            receiver.setCollection(getAllWorkersFromDB());
        } catch (Exception e) {
            System.out.println("failed to load data from database: " + e.getMessage());
        }
    }

    private void startSeverConsoleThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runServerConsole();
            }
        }).start();
    }

    private void runServerConsole() {
        InputParser commandParser = new InputParser(new ConsoleInputGetter(), getServerCommands());
        while (true) {
            System.out.print(">>>");
            try {
                CustomPair<Command, Request> command = readHandleCommand(commandParser, accountCard);
                command.getSecond().setReceiver(receiver);
                queryHandler.handleServerConsoleCommand(command);
            } catch (NoSuchElementException e) {
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Receiver getReceiver() {
        return receiver;
    }
}
