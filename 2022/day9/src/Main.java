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
            this.i = i; this.j=j;
        }
        Pair() {
            this.i = 0; this.j=0;
        }
        private void move(String direction){
            switch (direction) {
                case "U" : this.i++; break;
                case "D" : this.i--; break;
                case "L" : this.j--; break;
                case "R" : this.j++; break;
            }
        }

        private boolean isCloseTo(Pair other){
            return (Math.abs(this.i-other.i)<2 && Math.abs(this.j-other.j)<2);
        }

        public boolean equals(Pair other) {
            return this.i==other.i && this.j == other.j;
        }
    }


    private static int partOne(List<String> values) {
        Set<String> pairs = new HashSet<>();
        Pair T = new Pair(0, 0);
        Pair H = new Pair(0, 0);
        pairs.add(T.i+" "+T.j);
        for (String instruction : values) {
            String dir = instruction.substring(0,1);
            int count = Integer.parseInt(instruction.substring(2));
            for( int i= 0; i < count; i++){
                H.move(dir);
                T = Follow(T, H);
                pairs.add(T.i + " " + T.j);
            }
        }

        return pairs.size();
    }


    private static Pair Follow(Pair Follower, Pair leader) {
        Pair ret = new Pair(Follower.i,Follower.j);
        if (!ret.isCloseTo(leader)) {
            if ( leader.i != ret.i  && leader.j != ret.j) {
                int i_ = ret.i;
                int j_ = ret.j;
                if (leader.i > i_)
                    ret.move("U");
                else
                    ret.move("D");

                if (leader.j > j_) {
                    ret.move("R");
                } else {
                    ret.move("L");
                }
            }



            if (Math.abs(leader.i - ret.i) > 1) {
                if (leader.i > ret.i) {
                    ret.move("U");
                } else {
                    ret.move("D");
                }
            }

            if (Math.abs(leader.j - ret.j) > 1) {
                if (leader.j > ret.j) {
                    ret.move("R");
                } else {
                    ret.move("L");
                }
            }
        }
        return ret;
    }

    static void print(Pair H, Pair T){
        for(int i = 5; i >= 0; i--){
            String p="";
            for (int j =0; j < 6; j ++){
                String l = ".";
                if ( T.i == i && T.j == j) l = "T";
                if ( H.i == i && H.j == j) l = "H";
                p += l+" ";
            }
            System.out.println(p);
        }
        System.out.println();
    }

    private static Integer partTwo(List<String> values) {
        Set<String> pairs = new HashSet<>();
        Pair T = new Pair(0, 0);
        Pair H = new Pair(0, 0);
        Pair[] rope = new Pair[10];
        for (int i = 0; i < 10; i++){
            rope[i] = new Pair();
        }
        //print(H,T);
        pairs.add(T.i+" "+T.j);
        for (String instruction : values) {
            String dir = instruction.substring(0,1);
            int count = Integer.parseInt(instruction.substring(2));
            for( int i= 0; i < count; i++){
                rope[0].move(dir);
                for (int j = 1; j < 10; j++)
                    rope[j] = Follow(rope[j], rope[j-1]);
                pairs.add(rope[9].i + " " + rope[9].j);
            }
        }

        return pairs.size();
    }
}