package y111studios.clock;

import java.time.Duration;

/**
 * An interface for a game timer. The timer can be paused and resumed, and has a maximum duration
 * for the game. There are methods to check if the timer has reached the maximum duration, as well
 * as to check how long is remaining on the timer.
 */
public interface GameTimer {
    /**
     * The maximum duration of the timer in seconds.
     */
    int MAX_SECONDS = 300;
    /**
     * The maximum duration of the timer.
     */
    Duration MAX_DURATION = Duration.ofSeconds(MAX_SECONDS);

    /**
     * Returns if the timer is paused.
     * 
     * @return True if the timer is paused, false otherwise.
     */
    boolean isPaused();

    /**
     * Pauses the timer. The timer will not increment while paused and can be resumed later.
     */
    void pause();

    /**
     * Resumes the timer.
     */
    void resume();

    /**
     * Returns whether the maximum duration of the timer has been reached.
     * 
     * @return True if the maximum duration has been reached, false otherwise.
     */
    boolean isTimeUp();

    /**
     * Returns the time remaining on the timer.
     * 
     * @return Duration representing the time remaining on the timer.
     */
    Duration timeRemaining();
}
