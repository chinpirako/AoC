import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
        long result = 0;
        List<List<String>> temp = new ArrayList<>();
        for (String l : values) {
            List<String> t = new ArrayList<>();
            t.add(l.substring(0, l.length() / 2));
            t.add(l.substring(l.length() / 2));
            temp.add(t);
        }

        for (List<String> l : temp) {
            Set<Character> s1 = toSet(l.get(0));
            Set<Character> s2 = toSet(l.get(1));
            s1.retainAll(s2);

            result = computePriority(result, s1);
        }
        return result;
    }

    private static long partTwo(List<String> values) {
        List<String> temp = new ArrayList<>();
        long result = 0;
        for (int i = 0; i < values.size(); i++) {
            temp.add(values.get(i));
            if ((i + 1) % 3 == 0) {
                Set<Character> s1 = toSet(temp.get(0));
                Set<Character> s2 = toSet(temp.get(1));
                Set<Character> s3 = toSet(temp.get(2));
                s1.retainAll(s2);
                s1.retainAll(s3);
                result = computePriority(result, s1);
                temp = new ArrayList<>();
            }
        }
        return result;
    }

    private static long computePriority(long result, Set<Character> s1) {
        String c = s1.stream().findFirst().get().toString();
        if (LOWER_CASE.contains(c)) {
            result += LOWER_CASE.indexOf(c) + 1;
        } else {
            result += UPPER_CASE.indexOf(c) + 27;
        }
        return result;
    }

    public static Set<Character> toSet(String s) {
        Set<Character> ss = new HashSet<>(s.length());
        for (char c : s.toCharArray())
            ss.add(c);
        return ss;
    }

    private static final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
}