import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        Map<String, List<String>> values = readFile();
        var startTime = System.nanoTime();
        System.out.println("Part one: " + partOne(values));
        values = readFile();
        System.out.println("Part two: " + partTwo(values));
        var stopTime = System.nanoTime();
        System.out.println("Duration : " + TimeUnit.NANOSECONDS.toMillis(stopTime - startTime));
    }

    private static Map<String, List<String>> readFile() throws IOException {
        var lines = Files.readAllLines(Paths.get("input.txt"));
        var temp = lines.stream().map(line -> Arrays.stream(line.split("-")).collect(Collectors.toList())).collect(Collectors.toList());
        Map<String, List<String>> result = new HashMap<>();
        for (List<String> list : temp) {
            var start = list.get(0);
            var end = list.get(1);
            if (!result.containsKey(start)) {
                result.put(start, new ArrayList<>());
            }
            if (!result.containsKey(end)) {
                result.put(end, new ArrayList<>());
            }
            var currentStart = new ArrayList<>(result.get(start));
            var currentEnd = new ArrayList<>(result.get(end));
            currentStart.add(end);
            currentEnd.add(start);
            result.put(start, currentStart);
            result.put(end, currentEnd);
        }

        return result;
    }

    private static int partOne(Map<String, List<String>> values) {
        HashSet<List<String>> paths = new HashSet<>();
        List<String> start = values.get("start");
        for (String s : start) {
            List<String> path = new ArrayList<>();
            path.add("start");
            path.add(s);
            continuePath(values, paths, path, s, false);
        }
        return paths.size();
    }

    private static long partTwo(Map<String, List<String>> values) {
        HashSet<List<String>> paths = new HashSet<>();
        List<String> start = values.get("start");
        for (String s : start) {
            List<String> path = new ArrayList<>();
            path.add("start");
            path.add(s);
            continuePath(values, paths, path, s, true);
        }
        return paths.size();
    }

    private static void continuePath(Map<String, List<String>> values, HashSet<List<String>> paths, List<String> path, String current, boolean isPartTwo) {
        for (String s : values.get(current)) {
            List<String> currentPath = new ArrayList<>(path);
            if (path.contains("end")) {
                paths.add(currentPath);
            }

            if (isPartTwo && (s.equals("start") || s.toLowerCase().equals(s) && path.contains(s) && hasAlreadyVisitedSmallCaveTwice(path) || path.contains("end"))) {
                continue;
            }
            if (!isPartTwo && (s.toLowerCase().equals(s) && path.contains(s) || path.contains("end"))) {
                continue;
            }
            currentPath.add(s);
            continuePath(values, paths, currentPath, s, isPartTwo);
        }
    }

    private static boolean hasAlreadyVisitedSmallCaveTwice(List<String> path) {
        var filteredPaths = path.stream().filter(s -> s.toLowerCase().equals(s) && !s.equals("start") && !s.equals("end")).collect(Collectors.toList());
        return filteredPaths.size() != filteredPaths.stream().distinct().count();
    }
}