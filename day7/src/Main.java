import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        List<Integer> values = readFile();
        long startTime = System.nanoTime();
        System.out.println("Part one: " + partOne(values));
        //values = readFile();
        System.out.println("Part two: " + partTwo(values));
        long stopTime = System.nanoTime();
        System.out.println("Duration : " + (double) TimeUnit.NANOSECONDS.toMillis(stopTime - startTime));

    }

    private static List<Integer> readFile() throws IOException {
        return Arrays.stream(Files.readAllLines(Paths.get("input.txt"))
                .get(0)
                .split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private static long partOne(List<Integer> values) {
        int median = (int) Math.floor(values.stream().mapToInt(i -> i).average().getAsDouble());
        return values.stream().mapToInt(v -> Math.abs(v - median)).sum();
    }

    private static long partTwo(List<Integer> values) {
        int mean1 = (int) Math.floor(values.stream().mapToInt(i -> i).average().getAsDouble());
        int mean2 = (int) Math.ceil(values.stream().mapToInt(i -> i).average().getAsDouble());
        int tmp1 = 0, tmp2 = 0;
        for (Integer value : values) {
            long n1 = Math.abs(value - mean1);
            long n2 = Math.abs(value - mean2);
            tmp1 += n1 == 0 ? 0 : n1 * (n1 + 1) / 2;
            tmp2 += n2 == 0 ? 0 : n2 * (n2 + 1) / 2;
        }
        return Math.min(tmp1, tmp2);
    }
}