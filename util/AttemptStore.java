package util;

import model.Quiz;
import model.Question;

import java.io.*;
import java.util.*;

public class AttemptStore {
    private static final String FILE = "attempts.txt";

    private static void ensure() {
        try {
            File f = new File(FILE);
            if (!f.exists()) f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveAttempt(String username, Quiz quiz) {
        ensure();
        int score = quiz.calculateScore();
        int total = quiz.getQuestionCount();
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(FILE, true));
            bw.write("USER:" + username);
            bw.newLine();
            bw.write("DATE:" + new Date().toString());
            bw.newLine();
            bw.write("SCORE:" + score);
            bw.newLine();
            bw.write("TOTAL:" + total);
            bw.newLine();
            for (int i = 0; i < total; i++) {
                Question q = quiz.getQuestions().get(i);
                String qt = q.getQuestionText().replace("\n", " ").replace("\r", " ");
                String correct = quiz.correctAnswerLabel(i);
                String user = quiz.userAnswerLabel(i);
                bw.write("Q" + (i + 1) + "|" + qt + "|Correct:" + correct + "|Your:" + user);
                bw.newLine();
            }
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { if (bw != null) bw.close(); } catch (IOException ignored) {}
        }
    }

    public static java.util.List<Attempt> loadAttemptsFor(String username) {
        ensure();
        java.util.List<Attempt> list = new ArrayList<Attempt>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(FILE));
            String line;
            Attempt cur = null;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    if (cur != null && username.equals(cur.user)) list.add(cur);
                    cur = null;
                    continue;
                }
                if (line.startsWith("USER:")) {
                    if (cur != null && username.equals(cur.user)) list.add(cur);
                    cur = new Attempt();
                    cur.user = line.substring(5);
                } else if (cur != null && line.startsWith("DATE:")) {
                    cur.date = line.substring(5);
                } else if (cur != null && line.startsWith("SCORE:")) {
                    try { cur.score = Integer.parseInt(line.substring(6)); } catch (Exception ignored) {}
                } else if (cur != null && line.startsWith("TOTAL:")) {
                    try { cur.total = Integer.parseInt(line.substring(6)); } catch (Exception ignored) {}
                } else if (cur != null && line.startsWith("Q")) {
                    cur.lines.add(line);
                }
            }
            if (cur != null && username.equals(cur.user)) list.add(cur);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { if (br != null) br.close(); } catch (IOException ignored) {}
        }
        return list;
    }

    public static class Attempt {
        public String user;
        public String date;
        public int score;
        public int total;
        public java.util.List<String> lines = new ArrayList<String>();
        public String title() { return date + "  —  " + score + "/" + total; }
        public String details() {
            StringBuilder sb = new StringBuilder();
            for (String s : lines) {
                String[] parts = s.split("\\|", -1);
                String qn = parts.length > 0 ? parts[0] : "";
                String qt = parts.length > 1 ? parts[1] : "";
                String corr = parts.length > 2 ? parts[2] : "";
                String your = parts.length > 3 ? parts[3] : "";
                sb.append(qn).append(": ").append(qt).append("\n")
                  .append("    ").append(corr).append("   ").append(your).append("\n\n");
            }
            return sb.toString();
        }
    }
}
