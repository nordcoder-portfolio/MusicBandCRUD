package common.commands;

import common.commands.command_util.Request;
import common.commands.command_util.Response;

public class Info extends AbstractCommand {
    @Override
    public Response executeCommand(Request request) {
        String info = request.getReceiver().getInfo();
        return new Response(info);
    }

    @Override
    protected void allowedToExecute(Request request) {
    }

    @Override
    public String getCommandDescription() {
        return "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }

    @Override
    public String getCommandName() {
        return "info";
    }
}
