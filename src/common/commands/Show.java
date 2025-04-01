package common.commands;

import common.commands.command_util.Request;
import common.commands.command_util.Response;
import common.util.AccountCard;

public class Show extends AbstractCommand{
    @Override
    public Response executeCommand(Request request) {
        String result = "You are " + (request.getCard().getStatus() == AccountCard.Authorization.AUTHORIZED ?
                request.getCard().getUsername() :
                "unauthorized") + request.getCard().getUserId() + System.lineSeparator();
        result += request.getReceiver().show();
        return new Response(result);
    }

    @Override
    protected void allowedToExecute(Request request) {
    }

    @Override
    public String getCommandDescription() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    @Override
    public String getCommandName() {
        return "show";
    }
}
