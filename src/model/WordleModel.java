package model;

public interface WordleModel extends ReadonlyWordleModel {
    public void guess(String userGuess);
}