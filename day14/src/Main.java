import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {
    static class Polymerization {
        private final String polymer;
        private final Map<String, String> insertions;

        public Polymerization(String polymer, Map<String, String> insertions) {
            this.polymer = polymer;
            this.insertions = insertions;
        }

        public String getPolymer() {
            return polymer;
        }

        public Map<String, String> getInsertions() {
            return insertions;
        }
    }

    public static void main(String[] args) throws Exception {
        Polymerization values = readFile();
        var startTime = System.nanoTime();
        System.out.println("Part one: " + partOne(values));
        values = readFile();
        System.out.println("Part two: " + partTwo(values));
        var stopTime = System.nanoTime();
        System.out.println("Duration : " + TimeUnit.NANOSECONDS.toMillis(stopTime - startTime));
    }

    private static Polymerization readFile() throws IOException {
        var lines = Files.readAllLines(Paths.get("input.txt")).stream()
                .filter(c -> !c.equals(""))
                .collect(Collectors.toList());

        var polymer = lines.stream().filter(c -> !c.contains("->")).collect(Collectors.toList()).get(0);
        var insertions = lines.stream().filter(c -> c.contains("->")).map(s -> s.split(" -> ")).collect(Collectors.toMap(c -> c[0], c -> c[1]));

        return new Polymerization(polymer, insertions);
    }

    private static long partOne(Polymerization values) {
        return findMaxMinDifference(values, 10);
    }

    private static long partTwo(Polymerization values) {
        return findMaxMinDifference(values, 40);
    }

    private static long findMaxMinDifference(Polymerization values, int steps) {
        var currentPolymer = values.getPolymer();
        Map<String, Long> pairCount = new HashMap<>();
        for (int i = 0; i < currentPolymer.length() - 1; i++) {
            var currentPair = currentPolymer.substring(i, i + 2);
            if (values.getInsertions().containsKey(currentPair)) {
                var newPair1 = currentPair.charAt(0) + values.getInsertions().get(currentPair);
                var newPair2 = values.getInsertions().get(currentPair) + currentPair.charAt(1);
                if (pairCount.containsKey(newPair1)) {
                    pairCount.put(newPair1, pairCount.get(newPair1) + 1);
                } else {
                    pairCount.put(newPair1, 1L);
                }
                if (pairCount.containsKey(newPair2)) {
                    pairCount.put(newPair2, pairCount.get(newPair2) + 1);
                } else {
                    pairCount.put(newPair2, 1L);
                }
            }
        }

        int j = 1;
        while (j < steps) {
            Map<String, Long> newPairCounts = new HashMap<>(pairCount);
            for (var entry : pairCount.entrySet()) {
                String currentPair = entry.getKey();
                var newPair1 = currentPair.charAt(0) + values.getInsertions().get(currentPair);
                var newPair2 = values.getInsertions().get(currentPair) + currentPair.charAt(1);
                newPairCounts.put(currentPair, newPairCounts.get(currentPair) - entry.getValue());
                if (newPairCounts.containsKey(newPair1)) {
                    newPairCounts.put(newPair1, newPairCounts.get(newPair1) + entry.getValue());
                } else {
                    newPairCounts.put(newPair1, entry.getValue());
                }
                if (newPairCounts.containsKey(newPair2)) {
                    newPairCounts.put(newPair2, newPairCounts.get(newPair2) + entry.getValue());
                } else {
                    newPairCounts.put(newPair2, entry.getValue());
                }
            }
            pairCount = newPairCounts;
            j++;
        }

        Map<String, Long> results = new HashMap<>();
        for (var entry : pairCount.entrySet()) {
            var letters = entry.getKey().split("");
            if (results.containsKey(letters[0])) {
                results.put(letters[0], results.get(letters[0]) + entry.getValue());
            } else {
                results.put(letters[0], entry.getValue());
            }
            if (results.containsKey(letters[1])) {
                results.put(letters[1], results.get(letters[1]) + entry.getValue());
            } else {
                results.put(letters[1], entry.getValue());
            }
        }
        var max = results.entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue();
        var min = results.entrySet().stream().min(Map.Entry.comparingByValue()).get().getValue();
        return (max - min) / 2;
    }
}