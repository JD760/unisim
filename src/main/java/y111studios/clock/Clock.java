package y111studios.clock;

import java.time.Duration;
import java.time.Instant;


/**
 * A clock object to keep track of the time elapsed since its creation.
 * 
 * <p>
 * The clock implements the GameTimer interface so shares the maximum duration
 * of
 * {@value GameTimer#MAX_SECONDS} seconds. After this time has elapsed, the
 * clock will return true
 * when the {@link #isTimeUp()} method is called.
 * </p>
 *
 * @see GameTimer
 */
public class Clock implements GameTimer {

  /**
   * The duration of the clock that has elapsed before the clock was most recently
   * started.
   */
  private Duration totalDuration;
  /**
   * The time the clock was most recently started.
   */
  private Instant start;

  /**
   * Initializes a paused instance of the clock.
   */
  public Clock() {
    this.totalDuration = Duration.ZERO;
    this.start = null;
  }

  /**
   * Initializes the clock with the specified start time.
   *
   * @param start The start time of the clock.
   */
  Clock(Instant start) {
    this.totalDuration = Duration.ZERO;
    this.start = start;
  }

  // Private helper methods

  /**
   * Gets a {@link Duration} representing the elapsed time since the clock was
   * last started. If
   * the clock is paused, this method returns 0.
   *
   * @return The elapsed time since the clock was last started.
   */
  private Duration elapsedSessionTime() {
    Instant now = Instant.now();
    if (isPaused()) {
      return Duration.ZERO;
    } else {
      return Duration.between(start, now);
    }
  }

  /**
   * Gets a {@link Duration} representing the total elapsed time while the clock
   * has been running.
   *
   * @return The total counted time since the clock was created.
   */
  private Duration totalElapsedTime() {
    Duration sessionTime = elapsedSessionTime();
    return totalDuration.plusNanos(sessionTime.toNanos());
  }

  // Public interface methods

  /**
   * Returns if the clock is paused.
   *
   * @return True if the clock is paused, false otherwise.
   */
  @Override
  public boolean isPaused() {
    return start == null;
  }

  /**
   * Returns whether the maximum duration of the clock has been reached.
   *
   * @return True if the maximum duration has been reached, false otherwise.
   */
  @Override
  public boolean isTimeUp() {
    boolean isTimeUp = totalElapsedTime().compareTo(MAX_DURATION) >= 0;
    // Fix clock to 0
    if (isTimeUp) {
      totalDuration = MAX_DURATION;
    }
    return isTimeUp;
  }

  /**
   * Returns the time remaining on the clock.
   *
   * @return Duration representing the time remaining on the clock.
   */
  @Override
  public Duration timeRemaining() {
    return MAX_DURATION.minus(totalElapsedTime());
  }

  /**
   * Pauses the clock if it is not paused.
   */
  @Override
  public void pause() {
    if (isPaused()) {
      return;
    }
    Duration session = elapsedSessionTime();
    totalDuration = totalDuration.plusNanos(session.toNanos());
    start = null;
  }

  /**
   * Resumes the clock if it is paused.
   */
  @Override
  public void resume() {
    if (!isPaused() || isTimeUp()) {
      return;
    }
    start = Instant.now();
  }

}
