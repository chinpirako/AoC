import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {
    final static List<Character> OPENING_CHARS = Arrays.asList('(', '[', '{', '<');
    final static Map<Character, Integer> CLOSING_CHARS_VALUE = new HashMap<>() {{
        put(')', 3);
        put(']', 57);
        put('}', 1197);
        put('>', 25137);
    }};
    final static Map<Character, Integer> OPENING_CHARS_VALUE = new HashMap<>() {{
        put('(', 1);
        put('[', 2);
        put('{', 3);
        put('<', 4);
    }};

    public static void main(String[] args) throws Exception {
        List<String> values = readFile();
        var startTime = System.nanoTime();
        System.out.println("Part one: " + partOne(values));
        System.out.println("Part two: " + partTwo(values));
        var stopTime = System.nanoTime();
        System.out.println("Duration : " + TimeUnit.NANOSECONDS.toMillis(stopTime - startTime));
    }

    private static List<String> readFile() throws IOException {
        var result = new ArrayList<>(Files.readAllLines(Paths.get("input.txt")));
        return result;
    }

    private static int partOne(List<String> values) {
        var result = 0;
        for (String line : values) {
            char invalidChar = getInvalidChar(line);
            if (invalidChar != Character.MIN_VALUE) {
                result += CLOSING_CHARS_VALUE.get(invalidChar);
            }
        }
        return result;
    }

    private static long partTwo(List<String> values) {
        var filteredList = new ArrayList<String>();
        for (String line : values) {
            char invalidChar = getInvalidChar(line);
            if (invalidChar == Character.MIN_VALUE) {
                filteredList.add(line);
            }
        }

        var results = new ArrayList<Long>();
        for (String line : filteredList) {
            long tmpResult = 0;
            var chars = line.toCharArray();
            Stack<Character> stack = new Stack<>();
            for (char c : chars) {
                if (OPENING_CHARS.contains(c)) {
                    stack.push(c);
                } else {
                    stack.pop();
                }
            }
            while (!stack.isEmpty()) {
                tmpResult = tmpResult * 5 + OPENING_CHARS_VALUE.get(stack.pop());
            }
            results.add(tmpResult);
        }
        Collections.sort(results);
        return results.get(results.size() / 2);
    }

    private static char getInvalidChar(String line) {
        char invalidChar = Character.MIN_VALUE;
        var chars = line.toCharArray();
        Stack<Character> stack = new Stack<>();
        for (char c : chars) {
            if (OPENING_CHARS.contains(c)) {
                stack.push(c);
            } else if (stack.isEmpty() || !isMatching(stack.pop(), c)) {
                invalidChar = c;
                break;
            }
        }
        return invalidChar;
    }

    private static boolean isMatching(char c1, char c2) {
        return c1 == '(' && c2 == ')' ||
                c1 == '[' && c2 == ']' ||
                c1 == '{' && c2 == '}' ||
                c1 == '<' && c2 == '>';
    }
}