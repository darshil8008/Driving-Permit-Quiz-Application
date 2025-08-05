import javax.swing.*;
import java.awt.*;

public class HomeScreen extends JFrame {
    public HomeScreen(User user) {
        setTitle("Home");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        JLabel welcome = new JLabel("Welcome, " + user.getUsername() + "!");
        JButton startQuiz = new JButton("Start Quiz");

        add(welcome);
        add(startQuiz);

        startQuiz.addActionListener(e -> {
            dispose();
            Quiz quiz = new Quiz(DatabaseManager.loadSampleQuestions());
            new QuizScreen(quiz).setVisible(true);
        });
    }
}
