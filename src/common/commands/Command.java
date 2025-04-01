package common.commands;

import common.commands.command_util.Request;
import common.commands.command_util.Response;

import java.io.Serializable;

public interface Command extends Serializable {
    Response execute(Request request);
    String getCommandDescription();
    String getCommandName();
}
