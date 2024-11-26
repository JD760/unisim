package y111studios.buildings;

import y111studios.buildings.premade_variants.RecreationVariant;
import y111studios.position.GridPosition;

/**
 * A class representing a recreational building within the game. This class
 * extends the
 * {@link Building} class.
 *
 * @see Building
 */
public class RecreationBuilding extends Building {

  /**
   * Creates a new recreation building at the given position of the given variant.
   *
   * @param position The position of the building
   * @param variant  The variant type of the building
   */
  public RecreationBuilding(GridPosition position, RecreationVariant variant) {
    super(position, variant);
  }

}
