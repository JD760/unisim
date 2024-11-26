package y111studios.buildings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import y111studios.buildings.premade_variants.AccommodationVariant;
import y111studios.buildings.premade_variants.CateringVariant;
import y111studios.buildings.premade_variants.RecreationVariant;
import y111studios.buildings.premade_variants.TeachingVariant;
import y111studios.position.GridPosition;

/**
 * Test working with Buildings of different types.
 */
public class BuildingTypeTest {

  @Test
  void fromBuilding() {
    assertThrows(IllegalArgumentException.class, () -> BuildingType.fromBuilding(null));

    final GridPosition origin = new GridPosition(0, 0);
    Building building;

    building = BuildingFactory.createBuilding(AccommodationVariant.SMALL_HOUSE, origin);
    assertEquals(BuildingType.ACCOMMODATION, BuildingType.fromBuilding(building));

    building = BuildingFactory.createBuilding(CateringVariant.FAST_FOOD, origin);
    assertEquals(BuildingType.CATERING, BuildingType.fromBuilding(building));

    building = BuildingFactory.createBuilding(RecreationVariant.PARK, origin);
    assertEquals(BuildingType.RECREATION, BuildingType.fromBuilding(building));

    building = BuildingFactory.createBuilding(TeachingVariant.SMALL_CLASSROOM, origin);
    assertEquals(BuildingType.TEACHING, BuildingType.fromBuilding(building));
  }
}
