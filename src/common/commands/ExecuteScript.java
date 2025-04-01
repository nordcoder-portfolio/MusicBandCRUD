package common.commands;

import common.commands.command_util.Request;
import common.commands.command_util.Response;

public class ExecuteScript extends AbstractCommand {
    @Override
    protected Response executeCommand(Request request) {
        boolean check = request.getReceiver().executeScript(request.getArgs().get(0), request.getCard());
        return new Response(check ? "Executed!" : "bad script");
    }

    @Override
    protected void allowedToExecute(Request request) {
    }

    @Override
    public String getCommandDescription() {
        return "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }

    @Override
    public String getCommandName() {
        return "execute_script file_name";
    }
}
