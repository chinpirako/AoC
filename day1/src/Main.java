import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws Exception {
        List<Integer> values = readFile();
        long startTime = System.nanoTime();
        System.out.println("Part one: " + partOne(values));
        System.out.println("Part two: " + partTwo(values));
        long stopTime = System.nanoTime();
        System.out.println("Duration : " + TimeUnit.NANOSECONDS.toMillis(stopTime - startTime));

    }

    private static List<Integer> readFile() throws IOException {
        return Files.readAllLines(Paths.get("input.txt")).stream().map((i) -> Integer.parseInt(i)).collect(Collectors.toList());
    }

    private static int partOne(List<Integer> values) {
        return IntStream.range(1, values.size())
                .mapToObj(i -> new AbstractMap.SimpleEntry<>(values.get(i - 1), values.get(i)))
                .map(b -> b.getKey() < b.getValue() ? 1 : 0)
                .reduce(0, (a, b) -> a = a + b);
    }

    private static int partTwo(List<Integer> values) {
        return partOne(IntStream.range(2, values.size())
                .mapToObj(i -> Arrays.asList(values.get(i - 2), values.get(i - 1), values.get(i)))
                .map(l -> l.stream().mapToInt(Integer::intValue).sum())
                .collect(Collectors.toList()));
    }
}