package model;

import java.util.List;

public class Quiz {
    private List<Question> questions;
    private int[] userAnswers;

    public Quiz(List<Question> questions) {
        this.questions = questions;
        this.userAnswers = new int[questions.size()];
        for (int i = 0; i < userAnswers.length; i++) userAnswers[i] = -1;
    }

    public List<Question> getQuestions() { return questions; }
    public int getQuestionCount() { return questions.size(); }

    public void answerQuestion(int index, int selected) {
        if (index >= 0 && index < userAnswers.length) userAnswers[index] = selected;
    }

    public int getUserAnswer(int index) {
        if (index >= 0 && index < userAnswers.length) return userAnswers[index];
        return -1;
    }

    public int calculateScore() {
        int c = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (userAnswers[i] == questions.get(i).getCorrectIndex()) c++;
        }
        return c;
    }

    public double calculatePercentage() {
        if (questions.isEmpty()) return 0.0;
        return (calculateScore() * 100.0) / questions.size();
    }

    public String correctAnswerLabel(int index) {
        return indexToLabel(questions.get(index).getCorrectIndex());
    }

    public String userAnswerLabel(int index) {
        int ua = getUserAnswer(index);
        if (ua < 0) return "-";
        return indexToLabel(ua);
    }

    private String indexToLabel(int i) {
        if (i == 0) return "A";
        if (i == 1) return "B";
        if (i == 2) return "C";
        if (i == 3) return "D";
        return "-";
    }
}
