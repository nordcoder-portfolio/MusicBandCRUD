package common.commands;

import common.commands.command_util.Request;
import common.commands.command_util.Response;
import server.db.DatabaseOperations;

import java.sql.SQLException;

import static common.util.Util.generateSHA512Hash;

public class Register extends AbstractCommand {
    @Override
    public Response executeCommand(Request request) {
        try {
            String passwordHash = generateSHA512Hash(request.getArgs().get(1));
            DatabaseOperations.insertUser(request.getArgs().get(0), passwordHash);
        } catch (SQLException e) {
            return new Response(e.getMessage());
        }
        return new Response("ok");
    }

    @Override
    protected void allowedToExecute(Request request) {
    }

    @Override
    public String getCommandDescription() {
        return "create an account in system";
    }

    @Override
    public String getCommandName() {
        return "register";
    }
}
