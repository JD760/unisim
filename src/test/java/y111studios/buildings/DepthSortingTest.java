package y111studios.buildings;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import y111studios.GameState;
import y111studios.Main;
import y111studios.World;
import y111studios.buildings.premade_variants.TeachingVariant;
import y111studios.position.GridPosition;

public class DepthSortingTest {

  @Test
  public void equalSizedBuildings() {
    GameState gameState = new GameState(75, 75);
    World world = new World(null, gameState);

    world.addObject(TeachingVariant.SUBJECT_HUB, new GridPosition(3, 3));
    world.addObject(TeachingVariant.SMALL_CLASSROOM, new GridPosition(5, 6));

    assertTrue(world.getBuildings().get(0).getArea().getOrigin() == new GridPosition(5, 6));
  }
}
