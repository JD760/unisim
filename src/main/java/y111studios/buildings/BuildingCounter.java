package y111studios.buildings;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

/**
 * A counter class to keep track of the number of buildings. This class keeps
 * track of the number of
 * each type of building and the total number of buildings placed.
 */
public class BuildingCounter {

  /**
   * A map to store the count of each type of building.
   */
  private @Getter final Map<BuildingType, Integer> buildingMap;

  // counts the total number of buildings placed
  private int count;

  /**
   * Creates a new building counter starting with 0 buildings placed.
   */
  public BuildingCounter() {
    buildingMap = new HashMap<>();
    for (BuildingType type : BuildingType.values()) {
      buildingMap.put(type, 0);
    }
    count = 0; // Initialise the total number of buildings to be 0.
  }

  /**
   * Increments the count for the specified building type and the total count.

   * @param type the type of building for which the corresponding counter should be incremented.
   */
  public void placeBuilding(BuildingType type) {
    buildingMap.put(type, buildingMap.get(type) + 1);
    count++; // Increment the total number of buildings when one is placed.
  }

  /**
   * Increments the count for the type of the building and the total count.

   * @param building The building to increment the type count for.
   */
  public void placeBuilding(Building building) {
    BuildingType type = BuildingType.fromBuilding(building);
    placeBuilding(type);
  }

  /**
   * Decrements the count for the specified building type and the total count.

   * @param type The type of building to decrement.
   */
  public void removeBuilding(BuildingType type) {
    buildingMap.put(type, buildingMap.get(type) - 1);
    count--;
  }

  /**
   * Decrements the count for the type of the building and the total count.

   * @param building The building to decrement the type count for.
   */
  public void removeBuilding(Building building) {
    BuildingType type = BuildingType.fromBuilding(building);
    removeBuilding(type);
  }

  /**
   * Returns the count for the specified building type.

   * @param type The type of building to retrieve the count for.
   * @return The count for the specified building type.
   */
  public int getBuildingCount(BuildingType type) {
    return buildingMap.get(type);
  }

  public int getTotalCount() {
    return count;
  }

  /**
   * Resets the building counter to have 0 buildings placed.
   */
  public void reset() {
    for (BuildingType type : BuildingType.values()) {
      buildingMap.put(type, 0);
    }
    count = 0;
  }

}
