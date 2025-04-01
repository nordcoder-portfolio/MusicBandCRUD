package common.commands;

import common.commands.command_util.Request;
import common.commands.command_util.Response;
import common.util.NotAllowedToUseCommandException;

public abstract class AbstractCommand implements Command {
    @Override
    public Response execute(Request request) {
        addToHistory(request);
        try {
            allowedToExecute(request);
        } catch (NotAllowedToUseCommandException e) {
            return new Response("You are not allowed to execute this command: " + e.getMessage());
        }
        return executeCommand(request);
    }

    private void addToHistory(Request request) {
        request.getReceiver().addCommandHistoryRecord(this);
    }
    protected abstract Response executeCommand(Request request);
    protected abstract void allowedToExecute(Request request) throws NotAllowedToUseCommandException;
}
