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

    static class Pair {
        int i, j;

        Pair(int i, int j) {
            this.i = i;
            this.j = j;
        }

        Pair() {
            this.i = 0;
            this.j = 0;
        }

        private void move(String direction) {
            switch (direction) {
                case "U":
                    this.i++;
                    break;
                case "D":
                    this.i--;
                    break;
                case "L":
                    this.j--;
                    break;
                case "R":
                    this.j++;
                    break;
            }
        }

        private boolean isCloseTo(Pair other) {
            return (Math.abs(this.i - other.i) < 2 && Math.abs(this.j - other.j) < 2);
        }
    }


    private static int partOne(List<String> values) {
        Set<String> pairs = new HashSet<>();
        Pair tail = new Pair(0, 0);
        Pair head = new Pair(0, 0);
        pairs.add(tail.i + " " + tail.j);
        for (String instruction : values) {
            String dir = instruction.substring(0, 1);
            int count = Integer.parseInt(instruction.substring(2));
            for (int i = 0; i < count; i++) {
                head.move(dir);
                tail = follow(tail, head);
                pairs.add(tail.i + " " + tail.j);
            }
        }

        return pairs.size();
    }


    private static Pair follow(Pair follower, Pair leader) {
        Pair result = new Pair(follower.i, follower.j);
        if (!result.isCloseTo(leader)) {
            if (leader.i != result.i && leader.j != result.j) {
                int current_i = result.i;
                int current_j = result.j;
                if (leader.i > current_i)
                    result.move("U");
                else
                    result.move("D");

                if (leader.j > current_j) {
                    result.move("R");
                } else {
                    result.move("L");
                }
            }


            if (Math.abs(leader.i - result.i) > 1) {
                if (leader.i > result.i) {
                    result.move("U");
                } else {
                    result.move("D");
                }
            }

            if (Math.abs(leader.j - result.j) > 1) {
                if (leader.j > result.j) {
                    result.move("R");
                } else {
                    result.move("L");
                }
            }
        }
        return result;
    }

    private static Integer partTwo(List<String> values) {
        Set<String> pairs = new HashSet<>();
        Pair tail = new Pair(0, 0);
        Pair head = new Pair(0, 0);
        Pair[] rope = new Pair[10];
        for (int i = 0; i < 10; i++) {
            rope[i] = new Pair();
        }
        pairs.add(tail.i + " " + tail.j);
        for (String instruction : values) {
            String dir = instruction.substring(0, 1);
            int count = Integer.parseInt(instruction.substring(2));
            for (int i = 0; i < count; i++) {
                rope[0].move(dir);
                for (int j = 1; j < 10; j++)
                    rope[j] = follow(rope[j], rope[j - 1]);
                pairs.add(rope[9].i + " " + rope[9].j);
            }
        }

        return pairs.size();
    }
}