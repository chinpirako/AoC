import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {
    private static class DirectionPair {
        private String direction;
        private int value;

        DirectionPair(String direction, int value) {
            this.direction = direction;
            this.value = value;
        }

        public String getDirection() {
            return direction;
        }

        public int getValue() {
            return value;
        }
    }

    public static void main(String[] args) throws Exception {
        List<DirectionPair> values = readFile();
        long startTime = System.nanoTime();
        System.out.println("Part one: " + partOne(values));
        System.out.println("Part two: " + partTwo(values));
        long stopTime = System.nanoTime();
        System.out.println("Duration : " + TimeUnit.NANOSECONDS.toMillis(stopTime - startTime));

    }

    private static List<DirectionPair> readFile() throws IOException {
        return Files.readAllLines(Paths.get("input.txt"))
                .stream()
                .map((i) -> {
                    String[] temp = i.split(" ");
                    return new DirectionPair(temp[0], Integer.parseInt(temp[1]));
                }).collect(Collectors.toList());
    }

    private static long partOne(List<DirectionPair> values) {
        long x = 0;
        long depth = 0;
        for (DirectionPair directionPair : values) {
            String direction = directionPair.getDirection();
            int value = directionPair.getValue();
            if (direction.equals("forward")) x += value;
            if (direction.equals("up")) depth -= value;
            if (direction.equals("down")) depth += value;
        }
        return x * depth;
    }

    private static long partTwo(List<DirectionPair> values) {
        long x = 0;
        long depth = 0;
        long aim = 0;
        for (DirectionPair directionPair : values) {
            String direction = directionPair.getDirection();
            int value = directionPair.getValue();
            if (direction.equals("forward")) {
                depth += value * aim;
                x += value;
            }
            if (direction.equals("up")) aim -= value;
            if (direction.equals("down")) aim += value;
        }
        return x * depth;
    }
}