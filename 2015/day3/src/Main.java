import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {
    static class Position {
        int x;
        int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
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
        return Arrays.stream(Files.readAllLines(Paths.get("input.txt")).get(0).split("")).toList();
    }

    private static long partOne(List<String> values) {
        List<Position> visited = new ArrayList<>();
        Position currentPosition = new Position(0, 0);
        visited.add(currentPosition);
        for (String d : values) {
            currentPosition = getNextPosition((List<Position>) visited, currentPosition, d);
        }
        return visited.stream().distinct().count();
    }

    private static long partTwo(List<String> values) {
        List<Position> santa = new ArrayList<>();
        List<Position> robot = new ArrayList<>();
        Position currentSantaPos = new Position(0, 0);
        Position currentRobotPos = new Position(0, 0);
        santa.add(currentSantaPos);
        robot.add(currentRobotPos);
        for (int i = 0; i < values.size(); i++) {
            String direction = values.get(i);
            if (i % 2 == 0) {
                currentSantaPos = getNextPosition((List<Position>) santa, currentSantaPos, direction);
            } else {
                currentRobotPos = getNextPosition(robot, currentRobotPos, direction);
            }
        }

        return santa.stream().distinct().count() + robot.stream().distinct().count() - santa.stream().filter(robot::contains).count() + 1;
    }

    private static Position getNextPosition(List<Position> robot, Position currentRobotPos, String direction) {
        Position newPosition = switch (direction) {
            case "^" -> new Position(currentRobotPos.getX(), currentRobotPos.getY() + 1);
            case "v" -> new Position(currentRobotPos.getX(), currentRobotPos.getY() - 1);
            case "<" -> new Position(currentRobotPos.getX() - 1, currentRobotPos.getY());
            default -> new Position(currentRobotPos.getX() + 1, currentRobotPos.getY());
        };
        currentRobotPos = newPosition;
        robot.add(currentRobotPos);
        return currentRobotPos;
    }
}