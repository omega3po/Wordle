package view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.ReadonlyWordleModel;

/**
 * Class that implement WordleView.
 */
public class WordleViewFrame extends JFrame implements WordleView {
  private final WordleViewPanel panel;

  /**
   * Constructor of the class.
   *
   * @param model read only model for getting game info
   */
  public WordleViewFrame(ReadonlyWordleModel model) {
    super("Wordle Game");
    if (model == null) {
      throw new IllegalArgumentException("Model should not be null.");
    }

    this.panel = new WordleViewPanel(model);
    this.add(panel);

    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.pack();
    this.setFocusable(true);
  }

  @Override
  public void render() {
    this.repaint();
  }

  @Override
  public void makeVisible(boolean visible) {
    this.setVisible(visible);
    if (visible) {
      this.panel.requestFocusInWindow();
    }
  }

  @Override
  public void addListener(FeatureListener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Listener should not be null.");
    }
    this.panel.addListener(listener);
  }

  @Override
  public void showMessage(String message) {
    JOptionPane.showMessageDialog(this, message);
  }
}
