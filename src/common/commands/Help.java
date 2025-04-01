package common.commands;

import common.commands.command_util.Request;
import common.commands.command_util.Response;

public class Help extends AbstractCommand {
    @Override
    protected Response executeCommand(Request request) {
        String help = request.getReceiver().getHelp();
        return new Response(help);
    }

    @Override
    protected void allowedToExecute(Request request) {
    }

    @Override
    public String getCommandDescription() {
        return "вывести справку по доступным командам";
    }

    @Override
    public String getCommandName() {
        return "help";
    }
}
