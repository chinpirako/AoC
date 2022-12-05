import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws Exception {
        String values = readFile();
        long startTime = System.nanoTime();
        System.out.println("Part one: " + partOne(values));
        System.out.println("Part two: " + partTwo(values));
        long stopTime = System.nanoTime();
        System.out.println("Duration : " + TimeUnit.NANOSECONDS.toMillis(stopTime - startTime));
    }

    private static String readFile() throws IOException {
        return Files.readString(Paths.get("example.txt"));
    }

    private static long partOne(String values) {
        Pattern pattern = Pattern.compile("-?\\d+");
        Matcher matcher = pattern.matcher(values);
        long result = 0;
        while (matcher.find()) {
            result += Long.parseLong(matcher.group());
        }
        return result;
    }

    private static long partTwo(String values) {
    /*
    Regex to match value red and remove the object it is in
    Example :
    "d":{
                "e":"red",
                "c":93,
                "a":135,
                "g":{
                  "e":43,
                  "c":"green",
                  "a":"orange",
                  "b":"green",
                  "d":54
                },
                "b":69,
                "d":159,
                "f":2
              },
              becomes
              "d": {}
     */
        Pattern pattern = Pattern.compile("\"red\"\\s*:\\s*\\{[^}]*\\}");
        Matcher matcher = pattern.matcher(values);
        while (matcher.find()) {
            values = values.replace(matcher.group(), "\"red\": {}");
        }
        return partOne(values);
    }
}