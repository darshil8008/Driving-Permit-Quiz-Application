package ui;

import util.Session;
import util.UIStyle;
import util.AttemptStore;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PastScoresScreen extends JFrame {
    private JList<String> attemptList;
    private DefaultListModel<String> listModel;
    private JTextArea detailArea;
    private java.util.List<AttemptStore.Attempt> attempts;

    public PastScoresScreen() {
        UIStyle.apply(this, "Driving Permit — Past Scores");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(820, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(12, 12));

        if (!Session.isLoggedIn()) {
            dispose();
            new LoginScreen().setVisible(true);
            return;
        }

        JLabel title = UIStyle.heading("Past Results for " + Session.getCurrentUser().getUsername());
        title.setBorder(BorderFactory.createEmptyBorder(16, 16, 0, 16));
        add(title, BorderLayout.NORTH);

        attempts = AttemptStore.loadAttemptsFor(Session.getCurrentUser().getUsername());
        listModel = new DefaultListModel<String>();
        for (AttemptStore.Attempt a : attempts) listModel.addElement(a.title());
        attemptList = new JList<String>(listModel);

        detailArea = new JTextArea();
        detailArea.setEditable(false);
        detailArea.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(attemptList), new JScrollPane(detailArea));
        split.setResizeWeight(0.35);
        add(split, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottom.setOpaque(false);
        JButton home = UIStyle.primaryButton("Home");
        bottom.add(home);
        add(bottom, BorderLayout.SOUTH);

        attemptList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int i = attemptList.getSelectedIndex();
                if (i >= 0) detailArea.setText(attempts.get(i).details());
            }
        });

        home.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new HomeScreen().setVisible(true);
            }
        });

        if (!attempts.isEmpty()) {
            attemptList.setSelectedIndex(0);
            detailArea.setText(attempts.get(0).details());
        } else {
            detailArea.setText("No past attempts found.");
        }
    }
}
