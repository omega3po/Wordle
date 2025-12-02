package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import model.BasicWordleModel;
import model.Letter;
import model.ReadonlyWordleModel;

/**
 * JPanel that is inside Wordle view frame.
 */
public class WordleViewPanel extends JPanel {
  private final ReadonlyWordleModel model;
  private final List<FeatureListener> listeners;
  private boolean inputEnabled = true;

  private final StringBuilder guess;

  private static final int BOX_SIZE = 60;
  private static final int PADDING = 10;
  private static final int Y_UPPER_PADDING = 50;

  private static final Color COLOR_PERFECT = new Color(106, 170, 100);
  private static final Color COLOR_INCLUDE = new Color(201, 180, 88);
  private static final Color COLOR_NO = new Color(120, 124, 126);
  private static final Color COLOR_BORDER = new Color(150, 150, 150);

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
    this.setPreferredSize(new Dimension(500, 600));
    this.setFocusable(true);
    this.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent event) {
        handleInput(event);
      }
    });
  }

  public void disableInput() {
    this.inputEnabled = false;
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
    if (!inputEnabled) {
      return;
    }

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
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setFont(new Font("Helvetica Neue", Font.BOLD, 32));
    drawGrid(g2d);
  }

  private void drawGrid(Graphics2D g2d) {
    int totalGuess = model.getTotalNumGuess();
    List<String> guessHistory = model.getGuessHistory();

    int gridWidth =
        (BOX_SIZE * BasicWordleModel.WORD_LENGTH) + ((BasicWordleModel.WORD_LENGTH - 1) * PADDING);
    int startX = (this.getWidth() - gridWidth) / 2;

    for (int row = 0; row < totalGuess; row++) {
      for (int col = 0; col < BasicWordleModel.WORD_LENGTH; col++) {

        int x = startX + (col * BOX_SIZE + PADDING);
        int y = Y_UPPER_PADDING + (row * BOX_SIZE + PADDING);

        if (row < guessHistory.size()) {
          String pastGuess = guessHistory.get(row);
          char c = pastGuess.charAt(col);
          Letter status = model.getCheckResult(row)[col];
          drawBox(g2d, x, y, String.valueOf(c), getCorrespondingColor(status), true);
        } else if (row == guessHistory.size()) {
          if (col < guess.length()) {
            drawBox(g2d, x, y, String.valueOf(guess.charAt(col)), Color.BLACK, false);
          } else {
            drawBox(g2d, x, y, "", Color.BLACK, false);
          }
        } else {
          drawBox(g2d, x, y, "", Color.BLACK, false);
        }
      }
    }
  }

  private Color getCorrespondingColor(Letter status) {
    return switch (status) {
      case PERFECT -> COLOR_PERFECT;
      case INCLUDE -> COLOR_INCLUDE;
      case NO -> COLOR_NO;
      default -> Color.WHITE;
    };
  }

  private void drawBox(Graphics2D g2d, int x, int y, String text, Color color, boolean fill) {
    if (fill) {
      g2d.setColor(color);
      g2d.fillRect(x, y, BOX_SIZE, BOX_SIZE);
    } else {
      g2d.setColor(COLOR_BORDER);
      g2d.drawRect(x, y, BOX_SIZE, BOX_SIZE);
    }

    g2d.drawRect(x, y, BOX_SIZE, BOX_SIZE);
    if (!text.isEmpty()) {
      g2d.setColor((fill) ? Color.WHITE : Color.BLACK);
      g2d.drawString(text, x + 9, y + 32);
    }
  }

}
