package common.commands;

import common.commands.command_util.Request;
import common.util.NotAllowedToUseCommandException;

import static common.util.Util.checkAuthorization;

public abstract class AbstractNeedToRegisterCommand extends AbstractCommand {
    @Override
    protected void allowedToExecute(Request request) throws NotAllowedToUseCommandException {
        checkIfAuthorized(request);
    }

    protected void checkIfAuthorized(Request request) throws NotAllowedToUseCommandException {
        if (!checkAuthorization(request.getCard())) {
            throw new NotAllowedToUseCommandException("You have to log in to execute this command");
        }
    }

}
