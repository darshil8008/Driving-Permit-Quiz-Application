package ui;

import model.Question;
import model.Quiz;
import util.Session;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeScreen extends JFrame {
    public HomeScreen() {
        setTitle("Home");
        setSize(380, 220);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        if (!Session.isLoggedIn()) {
            dispose();
            new LoginScreen().setVisible(true);
            return;
        }

        JLabel welcome = new JLabel("Welcome, " + Session.getCurrentUser().getUsername() + "!");
        JButton startQuiz = new JButton("Start Quiz");
        JButton viewScores = new JButton("View Past Scores");
        JButton logout = new JButton("Logout");

        add(welcome);
        add(startQuiz);
        add(viewScores);
        add(logout);

        startQuiz.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Question> qs = loadQuestions("questions.txt");
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

    private List<Question> loadQuestions(String path) {
        List<Question> list = new ArrayList<Question>();
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
                    if (opt == null) return list;
                    options[i] = opt.trim();
                }

                String correctLine = br.readLine();
                if (correctLine == null) return list;
                int correctIndex = Integer.parseInt(correctLine.trim());

                list.add(new Question(questionText, options, correctIndex));

                br.readLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try { if (br != null) br.close(); } catch (IOException ignored) {}
        }
        Collections.shuffle(list);
        if (list.size() > 20) return new ArrayList<Question>(list.subList(0, 20));
        return list;
    }

    private String readNonEmpty(BufferedReader br) throws IOException {
        String s;
        while ((s = br.readLine()) != null) {
            if (!s.trim().isEmpty()) return s;
        }
        return null;
    }
}
