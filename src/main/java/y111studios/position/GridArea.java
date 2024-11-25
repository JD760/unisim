package y111studios.position;

import lombok.Getter;
import y111studios.utils.IntRange;

/**
 * A class representing a continuous rectangular area on a grid.
 *
 * @see GridPosition
 */
public class GridArea {

  /**
   * The origin of the GridArea. This is the top-left corner of the area.
   */
  private @Getter GridPosition origin;
  /**
   * The width of the GridArea in grid tiles.
   */
  private @Getter int width;
  /**
   * The height of the GridArea in grid tiles.
   */
  private @Getter int height;

  /**
   * The continuous range of x-coordinates that are within this GridArea. This
   * field is
   * lazily-initialised with the first call to {@link #getrangeX()}.
   */
  private IntRange rangeX = null;
  /**
   * The continuous range of y-coordinates that are within this GridArea. This
   * field is
   * lazily-initialised with the first call to {@link #getrangeY()}.
   */
  private IntRange rangeY = null;

  /**
   * Constructs a new GridArea with the specified origin, width, and height.
   *
   * @param origin the origin of the GridArea
   * @param width  the width of the GridArea
   * @param height the height of the GridArea
   * 
   * @throws IllegalArgumentException if the origin is null
   * @throws IllegalArgumentException if the width is less than or equal to 0
   * @throws IllegalArgumentException if the height is less than or equal to 0
   */
  public GridArea(GridPosition origin, int width, int height) {
    if (origin == null) {
      throw new IllegalArgumentException("Origin must not be null");
    }
    if (width <= 0) {
      throw new IllegalArgumentException("Width must be greater than 0");
    }
    if (height <= 0) {
      throw new IllegalArgumentException("Height must be greater than 0");
    }
    this.origin = origin;
    this.width = width;
    this.height = height;
  }

  /**
   * Constructs a new GridArea with the specified x-coordinate, y-coordinate,
   * width, and height.
   *
   * @param x      the x-coordinate of the origin
   * @param y      the y-coordinate of the origin
   * @param width  the width of the GridArea
   * @param height the height of the GridArea
   * 
   * @throws IllegalArgumentException if the x is less than 0
   * @throws IllegalArgumentException if the y is less than 0
   * @throws IllegalArgumentException if the width is less than or equal to 0
   * @throws IllegalArgumentException if the height is less than or equal to 0
   */
  public GridArea(int x, int y, int width, int height) {
    this(new GridPosition(x, y), width, height);
  }

  /**
   * Gets the x-coordinate of the origin of this GridArea.
   *
   * @return the x-coordinate of the origin
   */
  public int getX() {
    return origin.getX();
  }

  /**
   * Gets the y-coordinate of the origin of this GridArea.
   *
   * @return the y-coordinate of the origin
   */
  public int getY() {
    return origin.getY();
  }

  /**
   * Checks if the specified GridPosition is within this GridArea.
   * 
   * <p>
   * This method returns true if the x-coordinate of the other position is within
   * the range
   * [origin.x, origin.x + width) and the y-coordinate is within the range
   * [origin.y, origin.y +
   * height). If other is {@code null}, this method returns false.
   * </p>
   *
   * @param other the GridPosition to check
   * @return true if the specified position is within this area, false otherwise
   */
  public boolean contains(GridPosition other) {
    if (other == null) {
      return false;
    }
    return getrangeX().contains(other.getX()) && getRangeY().contains(other.getY());
  }

  /**
   * Checks if the specified GridArea is completely contained within this
   * GridArea.
   * 
   * <p>
   * This method returns true if the other area is completely encompassed by this
   * area. This
   * includes if the other area has the same origin and dimensions as this area.
   * </p>
   *
   * @param other - A grid area to check containment with.
   * @return = True if other contains this object, false otherwise.
   */
  public boolean contains(GridArea other) {
    if (other == null) {
      return false;
    }
    return getrangeX().contains(other.getrangeX()) && getRangeY().contains(other.getRangeY());
  }

  /**
   * Returns the range of x-coordinates that are within the GridArea.
   *
   * @return the range of x-coordinates
   */
  public IntRange getrangeX() {
    if (rangeX == null) {
      rangeX = calculateRangeX();
    }
    return rangeX;
  }

  /**
   * Returns the range of y-coordinates that are within the GridArea.
   *
   * @return the range of y-coordinates
   */
  public IntRange getRangeY() {
    if (rangeY == null) {
      rangeY = calculateRangeY();
    }
    return rangeY;
  }

  /**
   * Returns a new instance of IntRange that represents the x-coordinates of this
   * GridArea.
   *
   * @return the range of x-coordinates
   */
  private IntRange calculateRangeX() {
    return new IntRange(origin.getX(), origin.getX() + width);
  }

  /**
   * Returns a new instance of IntRange that represents the y-coordinates of this
   * GridArea.
   *
   * @return the range of y-coordinates
   */
  private IntRange calculateRangeY() {
    return new IntRange(origin.getY(), origin.getY() + height);
  }

}
