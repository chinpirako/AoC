import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws Exception {
        List<List<Long>> values = readFile();
        long startTime = System.nanoTime();
        System.out.println("Part one: " + partOne(values));
        System.out.println("Part two: " + partTwo(values));
        long stopTime = System.nanoTime();
        System.out.println("Duration : " + TimeUnit.NANOSECONDS.toMillis(stopTime - startTime));

    }

    private static List<List<Long>> readFile() throws IOException {
        List<List<Long>> result = new ArrayList<>();
        Files.readAllLines(Paths.get("input.txt")).forEach(l -> {
            var temp = Arrays.stream(l.split("x")).map(Long::parseLong).toList();
            result.add(temp);
        });
        return result;
    }

    private static long partOne(List<List<Long>> values) {
        List<Long> temp = new ArrayList<>();
        values.forEach(l -> temp.add(
                        2 * ((long) l.get(0) * l.get(1) + (long) l.get(1) * l.get(2) + (long) l.get(0) * l.get(2))
                                + Math.min(Math.min((long) l.get(0) * l.get(1), (long) l.get(1) * l.get(2)), (long) l.get(0) * l.get(2))
                )
        );
        return temp.stream().reduce(0L, Long::sum);
    }

    private static long partTwo(List<List<Long>> values) {
        List<Long> temp = new ArrayList<>();
        values.forEach(l -> {
            var maxIndex = l.indexOf(Collections.max(l));
            var firstSum = l.stream().reduce(0L, Long::sum) * 2 - 2 * l.get(maxIndex);
            var secondSum = l.stream().reduce(1L, (a, b) -> a * b);
            temp.add(firstSum + secondSum);
        });

        return temp.stream().reduce(0L, Long::sum);
    }
}