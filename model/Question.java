package model;

public class Question {
    private String questionText;
    private String[] options;
    private int correctIndex;
    private String imagePath;

    public Question(String questionText, String[] options, int correctIndex) {
        this(questionText, options, correctIndex, null);
    }

    public Question(String questionText, String[] options, int correctIndex, String imagePath) {
        this.questionText = questionText;
        this.options = options;
        this.correctIndex = correctIndex;
        this.imagePath = imagePath;
    }

    public String getQuestionText() { return questionText; }
    public String[] getOptions() { return options; }
    public int getCorrectIndex() { return correctIndex; }
    public String getImagePath() { return imagePath; }
    public boolean hasImage() { return imagePath != null && imagePath.trim().length() > 0; }
    public boolean isCorrect(int i) { return i == correctIndex; }
}
