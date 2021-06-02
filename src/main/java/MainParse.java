import java.io.File;
import java.io.IOException;
import java.util.*;

public class MainParse {

    public static void main(String[] args) {
        long run = System.currentTimeMillis();
        List<Set<String>> groupedData = attemptToParse(args[0]);
        System.out.println(groupedData);
        System.out.println((System.currentTimeMillis() - run) / 1000f);
    }

    private static List<Set<String>> attemptToParse(String arg) {
        File file = new File(arg);
        try {
            return Parse.parseAndGrouping(file);
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла");
        }
        return new LinkedList<>();
    }
}
