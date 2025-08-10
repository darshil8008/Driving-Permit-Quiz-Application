package ui;

import util.ScoreStore;
import util.Session;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PastScoresScreen extends JFrame {

    public PastScoresScreen() {
        setTitle("Past Scores");
        setSize(420, 360);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        if (!Session.isLoggedIn()) {
            dispose();
            new LoginScreen().setVisible(true);
            return;
        }

        String username = Session.getCurrentUser().getUsername();
        List<String> scores = ScoreStore.getScoresForUser(username);

        if (scores.isEmpty()) {
            add(new JLabel("No past scores found for " + username, SwingConstants.CENTER), BorderLayout.CENTER);
        } else {
            JList<String> list = new JList<String>(scores.toArray(new String[0]));
            JScrollPane scroll = new JScrollPane(list);
            add(scroll, BorderLayout.CENTER);
        }

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton home = new JButton("Home");
        bottom.add(home);
        add(bottom, BorderLayout.SOUTH);

        home.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new HomeScreen().setVisible(true);
            }
        });
    }
}
