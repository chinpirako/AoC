import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws Exception {
        String values = readFile();
        long startTime = System.nanoTime();
        System.out.println("Part one: " + partOne(values));
        System.out.println("Part two: " + partTwo(values));
        long stopTime = System.nanoTime();
        System.out.println("Duration : " + TimeUnit.NANOSECONDS.toMillis(stopTime - startTime));
    }

    private static String readFile() throws IOException {
        return Files.readAllLines(Paths.get("input.txt")).get(0);
    }

    private static int partOne(String values) {
        return findMarkerIndex(values, 4);
    }

    private static int partTwo(String values) {
        return findMarkerIndex(values, 14);
    }

    private static int findMarkerIndex(String values, int x) {
        int i = 0;
        while (true) {
            if (values.substring(i, i + x).chars().distinct().count() == x) {
                i += x;
                break;
            }
            i++;
        }
        return i;
    }
}