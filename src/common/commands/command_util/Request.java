package common.commands.command_util;

import common.util.AccountCard;
import server.Receiver;

import java.io.Serializable;
import java.util.List;

public class Request implements Serializable {
    private AccountCard card;
    private List<String> args;
    private Receiver receiver;

    public Request(AccountCard card, List<String> args) {
        this.card = card;
        this.args = args;
    }

    public AccountCard getCard() {
        return card;
    }

    public List<String> getArgs() {
        return args;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }
}
