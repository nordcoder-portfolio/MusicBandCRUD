package common.input;

import java.io.File;
import java.util.Scanner;

public class FileInputGetter implements InputGetter {
    private Scanner in;
    public FileInputGetter(String path) {
        try {
            in = new Scanner(new File(path));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
        return true;
    }
}
