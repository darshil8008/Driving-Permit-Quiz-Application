import java.util.List;

public class Quiz {
    private List<Question> questions;
    private int[] userAnswers; // -1 = unanswered

    public Quiz(List<Question> questions) {
        this.questions = questions;
        this.userAnswers = new int[questions.size()];
        for (int i = 0; i < userAnswers.length; i++) {
            userAnswers[i] = -1;
        }
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void answerQuestion(int index, int selectedOption) {
        if (index >= 0 && index < userAnswers.length) {
            userAnswers[index] = selectedOption;
        }
    }

    public int calculateScore() {
        int correct = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (userAnswers[i] == questions.get(i).getCorrectIndex()) {
                correct++;
            }
        }
        return correct;
    }
}