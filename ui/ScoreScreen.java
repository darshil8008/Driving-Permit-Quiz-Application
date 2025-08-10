package ui;

import model.Quiz;
import util.ScoreStore;
import util.Session;

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
        int total = quiz.getQuestions().size();

        // Save score
        if (Session.isLoggedIn()) {
            ScoreStore.saveScore(Session.getCurrentUser().getUsername(), score, total);
        }

        JLabel scoreLabel = new JLabel("You scored: " + score + " / " + total);
        JButton homeButton = new JButton("Return Home");

        add(scoreLabel);
        add(homeButton);

        homeButton.addActionListener(e -> {
            dispose();
            new HomeScreen().setVisible(true);
        });
    }
}
