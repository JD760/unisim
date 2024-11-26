package y111studios;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

import y111studios.buildings.BuildingFactory;
import y111studios.buildings.premade_variants.VariantProperties;
import y111studios.utils.MenuTab;

public class WorldInputProcessor implements InputProcessor {
    int[] currentMenuItem;
    World world;
    GameState gameState;
    Map<MenuTab, VariantProperties[]> buildingVariants;
    MenuTab[] currentMenuTab;
    Viewport viewport;

    WorldInputProcessor(
        int[] currentMenuItem, World world, GameState gameState, Map<MenuTab,
        VariantProperties[]> buildingVariants, MenuTab[] currentMenuTab, Viewport viewport
    ) {
        this.currentMenuItem = currentMenuItem;
        this.world = world;
        this.gameState = gameState;
        this.buildingVariants = buildingVariants;
        this.currentMenuTab = currentMenuTab;
        this.viewport = viewport;
    }

    public boolean keyDown(int keyCode) {
        if(keyCode == Input.Keys.RIGHT || keyCode == Input.Keys.D) {
            world.getCamera().addVelocity(8, 0);
        } else if(keyCode == Input.Keys.LEFT || keyCode == Input.Keys.A) {
            world.getCamera().addVelocity(-8, 0);
        } else if(keyCode == Input.Keys.DOWN || keyCode == Input.Keys.S) {
            world.getCamera().addVelocity(0, 8);
        } else if(keyCode == Input.Keys.UP || keyCode == Input.Keys.W) {
            world.getCamera().addVelocity(0, -8);
        } else if(keyCode == Input.Keys.X) {
            if(world.getCamera().scale < 5) world.getCamera().scale *= 1.5f;
        } else if(keyCode == Input.Keys.Z) {
            if(world.getCamera().scale > 0.5) world.getCamera().scale /= 1.5f;
        }
        return true;
    }

    public boolean keyUp(int keyCode) {
        if(gameState.isPaused()) {
            return true;
        }
        if(keyCode == Input.Keys.RIGHT || keyCode == Input.Keys.D) {
            world.getCamera().addVelocity(-8, 0);
            world.getCamera().velocityReset();
        } else if(keyCode == Input.Keys.LEFT || keyCode == Input.Keys.A) {
            world.getCamera().addVelocity(8, 0);
            world.getCamera().velocityReset();
        } else if(keyCode == Input.Keys.DOWN || keyCode == Input.Keys.S) {
            world.getCamera().addVelocity(0, -8);
            world.getCamera().velocityReset();
        } else if(keyCode == Input.Keys.UP || keyCode == Input.Keys.W) {
            world.getCamera().addVelocity(0, 8);
            world.getCamera().velocityReset();
        }
        return true;
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
        if (currentMenuItem[0] >= 0 && currentMenuItem[0] < 5) {
            world.addObject(buildingVariants.get(currentMenuTab[0])[currentMenuItem[0]],
                world.pixelToTile(
                    (int)(screenPos.x * world.getCamera().scale),
                    (int)(screenPos.y * world.getCamera().scale)
                )
            );
            currentMenuItem[0] = -1;
            world.setSelectedBuilding(null);
        } else if(currentMenuItem[0] == 5) {
            try{
                world.removeObject(world.pixelToTile((int)(screenPos.x * world.getCamera().scale), (int)(screenPos.y * world.getCamera().scale)));
            } catch(IllegalStateException ignored) {}
        }
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

    public boolean mouseMoved(int x, int y) {
        world.setCursorScreenPos(viewport.getCamera().unproject(
            new Vector3(x, y, 0),
            viewport.getScreenX(), viewport.getScreenY(),
            viewport.getScreenWidth(), viewport.getScreenHeight()
        ));
        if (currentMenuItem[0] >= 0 && currentMenuItem[0] < 5) {
            VariantProperties variant = buildingVariants.get(currentMenuTab[0])[currentMenuItem[0]];
            world.setSelectedBuilding(BuildingFactory.createBuilding(variant, world.currentGridPosition()));
        }
        return true;
    }

    public boolean scrolled (float amountX, float amountY) {
        return false;
    }
}
