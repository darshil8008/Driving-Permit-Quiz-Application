import javax.swing.*;
import java.awt.*;
import java.util.List;

public class QuizScreen extends JFrame {
    private int index = 0;
    private Quiz quiz;
    private ButtonGroup group;
    private JRadioButton[] options;
    private JLabel questionLabel;

    public QuizScreen(Quiz quiz) {
        this.quiz = quiz;
        setTitle("Quiz");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        questionLabel = new JLabel();
        JPanel optionPanel = new JPanel(new GridLayout(4, 1));
        group = new ButtonGroup();
        options = new JRadioButton[4];

        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            group.add(options[i]);
            optionPanel.add(options[i]);
        }

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            for (int i = 0; i < 4; i++) {
                if (options[i].isSelected()) {
                    quiz.answerQuestion(index, i);
                }
            }
            index++;
            if (index < quiz.getQuestions().size()) {
                showQuestion();
            } else {
                dispose();
                new ScoreScreen(quiz).setVisible(true);
            }
        });

        add(questionLabel, BorderLayout.NORTH);
        add(optionPanel, BorderLayout.CENTER);
        add(nextButton, BorderLayout.SOUTH);

        showQuestion();
    }

    private void showQuestion() {
        Question q = quiz.getQuestions().get(index);
        questionLabel.setText("Q" + (index + 1) + ": " + q.getQuestionText());
        String[] opts = q.getOptions();
        group.clearSelection();
        for (int i = 0; i < 4; i++) {
            options[i].setText(opts[i]);
        }
    }
}
