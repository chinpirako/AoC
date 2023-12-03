import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    record Coords(Long x, Long y) {
    }

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
        final List<Long> partNumbers = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            var currentStartIndex = 0;
            final List<Long> numbers = parsePartNumbers(values.get(i));
            for (Long number : numbers) {
                final var start = values.get(i).indexOf(number.toString(), currentStartIndex) - 1;
                final var end = start + number.toString().length() + 1;
                currentStartIndex = end - 1;
                for (int j = -1; j < 2; j++) {
                    if (i + j < 0 || i + j >= values.size()) {
                        continue;
                    }
                    final var line = values.get(i + j);
                    for (int k = start; k <= end; k++) {
                        if (k < 0 || k >= line.length()) {
                            continue;
                        }
                        if (line.charAt(k) != '.' && !List.of('0','1','2','3','4','5','6','7','8','9').contains(line.charAt(k))) {
                            partNumbers.add(number);
                        }
                    }
                }
            }
        }
        System.out.println(partNumbers);
        return partNumbers.stream().mapToLong(Long::longValue).sum();
    }

    private static long partTwo(List<String> values) {
        final HashMap<Coords, List<Integer>> partNumbers = new HashMap<>();
        for (int i = 0; i < values.size(); i++) {
            var currentStartIndex = 0;
            final List<Long> numbers = parsePartNumbers(values.get(i));
            for (Long number : numbers) {
                final var start = values.get(i).indexOf(number.toString(), currentStartIndex) - 1;
                final var end = start + number.toString().length() + 1;
                currentStartIndex = end - 1;
                for (int j = -1; j < 2; j++) {
                    if (i + j < 0 || i + j >= values.size()) {
                        continue;
                    }
                    final var line = values.get(i + j);
                    for (int k = start; k <= end; k++) {
                        if (k < 0 || k >= line.length()) {
                            continue;
                        }
                        if (line.charAt(k) == '*') {
                            final var coords = new Coords((long) k, (long) i + j);
                            if (!partNumbers.containsKey(coords)) {
                                partNumbers.put(coords, new ArrayList<>());
                            }
                            partNumbers.get(coords).add(number.intValue());
                        }
                    }
                }
            }
        }
        System.out.println(partNumbers);
        return partNumbers.values()
                .stream()
                .filter(list -> list.size() > 1)
                .mapToLong(list -> list.stream().mapToLong(Integer::longValue).reduce(1, (a, b) -> a * b))
                .sum();
    }

    private static List<Long> parsePartNumbers(String line) {
        // Line like 467..114..
        final List<Long> result = new ArrayList<>();
        Matcher matcher = Pattern.compile("(\\d+)").matcher(line);
        while (matcher.find()) {
            result.add(Long.parseLong(matcher.group(1)));
        }
        return result;
    }
}