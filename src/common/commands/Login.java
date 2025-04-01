package common.commands;

import common.commands.command_util.Request;
import common.commands.command_util.Response;
import common.util.LoggingIn;

import java.sql.SQLException;

import static common.util.LoggingIn.EXISTS;
import static common.util.Util.generateSHA512Hash;
import static server.db.DatabaseOperations.authenticateUserCheck;

public class Login extends AbstractCommand {
    @Override
    public Response executeCommand(Request request) {
        try {
            LoggingIn accountExists = authenticateUserCheck(request.getArgs().get(0), generateSHA512Hash(request.getArgs().get(1)));
            Response response = new Response(accountExists == EXISTS ? "Successfully logged in" : "Invalid username or password");
            response.setLoginStatus(accountExists);
            return response;
        } catch (SQLException e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    protected void allowedToExecute(Request request) {
    }

    @Override
    public String getCommandDescription() {
        return "log in the system";
    }

    @Override
    public String getCommandName() {
        return "login";
    }
}
