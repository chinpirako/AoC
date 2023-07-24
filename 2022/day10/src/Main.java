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
    static class Instruction {
        private String type;
        private int value;

        private static final Map<String, Integer> CYCLES = Map.of("addx", 2, "noop", 1);

        public Instruction(String type, int value) {
            this.type = type;
            this.value = value;
        }
    }

    public static void main(String[] args) throws Exception {
        Stack<Instruction> values = readFile();
        long startTime = System.nanoTime();
        System.out.println("Part one: " + partOne(values));
        values = readFile();
        System.out.println("Part two: " + partTwo(values));
        long stopTime = System.nanoTime();
        System.out.println("Duration : " + TimeUnit.NANOSECONDS.toMillis(stopTime - startTime));
    }

    private static Stack<Instruction> readFile() throws IOException {
        Stack<Instruction> result = Files.lines(Paths.get("input.txt"))
                .map(line -> {
                    if (line.startsWith("addx")) {
                        return new Instruction("addx", Integer.parseInt(line.substring(5)));
                    } else {
                        return new Instruction("noop", 0);
                    }
                })
                .collect(Collectors.toCollection(Stack::new));
        Stack<Instruction> reversed = new Stack<>();
        while (!result.isEmpty()) {
            Instruction instruction = result.pop();
            if (instruction.type.equals("addx")) {
                reversed.push(new Instruction("addx", instruction.value));
            }
            reversed.push(new Instruction("noop", 0));
        }
        return reversed;
    }

    private static int partOne(Stack<Instruction> values) {
        int x = 1;
        int currentCycle = 1;
        List<Integer> cycleValues = new ArrayList<>();
        while (!values.isEmpty()) {
            currentCycle++;
            if (Arrays.asList(20, 60, 100, 140, 180, 220).contains(currentCycle)) {
                cycleValues.add(x * currentCycle);
            }
            Instruction instruction = values.pop();
            if (instruction.type.equals("addx")) {
                x += instruction.value;
            }

            System.out.println("x: " + x + ", currentCycle: " + currentCycle);
        }

        return cycleValues.stream().reduce(0, Integer::sum);
    }

    private static int partTwo(Stack<Instruction> values) {
        int x = 1;
        int currentCycle = 1;
        String line = "";
        while (!values.isEmpty()) {
            int currentPixel = currentCycle % 40 - 1;
            if (Arrays.asList(x - 1, x, x + 1).contains(currentPixel)) {
                line += "#";
            } else {
                line += ".";
            }
            currentCycle++;
            Instruction instruction = values.pop();
            if (instruction.type.equals("addx")) {
                x += instruction.value;
            }

            if (currentCycle % 40 - 1 == 0) {
                System.out.println(line);
                currentCycle = 1;
                line = "";
            }
        }
        return 0;
    }
}