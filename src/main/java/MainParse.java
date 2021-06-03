import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MainParse {

    public static void main(String[] args) {
        List<Set<String>> groupedData = attemptToParse(args[0]);
        groupedData.sort(Comparator.comparingInt(Set::size));
        Collections.reverse(groupedData);
        try {
            createFile(groupedData);
            System.out.println("Файл создан");
        } catch (IOException e) {
            System.out.println("Не удалось создать файл");
        }
    }

    private static List<Set<String>> attemptToParse(String arg) {
        File file = new File(arg);
        try {
            return Parse.parseAndGrouping(file);
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла");
            System.exit(500);
        }
        return new LinkedList<>();
    }

    private static void createFile(List<Set<String>> groupedData) throws IOException {
        FileWriter data = new FileWriter("../groupsForPeacockTeam.csv", false);
        data.write("Групп с более чем одним элементом " +
                groupedData.stream()
                .filter(set -> set.size() > 1)
                .count() + "\n");
        int index = 1;
        for (Set<String> group : groupedData) {
            data.write("Группа " + index + "\n");
            for (String str : group) {
                data.write(str + "\n");
            }
            data.write("\n");
            index++;
        }
        data.write("Всего групп " + groupedData.size());
        data.close();
    }
}
