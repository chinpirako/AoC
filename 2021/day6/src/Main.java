import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        Map<Integer, Long> values = readFile();
        long startTime = System.nanoTime();
        System.out.println("Part one: " + partOne(values));
        //values = readFile();
        System.out.println("Part two: " + partTwo(values));
        long stopTime = System.nanoTime();
        System.out.println("Duration : " + (double) TimeUnit.NANOSECONDS.toMillis(stopTime - startTime));

    }

    private static Map<Integer, Long> readFile() throws IOException {
        return Arrays.stream(Files.readAllLines(Paths.get("input.txt"))
                .get(0)
                .split(","))
                .map(Integer::parseInt)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private static long partOne(Map<Integer, Long> values) {
        return countFishesAfter(values, 80);
    }

    private static long partTwo(Map<Integer, Long> values) {
        return countFishesAfter(values, 256);
    }

    private static long countFishesAfter(Map<Integer, Long> values, int days) {
        Map<Integer, Long> initial = new HashMap<>(values);
        Map<Integer, Long> newState = new HashMap<>();
        for (long i = 0; i < days; i++) {
            for (int k = 0; k <= 8; k++) {
                newState.put(k, 0L);
                if (!initial.containsKey(k)) initial.put(k, 0L);
            }
            long newFishes = 0;
            for (int j = 0; j <= 8; j++) {
                if (j == 0) {
                    newState.put(j, 0L);
                    newFishes += initial.get(j);
                } else {
                    newState.put(j - 1, newState.get(j) + initial.get(j));
                }
            }
            newState.put(8, newFishes);
            newState.put(6, newFishes + newState.get(6));
            initial = new HashMap<>(newState);
        }

        long result = 0;
        for (long count: initial.values()) {
            result += count;
        }
        return result;
    }
}