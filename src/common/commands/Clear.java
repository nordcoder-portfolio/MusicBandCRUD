package common.commands;

import common.commands.command_util.Request;
import common.commands.command_util.Response;
import common.util.NotAllowedToUseCommandException;

import java.sql.SQLException;

import static common.util.Util.checkAuthorization;
import static server.db.DatabaseOperations.isSuperuser;

public class Clear extends AbstractCommand {
    @Override
    protected Response executeCommand(Request request) {
        return request.getReceiver().clear();
    }

    @Override
    protected void allowedToExecute(Request request) throws NotAllowedToUseCommandException {
        try {
            if (!(checkAuthorization(request.getCard()) || !isSuperuser(request.getCard().getUserId()))) {
                throw new NotAllowedToUseCommandException("Only superusers can execute this command");
            }
        } catch (SQLException e) {
            throw new NotAllowedToUseCommandException("unable to check allowance: " + e.getMessage());
        }
    }

    @Override
    public String getCommandDescription() {
        return "очистить коллекцию";
    }

    @Override
    public String getCommandName() {
        return "clear";
    }
}
