package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Basic wordle model.
 */
public class BasicWordleModel implements WordleModel {
  private final List<String> guessHistory;
  private final Letter[][] guessRecord;
  private int guessLeft;
  private final String target;
  private final int totalNumGuess;
  private boolean guessedRight;

  public static final int WORD_LENGTH = 5;

  /**
   * Constructor of the class.
   *
   * @param target        the target word
   * @param totalNumGuess number of guesses allowed to the player
   */
  public BasicWordleModel(String target, int totalNumGuess) {
    this.guessHistory = new ArrayList<>();
    this.guessRecord = new Letter[totalNumGuess][WORD_LENGTH];
    this.guessLeft = totalNumGuess;
    this.target = target;
    this.totalNumGuess = totalNumGuess;
    this.guessedRight = false;
  }


  @Override
  public boolean isGameOver() {
    return guessLeft <= 0 || guessedRight;
  }

  @Override
  public String getTargetWord() {
    return target;
  }

  @Override
  public List<String> getGuessHistory() {
    return new ArrayList<>(guessHistory);
  }

  @Override
  public int getTotalNumGuess() {
    return this.totalNumGuess;
  }

  @Override
  public Letter[] getCheckResult(int index) {
    return guessRecord[index].clone();
  }

  @Override
  public void guess(String userGuess) {
    if (userGuess.length() != WORD_LENGTH) {
      throw new IllegalArgumentException("word should exactly have " + WORD_LENGTH + " letters");
    }

    Letter[] result = new Letter[WORD_LENGTH];
    guessHistory.add(userGuess);

    Map<Character, Integer> letterCount = new HashMap<>();
    for (int i = 0; i < target.length(); i++) {
      letterCount.put(target.charAt(i),
          letterCount.getOrDefault(target.charAt(i), 0) + 1);
    }

    // Put grey first.
    Arrays.fill(result, Letter.NO);

    int perfectCounter = 0;

    // Check for green.
    for (int i = 0; i < WORD_LENGTH; i++) {
      if (userGuess.charAt(i) == target.charAt(i)) {
        perfectCounter++;
        result[i] = Letter.PERFECT;
        letterCount.put(userGuess.charAt(i), letterCount.get(userGuess.charAt(i)) - 1);
      }
    }

    if (perfectCounter == WORD_LENGTH) {
      guessedRight = true;
    }

    // Check for yellow.
    for (int i = 0; i < WORD_LENGTH; i++) {
      if (result[i] == Letter.PERFECT) {
        continue;
      }
      if (target.indexOf(userGuess.charAt(i)) != -1 && letterCount.get(userGuess.charAt(i)) > 0) {
        result[i] = Letter.INCLUDE;
        letterCount.put(userGuess.charAt(i), letterCount.get(userGuess.charAt(i)) - 1);
      }
    }

    guessRecord[totalNumGuess - guessLeft] = result;
    guessLeft--;
  }

}