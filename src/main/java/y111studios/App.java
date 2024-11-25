package y111studios;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/**
 * Entry class for the program to begin running.
 */
public class App {
  /**
   * Main method that initialised the LibGDX application in a new window.

   * @param args the command line arguments
   */
  public static void main(String[] args) {
    Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
    new Lwjgl3Application(new Main(), config);
  }
}
