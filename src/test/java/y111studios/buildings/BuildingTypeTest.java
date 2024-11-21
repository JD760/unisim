package y111studios.buildings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import y111studios.buildings.premade_variants.AccomodationVariant;
import y111studios.buildings.premade_variants.CateringVariant;
import y111studios.buildings.premade_variants.RecreationVariant;
import y111studios.buildings.premade_variants.TeachingVariant;
import y111studios.position.GridPosition;

public class BuildingTypeTest {

  @Test
  void fromBuilding() {
    assertThrows(IllegalArgumentException.class, () -> BuildingType.fromBuilding(null));

    final GridPosition ZERO = new GridPosition(0, 0);
    Building building;

    building = BuildingFactory.createBuilding(AccomodationVariant.SMALL_HOUSE, ZERO);
    assertEquals(BuildingType.ACCOMMODATION, BuildingType.fromBuilding(building));

    building = BuildingFactory.createBuilding(CateringVariant.FAST_FOOD, ZERO);
    assertEquals(BuildingType.CATERING, BuildingType.fromBuilding(building));

    building = BuildingFactory.createBuilding(RecreationVariant.PARK, ZERO);
    assertEquals(BuildingType.RECREATION, BuildingType.fromBuilding(building));

    building = BuildingFactory.createBuilding(TeachingVariant.SMALL_CLASSROOM, ZERO);
    assertEquals(BuildingType.TEACHING, BuildingType.fromBuilding(building));
  }
}
