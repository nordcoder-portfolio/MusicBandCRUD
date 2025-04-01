package common.commands;

import common.commands.command_util.Request;
import common.commands.command_util.Response;
import common.entity.Worker;

public class AddIfMax extends AbstractNeedToRegisterCommand implements NeedsWorkerCommand {
    private Worker worker;

    @Override
    public Response executeCommand(Request request) {
        if (worker.countToCompare() > request.getReceiver().getMaximumValue()) {
            return request.getReceiver().addWorker(worker, request.getCard().getUserId());
        }
        return new Response("too small :(");
    }

    @Override
    public String getCommandDescription() {
        return "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }

    @Override
    public String getCommandName() {
        return "add_if_max {element}";
    }

    @Override
    public void setWorker(Worker w) {
        worker = w;
    }
}
