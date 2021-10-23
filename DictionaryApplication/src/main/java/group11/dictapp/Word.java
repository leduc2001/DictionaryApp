package group11.dictapp;

public class Word { //class word
    private String word_target; //từ tiếng anh
    private String word_explain; //nghĩa tiếng việt

    public String getWord_explain() {
        return word_explain;
    }

    public String getWord_target() {
        return word_target;
    }

    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }

    Word(String Target, String Explain) {
        this.word_target = Target;
        this.word_explain = Explain;
    }

    Word() {
    }
}
