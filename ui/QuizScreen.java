package ui;

import model.Question;
import model.Quiz;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizScreen extends JFrame {
    private int index = 0;
    private Quiz quiz;
    private ButtonGroup group;
    private JRadioButton[] options;
    private JLabel questionLabel;
    private JLabel counterLabel;
    private JButton prevButton;
    private JButton nextButton;
    private JButton submitButton;

    public QuizScreen(Quiz quiz) {
        this.quiz = quiz;
        setTitle("Quiz");
        setSize(520, 340);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel top = new JPanel(new BorderLayout());
        questionLabel = new JLabel();
        counterLabel = new JLabel("", SwingConstants.RIGHT);
        top.add(questionLabel, BorderLayout.WEST);
        top.add(counterLabel, BorderLayout.EAST);

        JPanel optionPanel = new JPanel(new GridLayout(4, 1, 6, 6));
        group = new ButtonGroup();
        options = new JRadioButton[4];
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            group.add(options[i]);
            optionPanel.add(options[i]);
        }

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");
        submitButton = new JButton("Submit");
        bottom.add(prevButton);
        bottom.add(nextButton);
        bottom.add(submitButton);

        add(top, BorderLayout.NORTH);
        add(optionPanel, BorderLayout.CENTER);
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

        submitButton.addActionListener(new ActionListener() {
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
    }

    private void showQuestion() {
        Question q = quiz.getQuestions().get(index);
        questionLabel.setText("Q" + (index + 1) + ": " + q.getQuestionText());
        counterLabel.setText("Question " + (index + 1) + " of " + quiz.getQuestionCount());

        String[] opts = q.getOptions();
        group.clearSelection();
        for (int i = 0; i < 4; i++) {
            options[i].setText(opts[i]);
        }

        int prevSel = quiz.getUserAnswer(index);
        if (prevSel >= 0 && prevSel < 4) {
            options[prevSel].setSelected(true);
        }

        prevButton.setEnabled(index > 0);
        nextButton.setEnabled(index < quiz.getQuestionCount() - 1);
    }
}
