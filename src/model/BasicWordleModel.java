package model;

import java.util.ArrayList;
import java.util.List;

public class BasicWordleModel implements WordleModel {
    private List<String> guessHistory;
    private int guessLeft;
    private String target;
    private int totalNumGuess;

    public BasicWordleModel(String target, int totalNumGuess) {
        this.guessHistory = new ArrayList<>();
        this.guessLeft = 5;
        this.target = target;
        this.totalNumGuess = totalNumGuess;
    }


    @Override
    public boolean isGameOver() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isGameOver'");
    }

    @Override
    public String getTargetWord() {
        return "";
    }

    @Override
    public List<String> getGuessHistory() {
        List<String> copy = new ArrayList<>();
        for (String word : guessHistory) {
            copy.add(word);
        }
        return copy;
    }

    @Override
    public int getTotalNumGuess() {
        return this.totalNumGuess;
    }

    @Override
    public List<Letter> getCheckResult(int index) {
        
    }

    @Override
    public void guess(String userGuess) {

    }

}