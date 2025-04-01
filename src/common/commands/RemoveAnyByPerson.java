package common.commands;

import common.commands.command_util.Request;
import common.commands.command_util.Response;
import common.entity.Person;

public class RemoveAnyByPerson extends AbstractNeedToRegisterCommand implements NeedsPersonCommand {
    private Person person;

    @Override
    public Response executeCommand(Request request) {
        return request.getReceiver().removeAnyByPerson(person, request.getCard());
    }

    @Override
    public String getCommandDescription() {
        return "удалить из коллекции один элемент, значение поля person которого эквивалентно заданному";
    }

    @Override
    public String getCommandName() {
        return "remove_any_by_person person";
    }

    @Override
    public void setPerson(Person p) {
        this.person = p;
    }
}
