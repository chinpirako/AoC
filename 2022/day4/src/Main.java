import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws Exception {
        List<List<String>> values = readFile();
        long startTime = System.nanoTime();
        System.out.println("Part one: " + partOne(values));
        System.out.println("Part two: " + partTwo(values));
        long stopTime = System.nanoTime();
        System.out.println("Duration : " + TimeUnit.NANOSECONDS.toMillis(stopTime - startTime));
    }

    private static List<List<String>> readFile() throws IOException {
        return Files.readAllLines(Paths.get("input.txt")).stream().map(l -> Arrays.asList(l.split(","))).collect(Collectors.toList());
    }

    private static long partOne(List<List<String>> values) {
        long result = 0;
        for (List<String> l : values) {
            List<String> s1 = List.of(l.get(0).split("-"));
            List<String> s2 = List.of(l.get(1).split("-"));

            List<Integer> s1Range = IntStream.rangeClosed(Integer.parseInt(s1.get(0)), Integer.parseInt(s1.get(1))).boxed().toList();
            List<Integer> s2Range = IntStream.rangeClosed(Integer.parseInt(s2.get(0)), Integer.parseInt(s2.get(1))).boxed().toList();

            if (new HashSet<>(s2Range).containsAll(s1Range) || new HashSet<>(s1Range).containsAll(s2Range)) {
                result += 1;
            }
        }
        return result;
    }

    private static long partTwo(List<List<String>> values) {
        long result = 0;
        for (List<String> l : values) {
            List<String> s1 = List.of(l.get(0).split("-"));
            List<String> s2 = List.of(l.get(1).split("-"));

            List<Integer> s1Range = IntStream.rangeClosed(Integer.parseInt(s1.get(0)), Integer.parseInt(s1.get(1))).boxed().toList();
            List<Integer> s2Range = IntStream.rangeClosed(Integer.parseInt(s2.get(0)), Integer.parseInt(s2.get(1))).boxed().toList();

            if (s1Range.stream().anyMatch(s2Range::contains)) {
                result += 1;
            }
        }
        return result;
    }
}