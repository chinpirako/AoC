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
    static class File {
        String name;
        int size;

        public File(String name, int size) {
            this.name = name;
            this.size = size;
        }
    }

    static class Folder {
        String name;
        List<Folder> folders = new ArrayList<>();
        List<File> files = new ArrayList<>();
        // Parent folder
        Folder parent;

        public Folder(String name, Folder parent) {
            this.name = name;
            this.parent = parent;
        }

        public void addFolder(Folder folder) {
            folders.add(folder);
        }

        public void addFile(File file) {
            files.add(file);
        }

        public int getSize() {
            return files.stream().mapToInt(f -> f.size).sum() + folders.stream().mapToInt(Folder::getSize).sum();
        }
    }

    public static void main(String[] args) throws Exception {
        Folder values = readFile();
        long startTime = System.nanoTime();
        System.out.println("Part one: " + partOne(values));
        System.out.println("Part two: " + partTwo(values));
        long stopTime = System.nanoTime();
        System.out.println("Duration : " + TimeUnit.NANOSECONDS.toMillis(stopTime - startTime));
    }

    private static Folder readFile() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("input.txt"));
        Folder result = new Folder("/", null);
        Folder currentFolder = result;
        Folder parentFolder = null;

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.startsWith("$")) {
                if (line.contains("cd")) {
                    String folder = line.substring(line.indexOf("cd") + 3);
                    if (folder.equals("..")) {
                        currentFolder = parentFolder;
                        parentFolder = currentFolder.parent;
                    } else {
                        boolean notAdded = currentFolder.folders.stream().noneMatch(f -> f.name.equals(folder));
                        parentFolder = currentFolder;
                        if (notAdded) {
                            currentFolder = new Folder(folder, parentFolder);
                            currentFolder.folders.add(currentFolder);
                        } else {
                            currentFolder = currentFolder.folders.stream().filter(f -> f.name.equals(folder)).findFirst().get();
                        }
                    }
                }
                if (line.contains("ls")) {
                    while (i < lines.size() - 1 && !lines.get(i + 1).startsWith("$")) {
                        i++;
                        String[] split = lines.get(i).split(" ");
                        if (split[0].equals("dir")) {
                            Folder folder = new Folder(split[1], currentFolder);
                            folder.parent = currentFolder;
                            currentFolder.addFolder(folder);
                        } else {
                            File file = new File(split[1], Integer.parseInt(split[0]));
                            currentFolder.addFile(file);
                        }
                    }
                }
            }
        }

        return result;
    }

    private static int partOne(Folder values) {
        List<Folder> underSize = new ArrayList<>();
        findUnderSize(values, underSize);
        return underSize.stream().mapToInt(Folder::getSize).sum();
    }

    private static int partTwo(Folder values) {
        int totalDiskSize = 70000000;
        int requiredUpdateSize = 30000000;
        int currentSize = values.getSize();
        int currentUnusedSize = totalDiskSize - currentSize;
        int sizeToFree = requiredUpdateSize - currentUnusedSize;

        List<Folder> overSize = new ArrayList<>();
        findOverSize(values, overSize, sizeToFree);

        overSize.sort(Comparator.comparingInt(Folder::getSize));
        for (Folder folder : overSize) {
            System.out.println(folder.name + " " + folder.getSize());
        }
        return overSize.get(0).getSize();
    }

    private static void findUnderSize(Folder folder, List<Folder> underSize) {
        for (Folder f : folder.folders) {
            if (f.getSize() < 100000) {
                underSize.add(f);
            }
            findUnderSize(f, underSize);
        }
    }

    private static void findOverSize(Folder folder, List<Folder> overSize, int size) {
        for (Folder f : folder.folders) {
            if (f.getSize() >= size) {
                overSize.add(f);
            }
            findOverSize(f, overSize, size);
        }
    }
}