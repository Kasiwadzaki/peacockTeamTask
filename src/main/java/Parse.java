import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Parse {

    public static List<Set<String>> parseAndGrouping(File file) throws IOException {
        List<String> input = new LinkedList<>(Files.readAllLines(Paths.get(String.valueOf(file)), StandardCharsets.UTF_8));
        Map<String, List<String>> indexed = indexing(input);
        return grouping(input ,indexed);
    }

    static Map<String, List<String>> indexing(List<String> parsed) {
        Map<String, List<String>> invertedIndexing = new HashMap<>();
        for (String line : parsed) {
            String[] lineArray = line.split(";");
            if (lineArray.length != 3) {
                continue;
            }
            for (String s : lineArray) {
                if (s == null || s.equals("") || s.equals("\"\"")) {
                    continue;
                }
                if (invertedIndexing.containsKey(s)) {
                    invertedIndexing.get(s).add(line);
                    continue;
                }
                invertedIndexing.put(s, new LinkedList<>(List.of(line)));
            }
        }
        return invertedIndexing;
    }

    static List<Set<String>> grouping(List<String> input, Map<String, List<String>> indexed) {
        List<Set<String>> groups = new LinkedList<>();
        Map<String, List<String>> mutableIndexed = new HashMap<>(indexed);
        for (String line : input) {
            Set<String> group = recursiveSearchIntersections(line, mutableIndexed);
            if (group.size() != 0) {
                groups.add(group);
            }
        }
        return groups;
    }

    private static Set<String> recursiveSearchIntersections(String line, Map<String, List<String>> indexing) {
        Set<String> group = new HashSet<>();
        for (String key : line.split(";")) {
            if (indexing.get(key) != null) {
                group.addAll(indexing.get(key));
                indexing.remove(key);
            }
        }
        if (group.size() == 1) {
            return group;
        } else {
            Set<String> subGroup = group.stream()
                    .filter(str -> !str.equals(line))
                    .collect(Collectors.toSet());
            for (String subLine : subGroup) {
                group.addAll(recursiveSearchIntersections(subLine, indexing));
            }
        }
        return group;
    }
}