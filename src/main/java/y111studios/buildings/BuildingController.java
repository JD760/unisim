package y111studios.buildings;

import y111studios.position.GridPosition;

/**
 * A controller interface to manage the buildings and maintain an accurate
 * count.
 */
public interface BuildingController {

  /**
   * Gets the building that contains the specified position.

   * @param position The position to check
   * @return The building that contains the specified position
   */
  Building getBuilding(GridPosition position);

  /**
   * Pushes a building into the array of buildings.

   * @param building The building to add.
   * @return if the building was successfully added
   */
  boolean push(Building building);

  /**
   * Removes the building that contains the specified position.

   * @param position The position to remove
   * @return if the building was successfully removed
   */
  boolean removePosition(GridPosition position);

  public int getCount();

  boolean isFull();

}
