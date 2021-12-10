import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {
    static class Coordinates {
        int x;
        int y;

        public Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public static void main(String[] args) throws Exception {
        List<List<Integer>> values = readFile();
        var startTime = System.nanoTime();
        System.out.println("Part one: " + partOne(values));
        System.out.println("Part two: " + partTwo(values));
        var stopTime = System.nanoTime();
        System.out.println("Duration : " + TimeUnit.NANOSECONDS.toMillis(stopTime - startTime));
    }

    private static List<List<Integer>> readFile() throws IOException {
        var content = new ArrayList<>(Files.readAllLines(Paths.get("input.txt")));
        List<List<Integer>> result = new ArrayList<>();

        for (String gridLine : content) {
            var line = Arrays.stream(gridLine.split(""))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            result.add(line);
        }
        return result;
    }

    private static int partOne(List<List<Integer>> values) {
        ArrayList<Integer> lowPoints = findLowPointsHeights(values);
        return lowPoints.stream().mapToInt(i -> i).sum();
    }

    private static ArrayList<Integer> findLowPointsHeights(List<List<Integer>> values) {
        var result = new ArrayList<Integer>();
        List<Coordinates> lowPoints = retrieveLowPoints(values);
        for (Coordinates lowPoint : lowPoints) {
            result.add(values.get(lowPoint.getX()).get(lowPoint.getY()) + 1);
        }
        return result;
    }

    private static int partTwo(List<List<Integer>> values) {
        ArrayList<Coordinates> lowPoints = retrieveLowPoints(values);
        List<List<Coordinates>> basins = retrieveBasins(values, lowPoints);
        int result = 1;
        for (int i = 0; i < 3; i++) {
            result *= basins.get(i).size();
        }
        return result;
    }

    private static List<List<Coordinates>> retrieveBasins(List<List<Integer>> values, ArrayList<Coordinates> lowPoints) {
        List<List<Coordinates>> basins = new ArrayList<>();
        for (Coordinates lowPoint : lowPoints) {
            List<Coordinates> basin = new ArrayList<>();
            findBasin(values, lowPoint, basin);
            basins.add(basin);
        }
        Collections.sort(basins, Comparator.comparingInt(List::size));
        Collections.reverse(basins);
        return basins;
    }

    private static ArrayList<Coordinates> retrieveLowPoints(List<List<Integer>> values) {
        var lowPoints = new ArrayList<Coordinates>();
        for (int i = 0; i < values.size(); i++) {
            var row = values.get(i);
            for (int j = row.size() - 1; j >= 0; j--) {
                var currentHeight = row.get(j);
                if (j != 0 && row.get(j - 1) < currentHeight) continue;
                if (j != row.size() - 1 && row.get(j + 1) < currentHeight) continue;
                if (i != 0 && values.get(i - 1).get(j) < currentHeight) continue;
                if (i != values.size() - 1 && values.get(i + 1).get(j) < currentHeight) continue;
                if (currentHeight != 9) {
                    lowPoints.add(new Coordinates(i, j));
                }
            }
        }
        return lowPoints;
    }

    private static void findBasin(List<List<Integer>> values, Coordinates coords, List<Coordinates> basin) {
        int x = coords.getX();
        int y = coords.getY();
        if (values.get(x).get(y) == 9 || basin.contains(coords)) return;
        basin.add(coords);
        if (x != values.size() - 1) findBasin(values, new Coordinates(x + 1, y), basin);
        if (x != 0) findBasin(values, new Coordinates(x - 1, y), basin);
        if (y != values.get(0).size() - 1) findBasin(values, new Coordinates(x, y + 1), basin);
        if (y != 0) findBasin(values, new Coordinates(x, y - 1), basin);
    }
}