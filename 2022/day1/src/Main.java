import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();
        List<List<Long>> values = readFile();
        System.out.println("Part one: " + partOne(values));
        System.out.println("Part two: " + partTwo(values));
        long stopTime = System.nanoTime();
        System.out.println("Duration : " + TimeUnit.NANOSECONDS.toMillis(stopTime - startTime));
    }

    private static List<List<Long>> readFile() throws IOException {
        List<List<Long>> result = new ArrayList<>();
        List<Long> temp = new ArrayList<>();
        List<String> input = Files.readAllLines(Pathas.get("input.txt"));
        for (String l : input) {
            if (!l.equals("")) {
                temp.add(Long.parseLong(l));
            } else {
                result.add(temp);
                temp = new ArrayList<>();
            }
        }
        result.add(temp);
        return result;
    }

    private static long partOne(List<List<Long>> values) {
        return getSums(values).stream().max(Long::compareTo).get();
    }

    private static long partTwo(List<List<Long>> values) {
        return getSums(values).stream().sorted(Comparator.reverseOrder()).limit(3).reduce(0L, Long::sum);
    }

    private static List<Long> getSums(List<List<Long>> values) {
        return values.stream().reduce(new ArrayList<>(), (a, b) -> {
            a.add(b.stream().reduce(0L, Long::sum));
            return a;
        });
    }
}