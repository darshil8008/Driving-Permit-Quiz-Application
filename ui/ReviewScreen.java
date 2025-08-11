package ui;

import model.Question;
import model.Quiz;
import util.UIStyle;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ReviewScreen extends JFrame {
    private final Quiz quiz;
    private int index = 0;

    private JLabel questionLabel;
    private JLabel counterLabel;
    private JLabel imageLabel;

    private ButtonGroup group;
    private JRadioButton[] options;

    private JButton prevButton;
    private JButton nextButton;
    private JButton finalizeButton;

    public ReviewScreen(Quiz quiz) {
        this.quiz = quiz;
        UIStyle.apply(this, "Review Answers");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(720, 540);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(12, 12));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        questionLabel = UIStyle.heading("");
        counterLabel = new JLabel("", SwingConstants.RIGHT);
        counterLabel.setFont(UIStyle.body());
        questionLabel.setBorder(BorderFactory.createEmptyBorder(16, 16, 0, 16));
        counterLabel.setBorder(BorderFactory.createEmptyBorder(16, 16, 0, 16));
        top.add(questionLabel, BorderLayout.WEST);
        top.add(counterLabel, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(10, 10));
        center.setOpaque(false);
        add(center, BorderLayout.CENTER);

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        center.add(imageLabel, BorderLayout.NORTH);

        JPanel optPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        optPanel.setOpaque(false);
        group = new ButtonGroup();
        options = new JRadioButton[4];
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            options[i].setFont(UIStyle.body());
            options[i].setOpaque(false);
            group.add(options[i]);
            optPanel.add(options[i]);
        }
        center.add(optPanel, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottom.setOpaque(false);
        prevButton = UIStyle.secondaryButton("Previous");
        nextButton = UIStyle.secondaryButton("Next");
        finalizeButton = UIStyle.primaryButton("Finalize & Submit");
        bottom.add(prevButton);
        bottom.add(nextButton);
        bottom.add(finalizeButton);
        add(bottom, BorderLayout.SOUTH);

        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveSelection();
                if (index > 0) {
                    index--;
                    showQuestion();
                }
            }
        });

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveSelection();
                if (index < quiz.getQuestionCount() - 1) {
                    index++;
                    showQuestion();
                }
            }
        });

        finalizeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveSelection();
                dispose();
                new ScoreScreen(quiz).setVisible(true);
            }
        });

        showQuestion();
    }

    private void saveSelection() {
        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected()) {
                quiz.answerQuestion(index, i);
                return;
            }
        }
        quiz.answerQuestion(index, -1);
    }

    private void showQuestion() {
        Question q = quiz.getQuestions().get(index);
        questionLabel.setText("Q" + (index + 1) + ": " + q.getQuestionText());
        counterLabel.setText("Question " + (index + 1) + " of " + quiz.getQuestionCount());

        if (q.getImagePath() != null && q.getImagePath().length() > 0 && new File(q.getImagePath()).exists()) {
            ImageIcon icon = new ImageIcon(q.getImagePath());
            Image scaled = icon.getImage().getScaledInstance(220, 220, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
            imageLabel.setText("");
        } else {
            imageLabel.setIcon(null);
            imageLabel.setText("");
        }

        String[] opts = q.getOptions();
        group.clearSelection();
        for (int i = 0; i < 4; i++) options[i].setText(opts[i]);

        int prev = quiz.getUserAnswer(index);
        if (prev >= 0 && prev < 4) options[prev].setSelected(true);

        prevButton.setEnabled(index > 0);
        nextButton.setEnabled(index < quiz.getQuestionCount() - 1);
    }
}
