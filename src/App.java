import controller.WordleController;
import model.BasicWordleModel;
import model.WordleModel;
import view.WordleView;
import view.WordleViewFrame;

/**
 * Main class of the game.
 */
public class App {

  /**
   * Main method of the class.
   *
   * @param args arguments
   * @throws Exception exception
   */
  public static void main(String[] args) throws Exception {
    WordleModel model = new BasicWordleModel("SUNNY", 6);
    WordleView view = new WordleViewFrame(model);
    WordleController controller = new WordleController(model, view);
    controller.start();
  }
}
