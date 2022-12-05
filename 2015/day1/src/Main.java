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

    private static long partOne(String values) {
        return values.chars().filter(c -> (char) c == '(').count() - values.chars().filter(c -> (char) c == ')').count();
    }

    private static long partTwo(String values) {
        int position = 1;
        int floor = 0;
        int[] chars = values.chars().toArray();
        for (int c : chars) {
            floor += (char) c == '(' ? 1 : -1;
            if (floor < 0) break;
            position++;
        }
        return position;
    }
}