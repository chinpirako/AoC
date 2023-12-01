import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();
        List<String> values = readFile();
        System.out.println("Part one: " + partOne(values));
        System.out.println("Part two: " + partTwo(values));
        long stopTime = System.nanoTime();
        System.out.println("Duration : " + TimeUnit.NANOSECONDS.toMillis(stopTime - startTime));
    }

    private static List<String> readFile() throws IOException {
        return Files.readAllLines(Paths.get("input.txt"));
    }

    private static long partOne(List<String> values) {
        return compute(values);
    }

    private static Long compute(List<String> values) {
        return values.stream()
                .map(s -> Arrays.stream(s.split(""))
                        .filter(c -> {
                            try {
                                Integer.parseInt(c);
                                return true;
                            } catch (Exception e) {
                                return false;
                            }
                        })
                        .toList())
                .map(l -> l.get(0) + l.get(l.size() - 1))
                .map(Long::parseLong)
                .reduce(0L, Long::sum);
    }

    private static long partTwo(List<String> values) {
        List<String> newValues =
                values.stream()
                        .map(s -> {
                            String newString = s;
                            for (Map.Entry<String, String> entry : OVERLAPS.entrySet()) {
                                newString = newString.replace(entry.getKey(), entry.getValue());
                            }
                            return newString;
                        })
                        .map(s -> {
                            String newString = s;
                            for (Map.Entry<String, Long> entry : WORDS_TO_DIGITS.entrySet()) {
                                newString = newString.replace(entry.getKey(), entry.getValue().toString());
                            }
                            return newString;
                        })
                        .toList();
        return compute(newValues);
    }

    private static final Map<String, Long> WORDS_TO_DIGITS =
            Map.ofEntries(
                    Map.entry("zero", 0L),
                    Map.entry("one", 1L),
                    Map.entry("two", 2L),
                    Map.entry("three", 3L),
                    Map.entry("four", 4L),
                    Map.entry("five", 5L),
                    Map.entry("six", 6L),
                    Map.entry("seven", 7L),
                    Map.entry("eight", 8L),
                    Map.entry("nine", 9L)
            );

    private static final Map<String, String> OVERLAPS = Map.ofEntries(
            Map.entry("zerone", "zeroone"),
            Map.entry("eightwo", "eighttwo"),
            Map.entry("twone", "twoone"),
            Map.entry("nineight", "nineeight"),
            Map.entry("oneight", "oneeight"),
            Map.entry("threeight", "threeeight"),
            Map.entry("eighthree", "eightthree")
    );
}