package y111studios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.viewport.FitViewport;

import lombok.Getter;
import lombok.Setter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector3;
import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import y111studios.position.GridPosition;
import y111studios.utils.MenuTab;
import y111studios.utils.UnreachableException;
import y111studios.buildings.Building;
import y111studios.buildings.BuildingFactory;
import y111studios.buildings.BuildingManager;
import y111studios.buildings.BuildingType;
import y111studios.buildings.premade_variants.*;

public class World {
    // Proportional width of the display.
    static final int WIDTH = 640;
    // Proportional height of the display.
    static final int HEIGHT = 480;
    // Width of map in tiles.
    public static final int TILE_WIDTH = 75;
    // Height of map in tiles.
    public static final int TILE_HEIGHT = 75;

    private final static Color TRANSPARENT_PREVIEW = new Color(1, 1, 1, 0.475f);
    private final static Color INVALID_PREVIEW = new Color(1, 0.5f, 0.5f, 0.475f);
    private final static Color PAUSED_DULLING = new Color(0.5f, 0.5f, 0.5f, 1f);
    private final static Color NORMAL = new Color(1, 1, 1, 1);

    final Main game;
    GameState gameState;
    Texture[] gameMap = new Texture[4];
    @Setter Vector3 cursorScreenPos;
    @Getter Camera camera;
    @Setter Building possibleInstance;
    List<Building> renderOrdering;

    /**
     * Adds an object to the game.
     * 
     * @param variant The object to add.
     * @return Whether the object was added.
     */
    public boolean addObject(VariantProperties variant, GridPosition coords) {
        Building building = BuildingFactory.createBuilding(variant, coords);
        if (!gameState.push(building)) {
            return false;
        }
        int index;
        for (index = 0; index < renderOrdering.size(); index++) {
            Building current = renderOrdering.get(index);
            if (current.getArea().getY() > building.getArea().getY()) {
                break;
            }
            if (current.getArea().getX() < building.getArea().getX()) {
                break;
            }
        }
        renderOrdering.add(index, building);
        return true;
    }

    /**
     * Removes an object from the game.
     * 
     * @param coords The tile coordinates of the object to remove.
     * @return Whether an object was removed.
     */
    public boolean removeObject(GridPosition coords) {
        if (!gameState.removePosition(coords)) {
            return false;
        }
        for (int i = 0; i < renderOrdering.size(); i++) {
            Building current = renderOrdering.get(i);
            if (current.contains(coords)) {
                renderOrdering.remove(i);
                return true;
            }
        }
        throw new UnreachableException("State de-synced with renderOrdering");
    }

    /**
     * Sets up the camera and loads the background
     * 
     * @param game Reference to game manager
     */
    public World(final Main game) {
        this.game = game;
        this.gameState = new GameState(TILE_WIDTH, TILE_HEIGHT);
        renderOrdering = new LinkedList<>();
        gameMap[0] = game.getAsset(AssetPaths.MAP_BACKGROUND_TOP_LEFT);
        gameMap[1] = game.getAsset(AssetPaths.MAP_BACKGROUND_TOP_RIGHT);
        gameMap[2] = game.getAsset(AssetPaths.MAP_BACKGROUND_BOTTOM_LEFT);
        gameMap[3] = game.getAsset(AssetPaths.MAP_BACKGROUND_BOTTOM_RIGHT);
        camera = new Camera(2000, 1000, WIDTH, HEIGHT);
    }

    /**
     * Converts building tile coordinates to pixel coordinates. Must account for camera.scale and building depth separately.
     * 
     * @param coords The tile coordinates to convert.
     * @return The pixel coordinates.
     */
    public int[] tileToPixel(GridPosition coords) {
        int pixelX = 129 + (coords.getX() + coords.getY()) * 32 - camera.x;
        int pixelY = -1343 + (coords.getX() - coords.getY()) * 16 + camera.y + (int)(HEIGHT * camera.scale);
        return new int[] {pixelX, pixelY};
    }

