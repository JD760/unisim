package y111studios.utils;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test that the Int Range class contains the correct values & endpoints.
 */
public class IntRangeTest {

  @Test
  void testConstructor() {
    // Test constructor does not allow start > end
    assertThrows(IllegalArgumentException.class, () -> new IntRange(5, 3));
    // Test constructor allows start < end
    assertDoesNotThrow(() -> new IntRange(0, 5));
    // Test constructor allows start == end
    assertDoesNotThrow(() -> new IntRange(5, 5));
  }

  @Test
  void testContainsValue() {
    IntRange range = new IntRange(0, 5);
    // Test value within range
    assertTrue(range.contains(3));
    // Test value at start of range
    assertTrue(range.contains(0));
    // Test value at end of range
    assertFalse(range.contains(5));
    // Test value below range
    assertFalse(range.contains(-1));
    // Test value above range
    assertFalse(range.contains(6));
  }

}
