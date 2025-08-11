package ui;

import model.Quiz;
import util.Session;
import util.DatabaseManager;
import util.UIStyle;

import javax.swing.*;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeScreen extends JFrame {
    public HomeScreen() {
        UIStyle.apply(this, "Driving Permit — Home");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(560, 360);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(12, 12));

        if (!Session.isLoggedIn()) {
            dispose();
            new LoginScreen().setVisible(true);
            return;
        }

        JLabel title = UIStyle.heading("Hello, " + Session.getCurrentUser().getUsername());
        title.setBorder(BorderFactory.createEmptyBorder(16, 16, 0, 16));
        add(title, BorderLayout.NORTH);

        JPanel card = UIStyle.card(24);
        card.setLayout(new GridBagLayout());
        add(card, BorderLayout.CENTER);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0; c.gridy = 0;

        JButton startQuiz = UIStyle.primaryButton("Start Quiz");
        JButton viewScores = UIStyle.secondaryButton("View Past Scores");
        JButton logout = UIStyle.secondaryButton("Logout");

        card.add(startQuiz, c);
        c.gridy++;
        card.add(viewScores, c);
        c.gridy++;
        card.add(logout, c);

        startQuiz.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<model.Question> qs = DatabaseManager.loadQuestions();
                if (qs == null || qs.isEmpty()) {
                    JOptionPane.showMessageDialog(HomeScreen.this, "No questions found. Check questions.txt.");
                    return;
                }
                dispose();
                Quiz quiz = new Quiz(qs);
                new QuizScreen(quiz).setVisible(true);
            }
        });

        viewScores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PastScoresScreen().setVisible(true);
            }
        });

        logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Session.clear();
                dispose();
                new LoginScreen().setVisible(true);
            }
        });
    }
}
