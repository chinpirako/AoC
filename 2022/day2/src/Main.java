import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws Exception {
        List<String[]> values = readFile();
        long startTime = System.nanoTime();
        System.out.println("Part one: " + partOne(values));
        System.out.println("Part two: " + partTwo(values));
        long stopTime = System.nanoTime();
        System.out.println("Duration : " + TimeUnit.NANOSECONDS.toMillis(stopTime - startTime));
    }

    private static List<String[]> readFile() throws IOException {
        List<String[]> result = new ArrayList<>();
        List<String> input = Files.readAllLines(Paths.get("input.txt"));
        for (String l : input) {
            result.add(l.split(" "));
        }
        return result;
    }

    private static long partOne(List<String[]> values) {
        return computeScore(values, MATCHUPS);
    }

    private static long partTwo(List<String[]> values) {
        return computeScore(values, MATCHUPS_2);
    }

    private static int computeScore(List<String[]> values, Map<String, Map<String, Integer>> matchups) {
        List<Integer> scores = new ArrayList<>();
        values.forEach(v -> scores.add(matchups.get(v[0]).get(v[1])));
        return scores.stream().reduce(0, Integer::sum);
    }

    // A rock, B paper, C scissors
    // X rock, Y paper, Z scissors
    private static final Map<String, Map<String, Integer>> MATCHUPS = Map.of(
            "A", Map.of("X", 4, "Y", 8, "Z", 3),
            "B", Map.of("X", 1, "Y", 5, "Z", 9),
            "C", Map.of("X", 7, "Y", 2, "Z", 6)
    );

    // A rock, B paper, C scissors
    // X lose, Y draw, Z win
    private static final Map<String, Map<String, Integer>> MATCHUPS_2 = Map.of(
            "A", Map.of("X", 3, "Y", 4, "Z", 8),
            "B", Map.of("X", 1, "Y", 5, "Z", 9),
            "C", Map.of("X", 2, "Y", 6, "Z", 7)
    );
}