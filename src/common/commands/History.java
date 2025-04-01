package common.commands;

import common.commands.command_util.Request;
import common.commands.command_util.Response;

public class History extends AbstractCommand {
    @Override
    public Response executeCommand(Request request) {
        String history = request.getReceiver().getHistory();
        return new Response(history);
    }

    @Override
    protected void allowedToExecute(Request request) {
    }

    @Override
    public String getCommandDescription() {
        return "вывести последние 14 команд (без их аргументов)";
    }

    @Override
    public String getCommandName() {
        return "history";
    }
}
