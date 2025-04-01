package common.commands;

import common.commands.command_util.Request;
import common.commands.command_util.Response;
import common.entity.Worker;

public class AddIfMin extends AbstractNeedToRegisterCommand implements NeedsWorkerCommand {
    private Worker worker = null;

    @Override
    public Response executeCommand(Request request) {
        if (worker.countToCompare() < request.getReceiver().getMinimumValue()) {
            return request.getReceiver().addWorker(worker, request.getCard().getUserId());
        }
        return new Response("too big");
    }

    @Override
    public String getCommandDescription() {
        return "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }

    @Override
    public String getCommandName() {
        return "add_if_min {element}";
    }

    @Override
    public void setWorker(Worker w) {
        worker = w;
    }

}
