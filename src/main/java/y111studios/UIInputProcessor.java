package y111studios;

import java.util.Map;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

import y111studios.buildings.BuildingFactory;
import y111studios.buildings.premade_variants.VariantProperties;
import y111studios.utils.MenuTab;

public class UIInputProcessor implements InputProcessor {
    Viewport viewport;
    MenuTab currentMenuTab;
    Integer currentMenuItem;
    World world;
    GameState gameState;
    Map<MenuTab, VariantProperties[]> buildingVariants;
    Boolean showDebugInfo;

    UIInputProcessor(
        Viewport viewport, MenuTab currentMenuTab, Integer currentMenuItem, World world,
        GameState gameState, Map<MenuTab, VariantProperties[]> buildingVariants,
        Boolean showDebugInfo
    ) {
        this.viewport = viewport;
        this.currentMenuTab = currentMenuTab;
        this.currentMenuItem = currentMenuItem;
        this.world = world;
        this.gameState = gameState;
        this.buildingVariants = buildingVariants;
        this.showDebugInfo = showDebugInfo;
    }

    public boolean keyDown(int keyCode) {
        if(keyCode == Input.Keys.ESCAPE) {
            if (gameState.isPaused()) {
                gameState.resume();    
            } else {
                world.getCamera().velocityReset();
                gameState.pause();
            }
            world.getCamera().addVelocity(-1 * world.getCamera().vx, -1 * world.getCamera().vy);
            return true;
        }
        if(gameState.isPaused()) {
            return true;
        }
        if (keyCode == Input.Keys.TAB) {
            showDebugInfo = !showDebugInfo;
            return true;
        }
        return false;
    }

    public boolean keyUp (int keycode) {
        return false;
    }

    public boolean keyTyped (char character) {
        return false;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(gameState.isPaused()) {
            return true;
        }
        Vector3 screenPos = viewport.getCamera().unproject(new Vector3(screenX, screenY, 0), viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());
        if(screenPos.y < 100) {
            if(screenPos.y > 80) {
                if(screenPos.x < 155) {
                    currentMenuTab = MenuTab.ACCOMMODATION;
                } else if(screenPos.x > 245 && screenPos.x < 395) {
                    currentMenuTab = MenuTab.CATERING_RECREATION;
                } else if(screenPos.x > 490) {
                    currentMenuTab = MenuTab.TEACHING;
                }
                currentMenuItem = -1;
            } else if(screenPos.y < 75 && screenPos.y > 10){
                int newItem = (int)((screenPos.x - 10) / 80);
                if(newItem == currentMenuItem || newItem > 5) {
                    currentMenuItem = -1;
                } else {
                    currentMenuItem = newItem;
                }
            }
            VariantProperties variant = buildingVariants.get(currentMenuTab)[currentMenuItem];
            world.setSelectedBuilding(BuildingFactory.createBuilding(variant, world.currentGridPosition()));
            return true;
        }
        return false;
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
