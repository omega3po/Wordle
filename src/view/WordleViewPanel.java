package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import model.BasicWordleModel;
import model.ReadonlyWordleModel;

/**
 * JPanel that is inside Wordle view frame.
 */
public class WordleViewPanel extends JPanel {
  private final ReadonlyWordleModel model;
  private List<FeatureListener> listeners;

  private StringBuilder guess;

  /**
   * Constructor of the class.
   */
  public WordleViewPanel(ReadonlyWordleModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model should not be null.");
    }
    this.model = model;
    this.listeners = new ArrayList<>();
    this.guess = new StringBuilder();
    this.setPreferredSize(new Dimension(500, 500));
    this.setFocusable(true);
    this.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent event) {
        handleInput(event);
      }
    });
  }

  /**
   * Add listener.
   *
   * @param listener listener to add
   */
  public void addListener(FeatureListener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Listener should not be null.");
    }
    this.listeners.add(listener);
  }

  private void confirmGuess() {
    for (FeatureListener listener : listeners) {
      listener.onPlayerGuess(guess.toString());
    }
  }

  private void handleInput(KeyEvent e) {
    int code = e.getKeyCode();

    if (code == KeyEvent.VK_ENTER) {
      confirmGuess();
      guess.setLength(0);
    } else if (code == KeyEvent.VK_BACK_SPACE) {
      if (!guess.isEmpty()) {
        guess.deleteCharAt(guess.length() - 1);
      }
    } else if (Character.isLetter(e.getKeyChar())) {
      if (guess.length() < BasicWordleModel.WORD_LENGTH) {
        guess.append(Character.toUpperCase(e.getKeyChar()));
      }
    }
    this.repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
  }
}
