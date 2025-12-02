package model;

import java.util.List;

public interface ReadonlyWordleModel {

  boolean didWIn();

  boolean isGameOver();

  String getTargetWord();

  List<String> getGuessHistory();

  int getTotalNumGuess();

  Letter[] getCheckResult(int index);
}