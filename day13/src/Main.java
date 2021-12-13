import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {
    static class FoldingPaper {
        List<List<Integer>> paper;
        List<FoldInstruction> foldInstructions;

        public FoldingPaper(List<List<Integer>> paper, List<FoldInstruction> foldInstructions) {
            this.paper = paper;
            this.foldInstructions = foldInstructions;
        }

        public List<List<Integer>> getPaper() {
            return paper;
        }

        public List<FoldInstruction> getFoldInstructions() {
            return foldInstructions;
        }
    }

    static class FoldInstruction {
        String axis;
        int position;

        FoldInstruction(String axis, int position) {
            this.axis = axis;
            this.position = position;
        }

        public String getAxis() {
            return axis;
        }

        public int getPosition() {
            return position;
        }
    }

    public static void main(String[] args) throws Exception {
        FoldingPaper values = readFile();
        var startTime = System.nanoTime();
        System.out.println("Part one: " + partOne(values));
        values = readFile();
        System.out.println("Part two: " + partTwo(values));
        var stopTime = System.nanoTime();
        System.out.println("Duration : " + TimeUnit.NANOSECONDS.toMillis(stopTime - startTime));
    }

    private static FoldingPaper readFile() throws IOException {
        var lines = Files.readAllLines(Paths.get("input.txt")).stream()
                .filter(c -> !c.equals(""))
                .collect(Collectors.toList());

        var paperLines = lines.stream().filter(c -> !c.contains("fold"))
                .map(s -> Arrays.stream(s.split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        var foldInstructions = lines.stream().filter(c -> c.contains("fold")).map(s -> {
            String tmp = s.substring("fold along ".length());
            var sInstructions = tmp.split("=");
            return new FoldInstruction(sInstructions[0], Integer.parseInt(sInstructions[1]));
        }).collect(Collectors.toList());

        return new FoldingPaper(paperLines, foldInstructions);
    }

    private static long partOne(FoldingPaper values) {
        List<List<Integer>> paper = new ArrayList<>(values.getPaper());
        var foldInstruction = values.getFoldInstructions().get(0);
        var newPaper = new ArrayList<List<Integer>>();
        fold(paper, foldInstruction, newPaper);
        paper = newPaper.stream().distinct().collect(Collectors.toList());
        return paper.size();
    }

    private static void fold(List<List<Integer>> paper, FoldInstruction foldInstruction, ArrayList<List<Integer>> newPaper) {
        for (List<Integer> dot : paper) {
            var x = dot.get(0);
            var y = dot.get(1);
            if (foldInstruction.getAxis().equals("x")) {
                if (x > foldInstruction.getPosition()) {
                    x = foldInstruction.getPosition() - (x - foldInstruction.getPosition());
                }
                newPaper.add(Arrays.asList(x, y));
            } else {
                if (y > foldInstruction.getPosition()) {
                    y = foldInstruction.getPosition() - (y - foldInstruction.getPosition());
                }
            }
            newPaper.add(Arrays.asList(x, y));
        }
    }

    private static long partTwo(FoldingPaper values) {
        List<List<Integer>> paper = new ArrayList<>(values.getPaper());
        for (FoldInstruction foldInstruction : values.getFoldInstructions()) {
            var newPaper = new ArrayList<List<Integer>>();
            fold(paper, foldInstruction, newPaper);
            paper = newPaper;
        }
        var maxX = paper.stream().map(l -> l.get(0)).max(Integer::compareTo).get();
        var maxY = paper.stream().map(l -> l.get(1)).max(Integer::compareTo).get();
        int[][] tmpPaper = new int[maxY + 1][maxX + 1];

        for (int i = 0; i < paper.size(); i++) {
            var line = paper.get(i);
            tmpPaper[line.get(1)][line.get(0)] = 1;
        }
        var result = Arrays.stream(tmpPaper).map(l -> Arrays.stream(l).mapToObj(i -> i == 1 ? "#" : " ")
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        for (List<String> line : result) {
            System.out.println(String.join("", line));
        }
        return 0;
    }
}
