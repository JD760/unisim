package y111studios.utils;

/**
 * An exception that is thrown when code that is presumed to be unreachable is
 * executed.
 * 
 * <p>
 * This exception is used to indicate that a certain piece of code should never
 * be executed. If it
 * is then one of the assumed invariants of the program has been violated and
 * either the case needs
 * to be handled or the code needs to be fixed.
 * </p>
 */
public class UnreachableException extends RuntimeException {

  private static final String DEFAULT_MESSAGE = "Presumed unreachable code executed";

  public UnreachableException() {
    super(DEFAULT_MESSAGE + ".");
  }

  public UnreachableException(Throwable cause) {
    super(DEFAULT_MESSAGE + ".", cause);
  }

  public UnreachableException(String message) {
    super(String.format("%s : %s", DEFAULT_MESSAGE, message));
  }

  public UnreachableException(String message, Throwable cause) {
    super(String.format("%s : %s", DEFAULT_MESSAGE, message), cause);
  }

}
