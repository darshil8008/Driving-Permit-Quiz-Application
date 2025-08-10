package util;

import model.Question;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList; 
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DatabaseManager {

    private static final String DEFAULT_FILE = "questions.txt";

    public static List<Question> loadQuestions() {
        return loadQuestionsFromFile(DEFAULT_FILE);
    }

    public static List<Question> loadQuestionsFromFile(String path) {
        List<Question> all = new ArrayList<Question>();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(path));
            String line;

            while (true) {
                line = readNonEmpty(br);
                if (line == null) break;
                String questionText = line.trim();

                String[] options = new String[4];
                for (int i = 0; i < 4; i++) {
                    String opt = br.readLine();
                    if (opt == null) return finalizeToTwenty(all);
                    options[i] = opt.trim();
                }

                String correctLine = br.readLine();
                if (correctLine == null) return finalizeToTwenty(all);
                int correctIndex = Integer.parseInt(correctLine.trim());
                if (correctIndex < 0 || correctIndex > 3) return finalizeToTwenty(all);

                all.add(new Question(questionText, options, correctIndex));

                br.readLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try { if (br != null) br.close(); } catch (IOException ignored) {}
        }

        return finalizeToTwenty(all);
    }

    private static String readNonEmpty(BufferedReader br) throws IOException {
        String s;
        while ((s = br.readLine()) != null) {
            if (!s.trim().isEmpty()) return s;
        }
        return null;
    }

    private static List<Question> finalizeToTwenty(List<Question> all) {
        List<Question> copy = new ArrayList<Question>(all);
        Collections.shuffle(copy);

        if (copy.size() == 20) {
            return copy;
        }
        if (copy.size() > 20) {
            return new ArrayList<Question>(copy.subList(0, 20));
        }
        if (copy.isEmpty()) {
            return copy;
        }

        Random rnd = new Random();
        while (copy.size() < 20) {
            copy.add(all.get(rnd.nextInt(all.size())));
        }
        return copy;
    }
}
