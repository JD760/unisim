package y111studios.position;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Grid Position class.
 */
public class GridPositionTest {
  @Tag("Constructor")
  @Test
  void preventNegativeCreation() {
    assertThrows(IllegalArgumentException.class, () -> {
      new GridPosition(-1, 0);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      new GridPosition(0, -1);
    });
  }

}
