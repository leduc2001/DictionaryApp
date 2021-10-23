package group11.dictapp;

import java.util.ArrayList;

public class Dictionary {
    ArrayList<Word> diction = new ArrayList<>();

    public ArrayList<Word> getDiction() {
        return diction;
    }

    public void setDiction(ArrayList<Word> diction) {
        this.diction = diction;
    }
}
