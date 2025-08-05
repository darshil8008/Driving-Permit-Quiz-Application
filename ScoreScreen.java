import javax.swing.*;
import java.awt.*;

public class ScoreScreen extends JFrame {
    public ScoreScreen(Quiz quiz) {
        setTitle("Score");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        int score = quiz.calculateScore();
        JLabel scoreLabel = new JLabel("You scored: " + score + " / 20");
        JButton homeButton = new JButton("Return Home");

        add(scoreLabel);
        add(homeButton);

        homeButton.addActionListener(e -> {
            dispose();
            new HomeScreen(DatabaseManager.getHardcodedUser()).setVisible(true);
        });
    }
}