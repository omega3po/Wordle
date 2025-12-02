package view;

/**
 * Interface for wordle view.
 */
public interface WordleView {
  /**
   * render the view.
   */
  void render();

  /**
   * Set the visibility of the view.
   *
   * @param visible true for visible and false for no
   */
  void makeVisible(boolean visible);

  /**
   * Add listener to the view.
   *
   * @param listener listener that cares about view's notification
   */
  void addListener(FeatureListener listener);

  /**
   * Show message through JOptionPanel.
   *
   * @param message message content
   */
  void showMessage(String message);
}
