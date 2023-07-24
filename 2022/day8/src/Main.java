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
    public static void main(String[] args) throws Exception {
        int[][] values = readFile();
        long startTime = System.nanoTime();
        System.out.println("Part one: " + partOne(values));
        System.out.println("Part two: " + partTwo(values));
        long stopTime = System.nanoTime();
        System.out.println("Duration : " + TimeUnit.NANOSECONDS.toMillis(stopTime - startTime));
    }

    private static int[][] readFile() throws IOException {
        return Files.lines(Paths.get("input.txt"))
                .map(line -> Arrays.stream(line.split(""))
                        .mapToInt(Integer::parseInt)
                        .toArray())
                .toArray(int[][]::new);
    }

    private static int partOne(int[][] values) {
        int width = values[0].length;
        int height = values.length;

        int result = 2 * (width + height) - 4;

        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                int currentTree = values[i][j];
                // If all values at the left are smaller than the current value, then increment result
                int finalI = i;
                int finalJ = j;

                if (IntStream.range(0, j).allMatch(k -> values[finalI][k] < currentTree)) {
                    result++;
                } else if (IntStream.range(j + 1, width).allMatch(k -> values[finalI][k] < currentTree)) {
                    result++;
                } else if (IntStream.range(0, i).allMatch(k -> values[k][finalJ] < currentTree)) {
                    result++;
                } else if (IntStream.range(i + 1, height).allMatch(k -> values[k][finalJ] < currentTree)) {
                    result++;
                }
            }
        }
        return result;
    }

    private static int partTwo(int[][] values) {
        int width = values[0].length;
        int height = values.length;
        int result = 0;

        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                int currentTree = values[i][j];
                int viewTopCount = 0;
                int viewBottomCount = 0;
                int viewLeftCount = 0;
                int viewRightCount = 0;

                for (int k = j - 1; k >= 0; k--) {
                    viewLeftCount++;
                    if (values[i][k] >= currentTree) {
                        break;
                    }
                }

                for (int k = j + 1; k < width; k++) {
                    viewRightCount++;
                    if (values[i][k] >= currentTree) {
                        break;
                    }
                }

                for (int k = i - 1; k >= 0; k--) {
                    viewTopCount++;
                    if (values[k][j] >= currentTree) {
                        break;
                    }
                }

                for (int k = i + 1; k < height; k++) {
                    viewBottomCount++;
                    if (values[k][j] >= currentTree) {
                        break;
                    }
                }

                int tempResult = viewTopCount * viewBottomCount * viewLeftCount * viewRightCount;
                if (tempResult > result) {
                    result = tempResult;
                }
            }
        }
        return result;
    }
}