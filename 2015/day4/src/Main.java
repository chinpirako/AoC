import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

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
        return computeSequenceLength(values, 40);
    }

    private static long partTwo(String values) {
        return computeSequenceLength(values, 50);
    }

    private static int computeSequenceLength(String values, int depth) {
        for (int i = 0; i < depth; i++) {
            var temp = new StringBuilder();
            var stack = new Stack<Character>();
            for (int j = 0; j < values.length(); j++) {
                if (stack.isEmpty()) {
                    stack.push(values.charAt(j));
                } else {
                    if (stack.peek() == values.charAt(j)) {
                        stack.push(values.charAt(j));
                    } else {
                        temp.append(stack.size());
                        temp.append(stack.peek());
                        stack.clear();
                        stack.push(values.charAt(j));
                    }
                }
            }
            temp.append(stack.size());
            temp.append(stack.peek());
            values = temp.toString();
        }
        return values.length();
    }
}