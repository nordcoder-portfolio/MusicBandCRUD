package common.input;

import common.commands.Command;
import common.entity.*;
import common.util.CustomPair;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;

import static common.util.Consts.Predicates.*;

public class InputParser {
    private final Map<String, Command> commandMap;
    private final InputGetter inputGetter;

    public InputParser(InputGetter inputGetter, Map<String, Command> commandMap) {
        this.commandMap = commandMap;
        this.inputGetter = inputGetter;
    }

    public CustomPair<Command, List<String>> nextCommand() throws Exception {
        String[] command = inputGetter.getNextLine().split("\\s+");
        String commandName = command[0];
        if (commandName.equals("exit")) {
            System.exit(0);
        }
        String[] args = Arrays.copyOfRange(command, 1, command.length);
        Command commandObj = commandMap.get(commandName);
        if (commandObj == null) {
            throw new Exception("There's no command named " + commandName);
        } else {
            return new CustomPair<>(commandObj, Arrays.stream(args).toList());
        }
    }

    public boolean hasNextLine() {
        return inputGetter.hasNextLine();
    }

    public void printIfNotFile(String query) {
        if (!inputGetter.isFile()) {
            System.out.println(query);
        }
    }

    public void printEnumValuesIfNotFile(Enum<?>[] names) {
        for (Enum<?> name : names) {
            printIfNotFile(name.name());
        }
    }

    public String readOneData(String query, boolean necessary, String type, String additionalPredicate, Predicate<String> typeCheck, Predicate<String> additionalCondition) throws Exception {
        while (true) {
            printIfNotFile(query);

            String input = inputGetter.getNextLine();
            if (Objects.equals(input, "") && !necessary) {
                return null;
            }

            if (typeCheck.test(input) && additionalCondition.test(input)) {
                return input;
            } else {
                writeMessageAboutWrongInput(type, additionalPredicate);
                if (inputGetter.isFile()) {
                    throw new Exception("wrong script input");
                }
            }
        }
    }

    public void writeMessageAboutWrongInput(String type, String additionalPredicate) {
        if (Objects.equals(additionalPredicate, "")) {
            System.out.println("Invalid input. Input must be: " + type);
        } else {
            System.out.println("Invalid input. Input must be: " + type + " and " + additionalPredicate);
        }
    }

    public Worker readWorker() throws Exception {
        printIfNotFile("Reading Worker.");

        String name = readOneData("Enter name: ", true, "string", "", alwaysTrue, alwaysTrue);
        Coordinates coordinates = readCoordinates();
        double salary = Double.parseDouble(readOneData("Enter salary: ", true, "double","must be greater than zero", isDouble, greaterZero));
        String endDateString = readOneData("Enter end date (mm-dd-yyyy): ", false, "LocalDate", "format: (mm-dd-yyyy)", isDate, alwaysTrue);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate endDate = endDateString == null ? null : LocalDate.parse(endDateString, formatter);

        Position position = readPosition();
        Status status = readStatus();
        Person person = readPerson();
        return new Worker(name, coordinates, LocalDateTime.now(), salary, endDate, position, status, person);
    }

    public Person readPerson() throws Exception {
        printIfNotFile("Reading Person.");
        int height = Integer.parseInt(readOneData("Enter height: ", true, "integer", "must be greater that zero", isInteger, greaterZero));
        String weightString = readOneData("Enter weight: ", false, "integer", "must be greater that zero", isInteger, greaterZero);
        Double weight = weightString == null ? null : Double.parseDouble(weightString);
        Color color = readColor();
        Country country = readCountry();
        return new Person(height, weight, color, country);
    }

    public List<String> readUsernamePassword() throws Exception {
        ArrayList<String> res = new ArrayList<>();
        res.add(readOneData("Enter username: ", true, "string", "must be longer than 2 symbols", alwaysTrue, longerThanTwo));
        res.add(readOneData("Enter password: ", true, "string", "must be longer than 5 symbols", alwaysTrue, longerThanFive));
        return res;
    }

    private Coordinates readCoordinates() throws Exception {
        printIfNotFile("Reading Coordinates.");
        double x = Double.parseDouble(readOneData("Enter X coordinate: ", true, "double", "must be less or equal than 644", isDouble, coordinatesXPredicate));
        long y = Long.parseLong(readOneData("Enter Y coordinate: ", true, "long", "must be greater than -154", isLong, coordinatesYPredicate));
        return new Coordinates(x, y);
    }

    private Color readColor() throws Exception {
        printIfNotFile("Reading Color. Possible values: ");
        printEnumValuesIfNotFile(Color.values());
        String val = readOneData("Enter color: ", false, "color", "", isColor, alwaysTrue);
        return val == null ? null : Color.valueOf(val);
    }

    private Country readCountry() throws Exception {
        printIfNotFile("Reading Country. Possible values: ");
        printEnumValuesIfNotFile(Country.values());
        return Country.valueOf(readOneData("Enter country: ", true, "country", "", isCountry, alwaysTrue));
    }

    private Position readPosition() throws Exception {
        printIfNotFile("Reading Position. Possible values: ");
        printEnumValuesIfNotFile(Position.values());
        return Position.valueOf(readOneData("Enter position: ", true, "Position", "", isPosition, alwaysTrue));
    }

    private Status readStatus() throws Exception {
        printIfNotFile("Reading Status. Possible values: ");
        printEnumValuesIfNotFile(Status.values());
        String val = readOneData("Enter status: ", false, "Status", "", isStatus, alwaysTrue);
        return val == null ? null : Status.valueOf(val);
    }
}
