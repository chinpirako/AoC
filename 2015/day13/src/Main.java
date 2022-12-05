import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws Exception {
        Map<String, Map<String, Long>> values = readFile();
        long startTime = System.nanoTime();
        System.out.println("Part one: " + partOne(values));
        System.out.println("Part two: " + partTwo(values));
        long stopTime = System.nanoTime();
        System.out.println("Duration : " + TimeUnit.NANOSECONDS.toMillis(stopTime - startTime));
    }

    private static Map<String, Map<String, Long>> readFile() throws IOException {
        Map<String, Map<String, Long>> result = new HashMap<>();
        List<String> input = Files.readAllLines(Paths.get("input.txt"));
        for (String l : input) {
        }
        return result;
    }

    private static long partOne(Map<String, Map<String, Long>> values) {
        return 0;
    }

    private static long partTwo(Map<String, Map<String, Long>> values) {
        return 0;
    }
}