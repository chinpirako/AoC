import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Main {
    static class Octopus {
        private int energy;
        private boolean hasFlashed = false;

        public Octopus(int energy) {
            this.energy = energy;
        }

        public void flash() {
            hasFlashed = true;
        }

        public void reset() {
            hasFlashed = false;
        }

        public int getEnergy() {
            return energy;
        }

        public void setEnergy(int energy) {
            if (!hasFlashed) {
                this.energy = energy;
            }
        }

        public boolean hasFlashed() {
            return hasFlashed;
        }
    }

    public static void main(String[] args) throws Exception {
        Octopus[][] values = readFile();
        var startTime = System.nanoTime();
        System.out.println("Part one: " + partOne(values));
        values = readFile();
        System.out.println("Part two: " + partTwo(values));
        var stopTime = System.nanoTime();
        System.out.println("Duration : " + TimeUnit.NANOSECONDS.toMillis(stopTime - startTime));
    }

    private static Octopus[][] readFile() throws IOException {
        var result = new ArrayList<>(Files.readAllLines(Paths.get("input.txt"))).stream().map(l -> Arrays.stream(l.split("")).mapToInt(Integer::parseInt).mapToObj(Octopus::new).toArray(Octopus[]::new)).toArray(Octopus[][]::new);
        return result;
    }

    private static int partOne(Octopus[][] values) {
        var result = 0;
        for (int a = 0; a < 100; a++) {
            result += boostOctopuses(values);
            resetOctopuses(values);
        }
        return result;
    }

    private static long partTwo(Octopus[][] values) {
        var result = 0;
        var a = 0;
        while (result != values.length * values[0].length) {
            result = boostOctopuses(values);
            resetOctopuses(values);
            a++;
        }
        return a;
    }

    private static void resetOctopuses(Octopus[][] values) {
        for (Octopus[] value : values) {
            for (Octopus octopus : value) {
                octopus.reset();
            }
        }
    }

    private static int boostOctopuses(Octopus[][] values) {
        var result = 0;
        for (Octopus[] value : values) {
            for (Octopus octopus : value) {
                octopus.setEnergy(octopus.getEnergy() + 1);
            }
        }
        result = flashOctopuses(values, result);
        return result;
    }

    private static int flashOctopuses(Octopus[][] values, int result) {
        while (canFlash(values)) {
            for (int i = 0; i < values.length; i++) {
                for (int j = 0; j < values[i].length; j++) {
                    if (values[i][j].getEnergy() > 9 && !values[i][j].hasFlashed()) {
                        values[i][j].setEnergy(0);
                        values[i][j].flash();
                        result++;
                        if (i == 0) {
                            values[i + 1][j].setEnergy(values[i + 1][j].getEnergy() + 1);
                            if (j == 0) {
                                values[i][j + 1].setEnergy(values[i][j + 1].getEnergy() + 1);
                                values[i + 1][j + 1].setEnergy(values[i + 1][j + 1].getEnergy() + 1);
                            } else if (j == values[i].length - 1) {
                                values[i][j - 1].setEnergy(values[i][j - 1].getEnergy() + 1);
                                values[i + 1][j - 1].setEnergy(values[i + 1][j - 1].getEnergy() + 1);
                            } else {
                                values[i][j - 1].setEnergy(values[i][j - 1].getEnergy() + 1);
                                values[i + 1][j - 1].setEnergy(values[i + 1][j - 1].getEnergy() + 1);
                                values[i][j + 1].setEnergy(values[i][j + 1].getEnergy() + 1);
                                values[i + 1][j + 1].setEnergy(values[i + 1][j + 1].getEnergy() + 1);
                            }
                        } else if (i == values.length - 1) {
                            values[i - 1][j].setEnergy(values[i - 1][j].getEnergy() + 1);
                            if (j == 0) {
                                values[i][j + 1].setEnergy(values[i][j + 1].getEnergy() + 1);
                                values[i - 1][j + 1].setEnergy(values[i - 1][j + 1].getEnergy() + 1);
                            } else if (j == values[i].length - 1) {
                                values[i][j - 1].setEnergy(values[i][j - 1].getEnergy() + 1);
                                values[i - 1][j - 1].setEnergy(values[i - 1][j - 1].getEnergy() + 1);
                            } else {
                                values[i][j - 1].setEnergy(values[i][j - 1].getEnergy() + 1);
                                values[i - 1][j - 1].setEnergy(values[i - 1][j - 1].getEnergy() + 1);
                                values[i][j + 1].setEnergy(values[i][j + 1].getEnergy() + 1);
                                values[i - 1][j + 1].setEnergy(values[i - 1][j + 1].getEnergy() + 1);
                            }
                        } else {
                            values[i - 1][j].setEnergy(values[i - 1][j].getEnergy() + 1);
                            values[i + 1][j].setEnergy(values[i + 1][j].getEnergy() + 1);
                            if (j == 0) {
                                values[i][j + 1].setEnergy(values[i][j + 1].getEnergy() + 1);
                                values[i - 1][j + 1].setEnergy(values[i - 1][j + 1].getEnergy() + 1);
                                values[i + 1][j + 1].setEnergy(values[i + 1][j + 1].getEnergy() + 1);
                            } else if (j == values[i].length - 1) {
                                values[i][j - 1].setEnergy(values[i][j - 1].getEnergy() + 1);
                                values[i - 1][j - 1].setEnergy(values[i - 1][j - 1].getEnergy() + 1);
                                values[i + 1][j - 1].setEnergy(values[i + 1][j - 1].getEnergy() + 1);
                            } else {
                                values[i][j - 1].setEnergy(values[i][j - 1].getEnergy() + 1);
                                values[i - 1][j - 1].setEnergy(values[i - 1][j - 1].getEnergy() + 1);
                                values[i][j + 1].setEnergy(values[i][j + 1].getEnergy() + 1);
                                values[i + 1][j + 1].setEnergy(values[i + 1][j + 1].getEnergy() + 1);
                                values[i - 1][j + 1].setEnergy(values[i - 1][j + 1].getEnergy() + 1);
                                values[i + 1][j - 1].setEnergy(values[i + 1][j - 1].getEnergy() + 1);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    private static boolean canFlash(Octopus[][] values) {
        for (Octopus[] value : values) {
            for (Octopus octopus : value) {
                if (octopus.getEnergy() > 9) {
                    return true;
                }
            }
        }
        return false;
    }
}