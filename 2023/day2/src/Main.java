import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {

    record Cubes(String type, Long amount) {
    }

    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();
        List<String> values = readFile();
        System.out.println("Part one: " + partOne(values));
        System.out.println("Part two: " + partTwo(values));
        long stopTime = System.nanoTime();
        System.out.println("Duration : " + TimeUnit.NANOSECONDS.toMillis(stopTime - startTime));
    }

    private static List<String> readFile() throws IOException {
        return Files.readAllLines(Paths.get("input.txt"));
    }

    private static long partOne(List<String> values) {
        final Map<Integer, Set<Set<Cubes>>> games = parseGames(values);
        final Map<String, Long> availableCubes = Map.ofEntries(
                Map.entry("red", 12L),
                Map.entry("green", 13L),
                Map.entry("blue", 14L)
        );
        final Set<Integer> validGames = new HashSet<>();
        for (Map.Entry<Integer, Set<Set<Cubes>>> game: games.entrySet()) {
            var isValid = true;
            final Set<Set<Cubes>> cubes = game.getValue();
            for (Set<Cubes> cubeSet: cubes) {
                for (Cubes cube: cubeSet) {
                    if (availableCubes.get(cube.type()) < cube.amount()) {
                        isValid = false;
                        break;
                    }
                }
            }
            if (isValid) {
                validGames.add(game.getKey());
            }
        }
        return validGames.stream().mapToLong(Integer::longValue).sum();
    }

    private static long partTwo(List<String> values) {
        final Map<Integer, Set<Set<Cubes>>> games = parseGames(values);
        final List<Long> result = new ArrayList<>();
        for (Map.Entry<Integer, Set<Set<Cubes>>> game: games.entrySet()) {
            var minRed = 0L;
            var minGreen = 0L;
            var minBlue = 0L;
            final Set<Set<Cubes>> cubes = game.getValue();
            for (Set<Cubes> cubeSet: cubes) {
                for (Cubes cube: cubeSet) {
                    switch (cube.type()) {
                        case "red" -> minRed = Math.max(minRed, cube.amount());
                        case "green" -> minGreen = Math.max(minGreen, cube.amount());
                        case "blue" -> minBlue = Math.max(minBlue, cube.amount());
                    }
                }
            }
            result.add(minRed * minGreen * minBlue);
        }
        return result.stream().mapToLong(Long::longValue).sum();
    }

    private static Map<Integer, Set<Set<Cubes>>> parseGames(List<String> values) {
        final Map<Integer, Set<Set<Cubes>>> games = new HashMap<>();
        for (String line: values) {
            final String[] split = line.split(":");
            final String[] allCubes = split[1].split(";");
            final Integer gameNumber = Integer.valueOf(split[0].split(" ")[1]);
            final Set<Set<Cubes>> game = new HashSet<>();
            for (String revealedCubes: allCubes) {
                final Set<Cubes> cubesSet = new HashSet<>();
                for (String cubes: revealedCubes.split(", ")) {
                    final String[] cubeSplit = cubes.trim().split(" ");
                    final String type = cubeSplit[1];
                    final Long amount = Long.valueOf(cubeSplit[0]);
                    cubesSet.add(new Cubes(type, amount));
                    game.add(cubesSet);
                }
            }
            games.put(gameNumber, game);
        }
        return games;
    }
}