import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        List<String> values = readFile();
        long startTime = System.nanoTime();
        System.out.println("Part one: " + partOne(values));
        System.out.println("Part two: " + partTwo(values));
        long stopTime = System.nanoTime();
        System.out.println("Duration : " + TimeUnit.NANOSECONDS.toMillis(stopTime - startTime));
    }

    private static List<String> readFile() throws IOException {
        return Files.readAllLines(Paths.get("input.txt"));
    }

    private static long partOne(List<String> values) {
        int maxLength = values.get(0).length();
        StringBuilder gammaRateString = new StringBuilder();
        for (int i = 0; i < maxLength; i++) {
            int count = 0;
            for (String value : values) {
                count += Integer.parseInt(String.valueOf(value.charAt(i)));
            }
            gammaRateString.append(count >= values.size() / 2 ? 1 : 0);
        }
        long gammaRate = Integer.parseInt(gammaRateString.toString(), 2);
        long epsilon = gammaRate ^ Integer.parseInt("1".repeat(maxLength), 2);
        return gammaRate * epsilon;
    }

    private static long partTwo(List<String> values) {
        return computeOxygen(values) * computeCO2(values);
    }

    private static long computeCO2(List<String> values) {
        List<String> valuesCO2 = List.copyOf(values);
        int i = 0;
        while (valuesCO2.size() > 1) {
            long count = transpose(valuesCO2, i, i + 1)
                    .stream()
                    .mapToInt(Integer::parseInt)
                    .sum();
            long threshold = valuesCO2.size();
            int index = i;
            valuesCO2 = valuesCO2
                    .stream()
                    .filter((s) -> s.charAt(index) == (count >= (double) threshold / 2 ? '0' : '1')
            ).collect(Collectors.toList());
            i++;
        }
        return Integer.parseInt(valuesCO2.get(0), 2);
    }

    private static long computeOxygen(List<String> values) {
        List<String> valuesOxygen = List.copyOf(values);
        int i = 0;
        while (valuesOxygen.size() > 1) {
            long count = transpose(valuesOxygen, i, i + 1)
                    .stream()
                    .mapToInt(Integer::parseInt)
                    .sum();
            long threshold = valuesOxygen.size();
            int index = i;
            valuesOxygen = valuesOxygen.stream().filter(
                    (s) -> s.charAt(index) == (count >= (double) threshold / 2 ? '1' : '0')
            ).collect(Collectors.toList());
            i++;
        }
        return Integer.parseInt(valuesOxygen.get(0), 2);
    }

    private static List<String> transpose(List<String> input, int indexFrom, int indexTo) {
        StringBuilder newValue = new StringBuilder();
        for (int i = indexFrom; i < indexTo; i++) {
            for (String s : input) {
                newValue.append(s.charAt(i));
            }
        }
        return List.of(newValue.toString().split(""));
    }
}