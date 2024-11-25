package y111studios.position;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Test the Grid Area and edge cases associated with placement/area construction.
 */
public class GridAreaTest {

  @Tag("Constructor")
  @Test
  void preventNullCreation() {
    assertThrows(IllegalArgumentException.class, () -> {
      new GridArea(null, 1, 1);
    });
  }

  @Tag("Constructor")
  @Test
  void preventZeroSizedCreations() {
    assertThrows(IllegalArgumentException.class, () -> {
      new GridArea(0, 0, 0, 1);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      new GridArea(0, 0, 1, 0);
    });
  }

  @ParameterizedTest
  @MethodSource("provideAreasAndPoints")
  void containsPosition(GridArea area, GridPosition position, boolean answer) {
    assertEquals(area.contains(position), answer);
  }

  static Stream<Arguments> provideAreasAndPoints() {
    GridArea smallArea = new GridArea(0, 0, 5, 5);
    GridArea nonNormalisedArea = new GridArea(4, 3, 6, 7);
    return Stream.of(Arguments.of(smallArea, null, false),
        Arguments.of(smallArea, new GridPosition(0, 0), true),
        Arguments.of(smallArea, new GridPosition(3, 3), true),
        Arguments.of(smallArea, new GridPosition(5, 3), false),
        Arguments.of(smallArea, new GridPosition(3, 5), false),
        Arguments.of(nonNormalisedArea, null, false),
        Arguments.of(nonNormalisedArea, new GridPosition(0, 0), false),
        Arguments.of(nonNormalisedArea, new GridPosition(5, 0), false),
        Arguments.of(nonNormalisedArea, new GridPosition(5, 7), true),
        Arguments.of(nonNormalisedArea, new GridPosition(6, 10), false),
        Arguments.of(nonNormalisedArea, new GridPosition(10, 5), false));
  }
}
