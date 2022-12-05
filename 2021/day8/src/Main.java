import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {
    static class InputData {
        private List<String> input;
        private List<String> output;

        public InputData(List<String> input, List<String> output) {
            this.input = input;
            this.output = output;
        }

        public List<String> getInput() {
            return input;
        }

        public List<String> getOutput() {
            return output;
        }

        @Override
        public String toString() {
            return "InputData{" +
                    "input=" + input +
                    ", output=" + output +
                    '}';
        }
    }

    public static void main(String[] args) throws Exception {
        List<InputData> values = readFile();
        long startTime = System.nanoTime();
        System.out.println("Part one: " + partOne(values));
        //values = readFile();
        System.out.println("Part two: " + partTwo(values));
        long stopTime = System.nanoTime();
        System.out.println("Duration : " + (double) TimeUnit.NANOSECONDS.toMillis(stopTime - startTime));

    }

    private static List<InputData> readFile() throws IOException {
        List<List<String>> inputs = Files.readAllLines(Paths.get("input.txt"))
                .stream().map(p -> Arrays.asList(p.split(" \\| ")))
                .collect(Collectors.toList());
        List<InputData> result = new ArrayList<>();
        for (List<String> input : inputs) {
            List<String> dataInput =
                    Arrays.stream(input.get(0).split(" "))
                            .map(s -> {
                                char[] chars = s.toCharArray();
                                Arrays.sort(chars);
                                return new String(chars);
                            }).collect(Collectors.toList());
            List<String> dataOutput = Arrays.stream(input.get(1).split(" "))
                    .map(s -> {
                        char[] chars = s.toCharArray();
                        Arrays.sort(chars);
                        return new String(chars);
                    }).collect(Collectors.toList());
            result.add(new InputData(dataInput, dataOutput));
        }

        return result;
    }

    private static long partOne(List<InputData> values) {
        long result = 0;
        List<Integer> validLengths = Arrays.asList(2, 4, 3, 7);
        for (InputData value : values) {
            for (String output : value.getOutput()) {
                long count = value.getInput().stream().filter(p -> p.equals(output) && validLengths.contains(output.length())).count();
                if (count == 1) {
                    result++;
                }
            }
        }
        return result;
    }

    private static long partTwo(List<InputData> values) {
        long result = 0;
        for (InputData value : values) {
            List<String> decodedInput = decodeInput(value.getInput());
            String tmp = "";
            for (String output : value.getOutput()) {
                tmp += decodedInput.indexOf(output);
            }
            result += Integer.parseInt(tmp);
        }
        return result;
    }

    private static List<String> decodeInput(List<String> input) {
        String[] res = new String[10];
        for (String s : input) {
            if (s.length() == 2) {
                res[1] = s;
            }
            if (s.length() == 3) {
                res[7] = s;
            }
            if (s.length() == 4) {
                res[4] = s;
            }
            if (s.length() == 7) {
                res[8] = s;
            }
        }
        for (String s : input) {
            if (s.length() == 6) {
                if (res[1].contains(findMissing(res[8], s))) {
                    res[6] = s;
                } else if (res[4].contains(findMissing(res[8], s))) {
                    res[0] = s;
                } else {
                    res[9] = s;
                }
            }
        }

        for (String s : input) {
            if (s.length() == 5) {
                if (findMissing(res[6], s).length() == 1) {
                    res[5] = s;
                } else if (findMissing(res[1], s).length() == 0) {
                    res[3] = s;
                } else {
                    res[2] = s;
                }
            }
        }
        return Arrays.asList(res);
    }

    private static String findMissing(String big, String small) {
        String result = "";
        for (char c : big.toCharArray()) {
            if (small.indexOf(c) == -1) {
                result += c;
            }
        }
        return result;
    }
}