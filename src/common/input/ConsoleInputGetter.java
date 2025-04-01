package common.input;

import java.util.Scanner;

public class ConsoleInputGetter implements InputGetter {
    private Scanner in;

    public ConsoleInputGetter() {
        in = new Scanner(System.in);
    }

    @Override
    public String getNextLine() {
        return in.nextLine();
    }

    @Override
    public boolean hasNextLine() {
        return in.hasNextLine();
    }

    @Override
    public boolean isFile() {
        return false;
    }
}
