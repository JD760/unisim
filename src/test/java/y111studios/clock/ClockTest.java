package y111studios.clock;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import org.junit.jupiter.api.Test;

public class ClockTest {

  @Test
  void clockCreation() {
    assertDoesNotThrow(() -> new Clock());
    Clock clock = new Clock();
    assertNotNull(clock);
  }

  @Test
  void isTimeUp() {
    Instant start = Instant.now();
    Clock clock = new Clock(start);

    // Test clock isn't immediately finished
    assertFalse(clock.isTimeUp());

    // Test clock isn't finished part way through
    Instant later = start.plusSeconds(120);
    clock = new Clock(later);
    assertFalse(clock.isTimeUp());

    // Test clock is finished after 5 minutes
    Instant end = start.minusSeconds(300);
    clock = new Clock(end);
    assertTrue(clock.isTimeUp());
  }

  @Test
  void pause() {
    Clock clock = new Clock();
    clock.pause();

    assertDoesNotThrow(() -> clock.isTimeUp());
  }

  @Test
  void resume() {
    Clock clock = new Clock();
    clock.pause();
    clock.resume();

    assertDoesNotThrow(() -> clock.isTimeUp());
  }

  @Test
  void timeRemaining() {
    Clock clock = new Clock();
    clock.pause();

    assertDoesNotThrow(() -> clock.timeRemaining());
    assertFalse(clock.timeRemaining().isNegative());
  }
}
