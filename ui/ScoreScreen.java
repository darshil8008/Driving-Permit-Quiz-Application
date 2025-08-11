package ui;

import model.Question;
import model.Quiz;
import util.Session;
import util.UIStyle;
import util.AttemptStore;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScoreScreen extends JFrame {

    public ScoreScreen(Quiz quiz) {
        UIStyle.apply(this, "Driving Permit — Results");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(820, 560);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(12, 12));

        int total = quiz.getQuestionCount();
        int score = quiz.calculateScore();
        double pct = quiz.calculatePercentage();

        if (Session.isLoggedIn()) {
            String u = Session.getCurrentUser().getUsername();
            AttemptStore.saveAttempt(u, quiz);
        }

        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setOpaque(false);
        JLabel h = UIStyle.heading("Your Results");
        h.setBorder(BorderFactory.createEmptyBorder(16, 16, 0, 16));
        JLabel s = new JLabel("Score: " + score + "/" + total + "   |   Percentage: " + String.format("%.1f%%", pct));
        s.setFont(UIStyle.body());
        s.setBorder(BorderFactory.createEmptyBorder(0, 16, 8, 16));
        header.add(h);
        header.add(s);
        add(header, BorderLayout.NORTH);

        JTextArea details = new JTextArea();
        details.setEditable(false);
        details.setFont(new Font("Monospaced", Font.PLAIN, 13));

        StringBuilder sb = new StringBuilder();
        sb.append("Detailed review:\n\n");
        for (int i = 0; i < total; i++) {
            Question q = quiz.getQuestions().get(i);
            String[] opts = q.getOptions();
            int ci = q.getCorrectIndex();
            int ui = quiz.getUserAnswer(i);
            String cl = letter(ci);
            String ct = opt(opts, ci);
            String ul = ui >= 0 && ui < 4 ? letter(ui) : "-";
            String ut = ui >= 0 && ui < 4 ? opt(opts, ui) : "-";
            boolean ok = ui == ci;
            sb.append("Q").append(i + 1).append(": ").append(q.getQuestionText()).append("\n");
            sb.append("    Correct: ").append(cl).append(") ").append(ct).append("\n");
            sb.append("    Your   : ").append(ul).append(") ").append(ut).append("   ").append(ok ? "[Correct]" : "[Wrong]").append("\n\n");
        }
        details.setText(sb.toString());

        JScrollPane scroll = new JScrollPane(details);
        scroll.getViewport().setBackground(Color.WHITE);
        add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottom.setOpaque(false);
        JButton homeButton = UIStyle.primaryButton("Return Home");
        bottom.add(homeButton);
        add(bottom, BorderLayout.SOUTH);

        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new HomeScreen().setVisible(true);
            }
        });
    }

    private String letter(int i) {
        if (i == 0) return "A";
        if (i == 1) return "B";
        if (i == 2) return "C";
        if (i == 3) return "D";
        return "-";
    }

    private String opt(String[] o, int i) {
        if (o == null) return "-";
        if (i >= 0 && i < o.length) return o[i];
        return "-";
    }
}
