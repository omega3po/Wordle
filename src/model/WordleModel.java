package model;

public interface WordleModel extends ReadonlyWordleModel {
  void guess(String userGuess);
}