package y111studios.buildings;

/**
 * An enumeration of the different types of buildings that can be placed.
 */
public enum BuildingType {
  ACCOMMODATION, CATERING, RECREATION, TEACHING;

  /**
   * Returns the building type of the specified building instance.
   *
   * @param building Building instance to determine the type of.
   * @return BuildingType
   *
   * @throws IllegalArgumentException if the building is null or the type is not
   *                                  recognised.
   */
  public static BuildingType fromBuilding(Building building) {
    if (building == null) {
      throw new IllegalArgumentException("Building cannot be null.");
    }
    if (building instanceof AccommodationBuilding) {
      return ACCOMMODATION;
    } else if (building instanceof CateringBuilding) {
      return CATERING;
    } else if (building instanceof RecreationBuilding) {
      return RECREATION;
    } else if (building instanceof TeachingBuilding) {
      return TEACHING;
    }
    throw new IllegalArgumentException("Building type not recognised.");
  }
}
