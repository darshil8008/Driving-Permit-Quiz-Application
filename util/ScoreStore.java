package util;

import java.io.*;
import java.util.*;

public class ScoreStore {

    private static final String SCORE_FILE = "scores.txt";

    private static void ensureFile() {
        try {
            File f = new File(SCORE_FILE);
            if (!f.exists()) {
                f.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveScore(String username, int score, int total) {
        ensureFile();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(SCORE_FILE, true))) {
            String time = new Date().toString();
            bw.write(username + "," + score + "," + total + "," + time);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getScoresForUser(String username) {
        ensureFile();
        List<String> scores = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(SCORE_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 4 && parts[0].equals(username)) {
                    scores.add("Score: " + parts[1] + "/" + parts[2] + " on " + parts[3]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores;
    }
}
