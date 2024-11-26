package y111studios.buildings;

import y111studios.AssetPaths;
import y111studios.buildings.premade_variants.VariantProperties;
import y111studios.position.GridArea;
import y111studios.position.GridPosition;

/**
 * A class representing a building within the game.

 * @see MapObject
 */
public abstract class Building extends MapObject {

  /**
   * Constructor for a building that takes up a specified area.

   * @param area the area in grid space occupied by the building
   */
  protected Building(GridArea area, AssetPaths texturePath) {
    super(area, texturePath);
  }

  /**
   * Constructor for a building that starts at a position and has a specified
   * width and height.

   * @param position the position of the building
   * @param width    the width of the building
   * @param height   the height of the building
   */
  protected Building(GridPosition position, int width, int height, AssetPaths texturePath) {
    this(new GridArea(position, width, height), texturePath);
  }

  /**
   * Constructor for a building that starts at a position and is of a specified
   * variant.

   * @param position the position of the building
   * @param variant  the variant information of the building
   */
  protected Building(GridPosition position, VariantProperties variant) {
    this(position, variant.getWidth(), variant.getHeight(), variant.getTexturePath());
  }

}
