package model;

import java.util.List;

public interface ReadonlyWordleModel {
    boolean isGameOver();
    String getTargetWord();
    List<String> getGuessHistory();
    int getTotalNumGuess();
    List<Letter> getCheckResult();
}