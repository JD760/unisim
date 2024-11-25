package y111studios;

import java.util.Map;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

import y111studios.buildings.BuildingFactory;
import y111studios.buildings.premade_variants.VariantProperties;
import y111studios.utils.MenuTab;

public class WorldInputProcessor implements InputProcessor {
    Integer currentMenuItem;
    World world;
    GameState gameState;
    Map<MenuTab, VariantProperties[]> buildingVariants;
    MenuTab currentMenuTab;
    Viewport viewport;

    public boolean keyDown (int keycode) {
      return false;
   }

   public boolean keyUp (int keycode) {
      return false;
   }

   public boolean keyTyped (char character) {
      return false;
   }

   public boolean touchDown (int screenX, int screenY, int pointer, int button) {
      Vector3 screenPos = viewport.getCamera().unproject(
         new Vector3(screenX, screenY, 0),
         viewport.getScreenX(), viewport.getScreenY(),
         viewport.getScreenWidth(), viewport.getScreenHeight()
      );
      if (currentMenuItem >= 0 && currentMenuItem < 5) {
          world.addObject(buildingVariants.get(currentMenuTab)[currentMenuItem],
              world.pixelToTile(
                  (int)(screenPos.x * world.getCamera().scale),
                  (int)(screenPos.y * world.getCamera().scale)
              )
          );
          currentMenuItem = -1;
      } else if(currentMenuItem == 5) {
         try{
             world.removeObject(world.pixelToTile((int)(screenPos.x * world.getCamera().scale), (int)(screenPos.y * world.getCamera().scale)));
         } catch(IllegalStateException ignored) {
         }
      }
      VariantProperties variant = buildingVariants.get(currentMenuTab)[currentMenuItem];
      World.setSelectedBuilding(BuildingFactory.createBuilding(variant, world.currentGridPosition()));
      return true;
   }

    public boolean touchUp (int x, int y, int pointer, int button) {
        return false;
    }

    public boolean touchDragged (int x, int y, int pointer) {
        return false;
    }

    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean mouseMoved (int x, int y) {
        return false;
    }

    public boolean scrolled (float amountX, float amountY) {
        return false;
    }
}
