package common.commands;

import common.commands.command_util.Request;
import common.commands.command_util.Response;
import common.entity.Worker;

import static common.util.Util.allowedToChangeById;

public class UpdateById extends AbstractNeedToRegisterCommand implements NeedsWorkerCommand {
    private Worker worker;
    @Override
    public Response executeCommand(Request request) {
        try {
            int id = Integer.parseInt(request.getArgs().get(0));
            if (!allowedToChangeById(request.getCard(), id)) {
                return new Response("you are not allowed to update this element");
            }
            request.getReceiver().replaceWorkerById(id, worker, request.getCard().getUserId());
            return new Response("done");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public String getCommandDescription() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public String getCommandName() {
        return "update id {element}";
    }

    @Override
    public void setWorker(Worker w) {
        worker = w;
    }
}
