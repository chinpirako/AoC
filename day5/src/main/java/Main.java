import com.google.common.collect.Sets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    static class Segment {
        Coordinates start;
        Coordinates end;
        HashSet<Coordinates> points = null;

        Segment(Coordinates start, Coordinates end) {
            this.start = start;
            this.end = end;
        }

        public HashSet<Coordinates> generate() {
            if (this.points != null) return this.points;
            var result = new HashSet<Coordinates>();
            if (this.end.getX() != this.start.getX()) {
                var slope = (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
                var b = this.start.getY() - slope * this.start.getX();
                var start = this.start.getX();
                var end = this.end.getX();
                if (start > end) {
                    start = this.end.getX();
                    end = this.start.getX();
                }
                result = IntStream.rangeClosed(start, end).mapToObj(i -> new Coordinates(i, slope * i + b)).collect(Collectors.toCollection(HashSet::new));
            } else {
                result = IntStream.rangeClosed(Math.min(this.start.getY(), this.end.getY()), Math.max(this.start.getY(), this.end.getY()))
                        .mapToObj(i -> new Coordinates(this.start.getX(), i))
                        .collect(Collectors.toCollection(HashSet::new));
            }
            this.points = result;
            return result;
        }

        public Sets.SetView<Coordinates> getIntersections(Segment s) {
            return Sets.intersection(this.generate(), s.generate());
        }

        public boolean isSloped() {
            return this.start.getX() != this.end.getX() && (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX()) != 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Segment segment = (Segment) o;
            return Objects.equals(start, segment.start) && Objects.equals(end, segment.end);
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end);
        }
    }

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinates that = (Coordinates) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public static void main(String[] args) throws Exception {
        List<Segment> values = readFile();
        var startTime = System.nanoTime();
        System.out.println("Part one: " + partOne(values));
        System.out.println("Part two: " + partTwo(values));
        var stopTime = System.nanoTime();
        System.out.println("Duration : " + TimeUnit.NANOSECONDS.toMillis(stopTime - startTime));
    }

    private static List<Segment> readFile() throws IOException {
        var result = new ArrayList<Segment>();
        var startTime = System.nanoTime();
        var content = Files.readAllLines(Paths.get("input.txt"))
                .stream()
                .filter(c -> !c.equals("")).toList();
        var tmp = content
                .stream()
                .map(l -> l.split(" -> ")).toList();

        for (String[] coords : tmp) {
            var fromString = Arrays.stream(coords[0].split(",")).mapToInt(Integer::parseInt).toArray();
            var toString = Arrays.stream(coords[1].split(",")).mapToInt(Integer::parseInt).toArray();
            var from = new Coordinates(fromString[0], fromString[1]);
            var to = new Coordinates(toString[0], toString[1]);
            var segment = new Segment(from, to);
            segment.generate();
            result.add(segment);
        }
        var stopTime = System.nanoTime();
        System.out.println("Duration (readFile): " + TimeUnit.NANOSECONDS.toMillis(stopTime - startTime));
        return result;
    }

    private static int partOne(List<Segment> segments) {
        var filteredSegments = segments.stream().filter(s -> !s.isSloped()).collect(Collectors.toList());
        return countIntersections(filteredSegments);
    }

    private static int partTwo(List<Segment> segments) {
        return countIntersections(segments);
    }

    private static int countIntersections(List<Segment> filteredSegments) {
        var intersections = Collections.synchronizedSet(new HashSet<Coordinates>());
        var startTime = System.nanoTime();
        filteredSegments.parallelStream().forEach(s -> filteredSegments.parallelStream().forEach(t -> {
            if (!s.equals(t)) {
                intersections.addAll(s.getIntersections(t));
            }
        }));
        var stopTime = System.nanoTime();
        System.out.println("Duration (countIntersections) : " + TimeUnit.NANOSECONDS.toMillis(stopTime - startTime));
        return intersections.size();
    }
}