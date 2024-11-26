package y111studios;

import java.time.Duration;
import y111studios.buildings.Building;
import y111studios.buildings.BuildingController;
import y111studios.buildings.BuildingManager;
import y111studios.clock.Clock;
import y111studios.clock.GameTimer;
import y111studios.map.CollisionDetection;
import y111studios.position.GridPosition;

/**
 * A class representing the sum state of the game. This class contains the
 * clock, building manager
 * and collision detection. This class is used to manage the state of the game
 * by exposing the
 * necessary methods to alter the state of the game in a controlled manner.
 *
 * @see Clock
 * @see BuildingManager
 * @see CollisionDetection
 */
public class GameState implements GameTimer, BuildingController {

  private GameTimer timer;
  BuildingManager buildingManager;
  CollisionDetection collisionDetection;

  /**
   * Constructor for the GameState class.
   *
   * @param width  width of the game map
   * @param height height of the game map
   */
  public GameState(int width, int height) {
    timer = new Clock();
    buildingManager = new BuildingManager();
    int[][] staticObjects = new int[][] { { 11, 35, 11, 13 }, { 14, 13, 7, 10 }, { 40, 13, 15, 15 },
        { 42, 7, 17, 5 }, { 55, 29, 2, 5 }, { 30, 32, 27, 2 }, { 30, 32, 2, 21 }, { 26, 52, 5, 2 },
        { 26, 52, 2, 8 }, { 20, 60, 7, 2 }, { 14, 58, 5, 5 }, { 55, 47, 10, 13 } };
    collisionDetection = new CollisionDetection(width, height, staticObjects);
  }

  // BuildingController methods

  @Override
  public Building getBuilding(GridPosition position) {
    if (position == null) {
      return null;
    }
    if (collisionDetection.canPlaceBuilding(position)) {
      return null;
    }
    return this.buildingManager.getBuilding(position);
  }

  @Override
  public boolean push(Building building) {
    if (isPaused()) {
      return false;
    }
    if (building == null) {
      return true;
    }
    // Cannot push into a full building manager
    if (buildingManager.isFull()) {
      return false;
    }
    // Cannot push if the building area is not empty
    if (!collisionDetection.placeBuilding(building.getArea())) {
      return false;
    }
    // Push the building into the building manager
    this.buildingManager.push(building);
    return true;
  }

  @Override
  public boolean removePosition(GridPosition position) {
    if (isPaused()) {
      return false;
    }
    if (position == null) {
      return false;
    }
    // Get building being removed
    final Building building = this.buildingManager.getBuilding(position);
    if (building == null) {
      // This should never happen provided push is correctly implemented
      throw new IllegalStateException("Building not found at position: " + position);
    }
    // Remove the building from the collision detection
    this.collisionDetection.removeBuilding(building.getArea());
    this.buildingManager.removePosition(position);
    return true;
  }

  @Override
  public int getCount() {
    return this.buildingManager.getCount();
  }

  @Override
  public boolean isFull() {
    return this.buildingManager.isFull();
  }

  // GameTimer methods

  @Override
  public boolean isPaused() {
    return timer.isPaused();
  }

  @Override
  public void pause() {
    timer.pause();
  }

  @Override
  public void resume() {
    timer.resume();
  }

  @Override
  public boolean isTimeUp() {
    return timer.isTimeUp();
  }

  @Override
  public Duration timeRemaining() {
    return timer.timeRemaining();
  }

  /**
   * Returns if the building can be placed given the current state of the game.
   *
   * @param building the building to be placed
   * @return true if the building can be placed, false otherwise
   */
  public boolean canPlaceBuilding(Building building) {
    return collisionDetection.canPlaceBuilding(building.getArea());
  }

}
