import java.io.*;
import java.util.*;

public class DatabaseManager {

    public static List<Question> loadSampleQuestions() {
        List<Question> questions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("questions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String questionText = line;
                String[] options = new String[4];
                for (int i = 0; i < 4; i++) {
                    options[i] = reader.readLine();
                }
                int correctIndex = Integer.parseInt(reader.readLine());
                questions.add(new Question(questionText, options, correctIndex));
                reader.readLine(); // Skip the blank line
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading questions. Please check questions.txt format.");
        }

        Collections.shuffle(questions);
        return questions.size() > 20 ? questions.subList(0, 20) : questions;
    }

    public static User getHardcodedUser() {
        return new User("test", "pass");
    }
}
