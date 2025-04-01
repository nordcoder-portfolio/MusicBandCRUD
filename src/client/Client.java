package client;

import common.commands.command_util.Response;
import common.util.AccountCard;

import java.util.NoSuchElementException;

import static common.util.Util.handleLoginCommand;

public class Client {
    private final AccountCard accountCard;
    private final CommandSender sender;
    private final ResponseReceiver responseReceiver;

    public Client() {
        accountCard = new AccountCard();
        ClientConnectionManager connectionManager = new ClientConnectionManager();
        sender = new CommandSender(connectionManager);
        responseReceiver = new ResponseReceiver(connectionManager);

        runMainLoop();
    }

    public void runMainLoop() {
        while (true) {
            System.out.print(">>>");
            try {
                sender.sendNextCommand(accountCard);
                handleResponse(responseReceiver.getResponse());
            } catch (NoSuchElementException e) {
                break;
            }
        }
    }

    public void handleResponse(Response response) {
        handleLoginCommand(response, accountCard);
        System.out.println(response.getText());
    }
}
