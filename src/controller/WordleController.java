package controller;

import model.WordleModel;
import view.FeatureListener;
import view.WordleView;

/**
 * Controller of the game.
 */
public class WordleController implements FeatureListener {
  private final WordleModel model;
  private final WordleView view;

  /**
   * Constructor of the class.
   */
  public WordleController(WordleModel model, WordleView view) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Model or view should not be null.");
    }
    this.model = model;
    this.view = view;
    this.view.addListener(this);
  }

  /**
   * Start the game.
   */
  public void start() {
    view.makeVisible(true);
  }

  @Override
  public void onPlayerGuess(String guess) {
    try {
      model.guess(guess);
    } catch (IllegalArgumentException e) {
      view.showMessage(e.getMessage());
    }
    if (model.isGameOver()) {
      view.stopInput();
      if (model.didWIn()) {
        view.showMessage("YOU WON! The word was: " + model.getTargetWord());
      } else {
        view.showMessage("YOU LOSE... The word was: " + model.getTargetWord());
      }
    }
  }
}
