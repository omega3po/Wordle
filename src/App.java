import controller.WordleController;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
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
    Set<String> dictionary = new HashSet<>();
    try (
        BufferedReader reader = new BufferedReader(new FileReader("docs/valid-wordle-words.txt"))) {
      String line;
      while ((line = reader.readLine()) != null) {
        dictionary.add(line.trim().toUpperCase());
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    List<String> wordList = new ArrayList<>(dictionary);
    Random random = new Random();
    int randomIndex = random.nextInt(wordList.size());
    String targetWord = wordList.get(randomIndex);

    System.out.println("Target Word is: " + targetWord);

    WordleModel model = new BasicWordleModel(targetWord, 6, dictionary);
    WordleView view = new WordleViewFrame(model);
    WordleController controller = new WordleController(model, view);
    controller.start();
  }
}
