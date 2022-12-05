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
    static class CrateMovement {
        List<List<String>> crates;
        List<List<Integer>> moves;

        public CrateMovement(List<List<String>> crates, List<List<Integer>> moves) {
            this.crates = crates;
            this.moves = moves;
        }
    }

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

    private static String partOne(List<String> values) {
        CrateMovement crateMovement = parseShit(values);
        for (List<Integer> move : crateMovement.moves) {
            int amount = move.get(0);
            int from = move.get(1);
            int to = move.get(2);
            for (int i = 0; i < amount; i++) {
                crateMovement.crates.get(to).add(0, crateMovement.crates.get(from).get(0));
                crateMovement.crates.get(from).remove(0);
            }
        }

        StringBuilder result = new StringBuilder("");
        for (List<String> crate : crateMovement.crates) {
            result.append(crate.get(0));
        }
        return result.toString();
    }

    private static String partTwo(List<String> values) {
        CrateMovement crateMovement = parseShit(values);
        for (List<Integer> move : crateMovement.moves) {
            int amount = move.get(0);
            int from = move.get(1);
            int to = move.get(2);
            List<String> tmp = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                tmp.add(0, crateMovement.crates.get(from).get(0));
                crateMovement.crates.get(from).remove(0);
            }
            Collections.reverse(tmp);
            crateMovement.crates.get(to).addAll(0, tmp);
        }

        StringBuilder result = new StringBuilder("");
        for (List<String> crate : crateMovement.crates) {
            result.append(crate.get(0));
        }
        return result.toString();
    }

    private static CrateMovement parseShit(List<String> values) {
        var indexOfIndexes = values.indexOf("") - 1;
        var numberToLookFor = 1;
        var indexOfNumber = values.get(indexOfIndexes).indexOf("" + numberToLookFor);

        List<List<String>> stackList = new ArrayList<>();

        do {
            List<String> stack = new ArrayList<>();
            for (int i = 0; i < indexOfIndexes; i++) {
                var line = values.get(i).split("");
                if (indexOfNumber < line.length) {
                    var crate = line[indexOfNumber];
                    if (!" ".equals(crate)) {
                        stack.add(crate);
                    }
                }
            }
            stackList.add(stack);
            numberToLookFor++;
            indexOfNumber = values.get(indexOfIndexes).indexOf("" + numberToLookFor);
        } while (indexOfNumber != -1);

        List<String> moves = values.subList(indexOfIndexes + 2, values.size());
        List<List<Integer>> parsedMoves = new ArrayList<>();

        for (String move : moves) {
            List<Integer> parsedMove = new ArrayList<>();
            String regex = "move (\\d+) from (\\d+) to (\\d+)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(move);
            if (matcher.find()) {
                int amount = Integer.parseInt(matcher.group(1));
                int from = Integer.parseInt(matcher.group(2)) - 1;
                int to = Integer.parseInt(matcher.group(3)) - 1;
                parsedMove.add(amount);
                parsedMove.add(from);
                parsedMove.add(to);
            }
            parsedMoves.add(parsedMove);
        }
        return new CrateMovement(stackList, parsedMoves);
    }
}