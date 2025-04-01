package server;

import common.commands.Command;
import common.commands.command_util.Request;
import common.commands.command_util.Response;
import common.entity.Person;
import common.entity.Worker;
import common.input.FileInputGetter;
import common.input.InputParser;
import common.util.AccountCard;
import common.util.CustomPair;
import server.db.DatabaseOperations;

import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static common.util.Util.*;
import static server.db.DatabaseOperations.insertWorkerSql;

// treeset comparing value is id, worker comparator is also id-based
// but to compare workers for commands function countToCompare is used

public class Receiver { // used for collection management and command execution
    private Set<Worker> collection;
    private final ZonedDateTime creationDate;
    private final ArrayList<Command> commandsHistory;

    public Receiver() {
        this.collection = Collections.synchronizedSet(new TreeSet<>(Comparator.comparingDouble(Worker::getId)));
        this.commandsHistory = new ArrayList<>();
        creationDate = ZonedDateTime.now();
    }

    public Response addWorker(Worker worker, int userId) {
        try {
            int id = insertWorkerSql(worker, userId);
            worker.setId(id);
            return new Response(collection.add(worker) ? "added!" : "already exists!");
        } catch (SQLException e) {
            return new Response("failed to add worker: " + e.getMessage());
        }
    }

    public double getMaximumValue() {
        Optional<Worker> maxWorker = collection.stream().max(Comparator.comparingDouble(Worker::getId));
        return maxWorker.map(Worker::countToCompare).orElse(Double.MIN_VALUE);
    }

    public double getMinimumValue() {
        Optional<Worker> minWorker = collection.stream().min(Comparator.comparingDouble(Worker::getId));
        return minWorker.map(Worker::countToCompare).orElse(Double.MAX_VALUE);
    }

    public void setCollection(Set<Worker> collection) {
        this.collection = collection;
    }

    public void addCommandHistoryRecord(Command command) {
        commandsHistory.add(command);
    }

    public String getHistory() {
        return commandsHistory.stream()
                .limit(14)
                .map(Command::getCommandName)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public String getAscending() {
        return collection
                .stream()
                .sorted(Comparator.comparingDouble(Worker::countToCompare))
                .map(Worker::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public String show() {
        return collection.isEmpty() ? "collection's empty" : collection
                .stream()
                .sorted(Comparator.comparingDouble(Worker::getCoordinatesCompareValue))
                .map(Worker::toString)
                .collect(Collectors.joining(System.lineSeparator()));

    }

    public String getInfo() {
        return "type: " + collection.getClass().getName() + System.lineSeparator() +
                "size: " + collection.size() + System.lineSeparator() +
                "creation date: " + creationDate + System.lineSeparator();
    }

    public String getFieldAscendingPerson() {
        return collection.stream()
                .sorted(Comparator.comparingDouble(Worker::getPersonCompareValue))
                .map(worker -> worker.getPerson().toString())
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public Response removeAnyByPerson(Person person, AccountCard card) {
        try {
            for (Worker w : collection) {
                if (w.getPerson().compareTo(person) == 0) {
                    if (allowedToChangeById(card, w.getId())) {
                        DatabaseOperations.deleteWorkerSql(w.getId());
                        collection.remove(w);
                        return new Response("removed!");
                    }
                }
            }
            return new Response("worker with such person not found!");
        } catch (Exception e) {
            return new Response("failed to remove worker with such person: " + e.getMessage());
        }

    }

    public boolean removeWorkerById(Integer id) throws SQLException {
        DatabaseOperations.deleteWorkerSql(id);
        long size = collection.size();
        collection = collection
                .stream()
                .filter(worker -> !Objects.equals(worker.getId(), id))
                .collect(Collectors.toCollection(TreeSet::new));
        return size != collection.size();
    }

    public boolean executeScript(String filePath, AccountCard card) {
        InputParser scriptParser = new InputParser(new FileInputGetter(filePath), getServerCommands());
        while (scriptParser.hasNextLine()) {
            try {
                CustomPair<Command, Request> command = readHandleCommand(scriptParser, card);
                command.getSecond().setReceiver(this);
                System.out.println((command.getFirst().execute(command.getSecond())).getText());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        return true;
    }

    public void replaceWorkerById(Integer id, Worker toAdd, int userId) throws SQLException {
        removeWorkerById(id);
        toAdd.setId(id);
        addWorker(toAdd, userId);
    }

    public String getHelp() {
        return getClientCommands()
                .values()
                .stream()
                .map(command -> (command.getCommandName() + ": " + command.getCommandDescription()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public Response clear()  {
        try {
            DatabaseOperations.clearDataBase();
            setCollection(new TreeSet<>(Comparator.comparingDouble(Worker::countToCompare)));
            return new Response("successfully cleared!");
        } catch (Exception e) {
            return new Response("failed to clear: " + e.getMessage());
        }
    }

    public int size() {
        return collection.size();
    }
}