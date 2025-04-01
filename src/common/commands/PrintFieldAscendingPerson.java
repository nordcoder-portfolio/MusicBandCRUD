package common.commands;

import common.commands.command_util.Request;
import common.commands.command_util.Response;

public class PrintFieldAscendingPerson extends AbstractCommand implements Command {
    @Override
    public Response executeCommand(Request request) {
        String persons = request.getReceiver().getFieldAscendingPerson();
        return new Response(persons);
    }

    @Override
    protected void allowedToExecute(Request request) {
    }

    @Override
    public String getCommandDescription() {
        return "вывести значения поля person всех элементов в порядке возрастания";
    }

    @Override
    public String getCommandName() {
        return "print_field_ascending_person";
    }
}
