package y111studios.buildings;

import java.util.OptionalInt;
import lombok.Getter;
import y111studios.position.GridPosition;

/**
 * A manager class to maintain the buildings and the count of them.
 * 
 * <p>
 * This class provides functionality to place buildings and keep track of the
 * number of buildings,
 * extending BuildingController. The maximum number of buildings that can be
 * placed is
 * {@link #MAX_BUILDINGS MAX_BUILDINGS} ({@value #MAX_BUILDINGS}). This is a
 * fixed size array of
 * buildings, helped by a the building counter.
 * </p>

 * @see BuildingCounter
 * @see BuildingController
 */
public class BuildingManager implements BuildingController {

  /**
   * The maximum number of buildings that can be placed.
   */
  public static final int MAX_BUILDINGS = 100;

  /**
   * Fixed sized array containing the buildings.
   * 
   * <p>
   * This will always contain exactly {@link #MAX_BUILDINGS MAX_BUILDINGS}
   * ({@value #MAX_BUILDINGS}) elements
   * </p>
   * 
   * <p>
   * This array is null-initialized and will contain null elements until a
   * building is placed. The
   * array should not contain any null elements in the range of 0 to the total
   * number of buildings
   * placed. This array should not contain any elements beyond the total number of
   * buildings.
   * Additionally, this array is unordered and the order of buildings is not
   * guaranteed.
   * </p>
   */
  private Building[] buildings;
  /**
   * The building counter to keep track of the number of buildings placed.
   */
  private @Getter BuildingCounter counter;

  /**
   * Initializes the building manager with a null-initialized array of buildings
   * and a building
   * counter.
   */
  public BuildingManager() {
    this.buildings = new Building[MAX_BUILDINGS];
    this.counter = new BuildingCounter();
  }

  // Private helper methods

  /**
   * Pops the last building from the array of buildings.
   * 
   * <p>
   * This method will remove the last building from the array of buildings and
   * decrement the
   * counter. The last building is returned. If there are no buildings to pop,
   * this method returns
   * null.
   * </p>
   *
   * @return Last building from the array of buildings
   */
  private Building popLast() {
    final int index = getCount() - 1;
    if (index < 0) {
      return null;
    }
    Building building = buildings[index];
    buildings[index] = null;
    return building;
  }

  // Other public methods

  /**
   * Removes a building from the array of buildings at the specified index.
   * 
   * <p>
   * This method will remove the building at the specified index from the array of
   * buildings and
   * decrement the counter, returning true. If the index is out of bounds, this
   * method returns
   * false.
   * </p>
   *
   * @param index The index of the building to remove
   * @return if the building was successfully removed
   */
  public boolean removeIndex(int index) {
    final int length = getCount();
    if (index < 0 || index >= length) {
      return false;
    }
    Building removed;
    if (index == length - 1) {
      // If removing the last building just remove it
      removed = popLast();
    } else {
      // Swap index building with last building
      removed = buildings[index];
      buildings[index] = popLast();
    }
    counter.removeBuilding(removed);
    return true;
  }

  /**
   * Returns the index of the building that contains the specified position.
   * 
   * <p>
   * This method will return the index of the building that contains the specified
   * position. If no
   * building contains the position or the position is null, this method returns
   * an empty
   * OptionalInt.
   * </p>
   *
   * @param position The position to find the building for
   * @return The index of the building that contains the specified position
   */
  public OptionalInt indexContainingPosition(GridPosition position) {
    if (position == null) {
      return OptionalInt.empty();
    }
    for (int i = 0; i < getCount(); i++) {
      if (buildings[i].contains(position)) {
        return OptionalInt.of(i);
      }
    }
    return OptionalInt.empty();
  }

  /**
   * Returns if the building manager is empty.
   *
   * @return if the building manager is empty
   */
  public boolean isEmpty() {
    return getCount() == 0;
  }

  // Public interface methods

  /**
   * Gets the building that contains the specified position.
   * 
   * <p>
   * This method will return the building that contains the specified position. If
   * no building
   * contains the position, this method returns null. If the position is null,
   * this method returns
   * null.
   * </p>
   *
   * @param position The position to check
   * @return The building that contains the specified position
   */
  @Override
  public Building getBuilding(GridPosition position) {
    OptionalInt index = indexContainingPosition(position);
    if (index.isEmpty()) {
      return null;
    }
    return buildings[index.getAsInt()];
  }

  /**
   * Pushes a building into the array of buildings.
   *
   * <p>
   * This method will add the building to the array of buildings and increment the
   * building
   * counter. This method then returns true if the building was successfully
   * added, and false if
   * the building could not be added as the maximum number of buildings had been
   * reached.
   * </p>
   * 
   * <p>
   * If the building is null, this method does nothing as there is no building to
   * add and just
   * short-circuits true.
   * </p>
   *
   * @param building The building to add to the array of buildings.
   * @return if the building was successfully added
   */
  @Override
  public boolean push(Building building) {
    if (building == null) {
      return true;
    }
    final int index = counter.getTotalCount();
    if (this.isFull()) {
      return false;
    }
    buildings[index] = building;
    counter.placeBuilding(building);
    return true;
  }

  /**
   * Removes the building that contains the specified position.
   * 
   * <p>
   * This method will remove the building that contains the specified position
   * from the array of
   * buildings and decrement the counter, returning true. If the position is null
   * or no building
   * contains the position, this method returns false and does nothing.
   * </p>
   *
   * @param position The position to remove
   * @return if the building was successfully removed
   */
  @Override
  public boolean removePosition(GridPosition position) {
    OptionalInt index = indexContainingPosition(position);
    if (index.isEmpty()) {
      return false;
    }
    return removeIndex(index.getAsInt());
  }

  /**
   * Gets the number of buildings placed.
   *
   * @return The number of buildings placed
   */
  @Override
  public int getCount() {
    return counter.getTotalCount();
  }

  /**
   * Returns if the building manager is full.
   * 
   * <p>
   * This method returns true if the number of buildings placed is equal to
   * {@link #MAX_BUILDINGS
   * MAX_BUILDINGS} ({@value #MAX_BUILDINGS}), and false otherwise.
   * </p>
   *
   * @return if the building manager is full
   */
  public boolean isFull() {
    return getCount() >= MAX_BUILDINGS;
  }

}
