package y111studios.map;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import y111studios.buildings.BuildingType;
import y111studios.position.GridArea;

public class CollisionDetectionTest {

  @ParameterizedTest
  @MethodSource("provideCanPlaceBuildingCases")
  void canPlaceBuilding(CollisionDetection cd, int x, int y, int width, int height,
      boolean expected) {
    assertEquals(expected, cd.canPlaceBuilding(new GridArea(x, y, width, height)));
  }

  private static Stream<Arguments> provideCanPlaceBuildingCases() {
    CollisionDetection square = new CollisionDetection(10, 10);
    Stream<Arguments> squareCases = Stream.of(
        Arguments.of(square, 1, 1, 3, 3, true), // Typical case
        Arguments.of(square, 0, 0, 4, 4, true), // Case: start corner of map
        Arguments.of(square, 9, 9, 1, 1, true), // Case: end corner of map
        Arguments.of(square, 10, 8, 1, 1, false), // Case: x out of bounds
        Arguments.of(square, 8, 10, 1, 1, false), // Case: y out of bounds
        Arguments.of(square, 9, 8, 10, 1, false), // Case: x + width out of bounds
        Arguments.of(square, 8, 9, 1, 10, false) // Case: y + height out of bounds
    );
    CollisionDetection yRect = new CollisionDetection(10, 20);
    Stream<Arguments> yRectCases = Stream.of(
        Arguments.of(yRect, 1, 1, 3, 3, true), // Typical case
        Arguments.of(yRect, 4, 15, 4, 3, true), // Case: correct check of y vs. height
        Arguments.of(yRect, 15, 6, 2, 3, false) // Case: correct check of x vs. width
    );
    CollisionDetection xRect = new CollisionDetection(20, 10);
    Stream<Arguments> xRectCases = Stream.of(
        Arguments.of(xRect, 1, 1, 3, 3, true), // Typical case
        Arguments.of(xRect, 15, 4, 3, 4, true), // Case: correct check of y vs. height
        Arguments.of(xRect, 6, 15, 3, 2, false) // Case: correct check of x vs. width
    );
    Stream<Arguments> rectCases = Stream.concat(xRectCases, yRectCases);
    return Stream.concat(squareCases, rectCases);
  }


  @ParameterizedTest
  @MethodSource("providePlaceBuildingCases")
  void placeBuilding(CollisionDetection cd, int x, int y, int width, int height,
      boolean expected) {
    assertEquals(expected, cd.placeBuilding(new GridArea(x, y, width, height)));
  }
  /**
   * Tests collision detection when buildings are placed, firstly checking
   *  if buildings can be placed withing the bounds and not out of bounds 
   *  and then checking if buildings are blocked from being placed on top of each other.
   */
  private static Stream<Arguments> providePlaceBuildingCases() {
    CollisionDetection bound = new CollisionDetection(10, 10);
    Stream<Arguments> boundCases = Stream.of(
        Arguments.of(bound, 1, 1, 3, 3, true), // Typical case
        Arguments.of(bound, 15, 4, 3, 3, false), // Case: x out of bounds
        Arguments.of(bound, 4, 15, 3, 3, false), // Case: y out of bounds
        Arguments.of(bound, 15, 15, 3, 3, false) // Case: x and y out of bounds
    );
    CollisionDetection buildingCollision = new CollisionDetection(10, 20);
    Stream<Arguments> buildingCollisionCases = Stream.of(
        Arguments.of(buildingCollision, 1, 1, 3, 3, true), // Typical case
        Arguments.of(buildingCollision, 1, 1, 3, 3, false) // Case: Building in location

    );
    return Stream.concat(boundCases, buildingCollisionCases);
  }

  @ParameterizedTest
  @MethodSource("removeBuildingCases")
  void removeBuilding(CollisionDetection cd, int x, int y, int width, int height,
      boolean expected) {
    assertEquals(expected, cd.removeBuilding(new GridArea(x, y, width, height)));
  }
  /**
   * Tests collision detection when attempting to remove buildings, firstly checking
   *  if buildings can be removed from within the bounds and then that the method 
   *  returns false if the area is out of bounds.
   */
  private static Stream<Arguments> removeBuildingCases() {
    CollisionDetection bound = new CollisionDetection(10, 10);
    Stream<Arguments> boundCases = Stream.of(
        Arguments.of(bound, 1, 1, 3, 3, true), // Typical case
        Arguments.of(bound, 15, 4, 3, 3, false), // Case: x out of bounds
        Arguments.of(bound, 4, 15, 3, 3, false), // Case: y out of bounds
        Arguments.of(bound, 15, 15, 3, 3, false) // Case: x and y out of bounds
    );

    return (boundCases);
  }
}