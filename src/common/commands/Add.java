package common.commands;

import common.commands.command_util.Request;
import common.commands.command_util.Response;
import common.entity.Worker;

public class Add extends AbstractNeedToRegisterCommand implements NeedsWorkerCommand {
    private Worker worker = null;
    @Override
    protected Response executeCommand(Request request) {
        return request.getReceiver().addWorker(worker, request.getCard().getUserId());
    }

    @Override
    public String getCommandDescription() {
        return "добавить новый элемент в коллекцию";
    }

    @Override
    public String getCommandName() {
        return "add {element}";
    }

    @Override
    public void setWorker(Worker w) {
        worker = w;
    }

}