    /**
     * Converts pixel coordinates to tile coordinates. Must account for camera.scale separately.
     * 
     * @param x The x pixel coordinate to convert.
     * @param y The y pixel coordinate to convert.
     * @return A {@link GridPosition} containing the tile coordinates.
     */
    public GridPosition pixelToTile(int x, int y) {
        int sum = (x + camera.x - 129) / 32;
        int diff = (y - camera.y - (int)(HEIGHT * camera.scale) + 1343) / 16;
        int tileY = (sum - diff) / 2;
        int tileX = sum - tileY;
        try {
            return new GridPosition(tileX, tileY);
        } catch(IllegalArgumentException e) {
            return new GridPosition(10000, 10000);
        }
    }

    /**
     * Returns the current grid position of the cursor.
     * 
     * @return The current grid position of the cursor.
     */
    public GridPosition currentGridPosition() {
        return pixelToTile((int)(cursorScreenPos.x * camera.scale), (int)(cursorScreenPos.y * camera.scale));
    }

    public void renderBuilding(Building building) {
        // TODO:
        // Add back highlight below
        // if (menuItem == 5 && building.getArea().contains(currentGridPosition())) {
        //     game.spritebatch.setColor(INVALID_PREVIEW);
        // }
        Texture texture = game.getAsset(building.getTexturePath());
        int[] pixelCoords = tileToPixel(building.getArea().getOrigin());
        game.spritebatch.draw(texture,
            (int)(pixelCoords[0] / camera.scale),
            (int)((pixelCoords[1] - building.getArea().getHeight() * 16) / camera.scale),
            (int)(2 * texture.getWidth() / camera.scale),
            (int)(2 * texture.getHeight() / camera.scale),
            0, 0, texture.getWidth(), texture.getHeight(),
            false, false
        );
        if (gameState.isPaused()) {
            game.spritebatch.setColor(PAUSED_DULLING);
        } else {
            game.spritebatch.setColor(NORMAL);
        }
    }

    /**
     * Renders the game each tick.
     *
     * @param delta The time since the previous tick.
     */
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);

        camera.shift();

        game.spritebatch.begin();
        // Change colour based to dull the screen if paused
        if(gameState.isPaused()) {
            game.spritebatch.setColor(PAUSED_DULLING);
        } else {
            game.spritebatch.setColor(NORMAL);
        }
        // Draw the game map
        game.spritebatch.draw(gameMap[0], 0, 0, WIDTH, HEIGHT, camera.x + 1, camera.y + 1,
            (int)(WIDTH * camera.scale), (int)(HEIGHT * camera.scale), false, false);
        game.spritebatch.draw(gameMap[1], 0, 0, WIDTH, HEIGHT, camera.x - gameMap[0].getWidth() + 3, camera.y + 1,
            (int)(WIDTH * camera.scale), (int)(HEIGHT * camera.scale), false, false);
        game.spritebatch.draw(gameMap[2], 0, 0, WIDTH, HEIGHT, camera.x + 1, camera.y - gameMap[0].getHeight() + 3,
            (int)(WIDTH * camera.scale), (int)(HEIGHT * camera.scale), false, false);
        game.spritebatch.draw(gameMap[3], 0, 0, WIDTH, HEIGHT, camera.x - gameMap[0].getWidth() + 3, camera.y - gameMap[0].getHeight() + 3,
            (int)(WIDTH * camera.scale), (int)(HEIGHT * camera.scale), false, false);

        // Add building placement hologram
        if (!gameState.isPaused() && possibleInstance != null) {
            // Set hologram colour
            if (!gameState.canPlaceBuilding(possibleInstance)) {
                game.spritebatch.setColor(INVALID_PREVIEW);
            } else {
                game.spritebatch.setColor(TRANSPARENT_PREVIEW);
            }

            renderBuilding(possibleInstance); // Render hologram

            // Reset colour to previous one
            if(gameState.isPaused()) {
                game.spritebatch.setColor(PAUSED_DULLING);
            } else {
                game.spritebatch.setColor(NORMAL);
            }
        }

        // Render buildings
        renderOrdering.forEach(this::renderBuilding);

        game.spritebatch.end();

        // Check for game over
        if (gameState.isTimeUp()) {
            camera.velocityReset(); // Lock camera
        }
    }
}
