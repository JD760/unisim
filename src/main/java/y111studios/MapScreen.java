package y111studios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.utils.viewport.FitViewport;
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

/**
 * A class to interact with LibGDX to render the game window.
 */
public class MapScreen extends ScreenAdapter {
    // Proportional width of the display.
    static final int WIDTH = 640;
    // Proportional height of the display.
    static final int HEIGHT = 480;
    // Width of map in tiles.
    public static final int TILE_WIDTH = 75;
    // Height of map in tiles.
    public static final int TILE_HEIGHT = 75;

    final Main game;
    GameState gameState;
    Texture[] gameMap = new Texture[4];
    Texture menu;
    Texture accommodationMenu;
    Texture cateringMenu;
    Texture teachingMenu;
    MenuTab currentMenuTab;
    Integer currentMenuItem;
    Texture[] buildingTextures;
    Map<MenuTab, VariantProperties[]> buildingVariants;
    FitViewport viewport;
    Texture pauseMenu;
    VariantProperties currentVariant;
    Boolean showDebugInfo = false;
    World world;
    InputMultiplexer inputMultiplexer;

    /**
     * Sets up the camera and loads the background
     * 
     * @param game Reference to game manager
     */
    public MapScreen(final Main game) {
        this.game = game;
        this.gameState = new GameState(TILE_WIDTH, TILE_HEIGHT);
        viewport = new FitViewport(WIDTH, HEIGHT);
        viewport.getCamera().position.set(WIDTH / 2f, HEIGHT / 2f, 0);
        viewport.getCamera().update();
        gameMap[0] = game.getAsset(AssetPaths.MAP_BACKGROUND_TOP_LEFT);
        gameMap[1] = game.getAsset(AssetPaths.MAP_BACKGROUND_TOP_RIGHT);
        gameMap[2] = game.getAsset(AssetPaths.MAP_BACKGROUND_BOTTOM_LEFT);
        gameMap[3] = game.getAsset(AssetPaths.MAP_BACKGROUND_BOTTOM_RIGHT);
        menu = game.getAsset(AssetPaths.MENU);
        accommodationMenu = game.getAsset(AssetPaths.ACCOMMODATION_MENU);
        cateringMenu = game.getAsset(AssetPaths.CATERING_MENU);
        teachingMenu = game.getAsset(AssetPaths.TEACHING_MENU);
        pauseMenu = game.getAsset(AssetPaths.PAUSE);
        currentMenuTab = MenuTab.ACCOMMODATION;
        currentMenuItem = -1;
        buildingTextures = new Texture[] {game.getAsset(AssetPaths.ACC1), game.getAsset(AssetPaths.ACC2), game.getAsset(AssetPaths.ACC3),
                                          game.getAsset(AssetPaths.ACC4), game.getAsset(AssetPaths.ACC5), game.getAsset(AssetPaths.TRASH), game.getAsset(AssetPaths.CATER1),
                                          game.getAsset(AssetPaths.CATER2), game.getAsset(AssetPaths.CATER3), game.getAsset(AssetPaths.REC1),
                                          game.getAsset(AssetPaths.REC2), game.getAsset(AssetPaths.TRASH), game.getAsset(AssetPaths.TEACH1), game.getAsset(AssetPaths.TEACH2),
                                          game.getAsset(AssetPaths.TEACH3), game.getAsset(AssetPaths.TEACH4), game.getAsset(AssetPaths.TEACH5), game.getAsset(AssetPaths.TRASH)};
        buildingVariants = new HashMap<>();
        buildingVariants.put(MenuTab.ACCOMMODATION, AccommodationVariant.values());
        buildingVariants.put(MenuTab.TEACHING, TeachingVariant.values());

        VariantProperties[] jointTabVariants = new VariantProperties[CateringVariant.values().length + RecreationVariant.values().length];
        System.arraycopy(CateringVariant.values(), 0, jointTabVariants, 0, CateringVariant.values().length);
        System.arraycopy(RecreationVariant.values(), 0, jointTabVariants, CateringVariant.values().length, RecreationVariant.values().length);

        buildingVariants.put(MenuTab.CATERING_RECREATION, jointTabVariants);

        world = new World(game, gameState);

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new UIInputProcessor(viewport, currentMenuTab, currentMenuItem, world, gameState, buildingVariants, showDebugInfo));
        inputMultiplexer.addProcessor(new WorldInputProcessor(currentMenuItem, world, gameState, buildingVariants, currentMenuTab, viewport));
    }

    /**
     * Handles input for panning and zooming the camera.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    /**
     * Renders the game each tick.
     * 
     * @param delta The time since the previous tick.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);

        viewport.apply();

        world.render(delta);

        game.spritebatch.begin();

        // Draw the menu
        game.spritebatch.draw(menu, (currentMenuTab.toInt() - 2) * 243, 0, 1126, 100, 0, 0, menu.getWidth(), menu.getHeight(), false, false);
        game.spritebatch.draw(accommodationMenu, 5, 85);
        game.spritebatch.draw(cateringMenu, 248, 85);
        game.spritebatch.draw(teachingMenu, 491, 85);

        // Draw the appropriate items in the menu
        for(int i = 0; i < 6; i++) {
            if(i == currentMenuItem || gameState.isPaused()) {
                game.spritebatch.setColor(1, 1, 1, 0.5f);
            } else {
                game.spritebatch.setColor(1, 1, 1, 1);
            }
            int j = i;
            if(currentMenuTab.toInt() > 0) {
                j += currentMenuTab.toInt() * 6;
            }
            game.spritebatch.draw(buildingTextures[j], 10 + i * 80, 15, 50, (int)((float)buildingTextures[j].getHeight() / buildingTextures[j].getWidth() * 50), 0, 0, buildingTextures[j].getWidth(), buildingTextures[j].getHeight(), false, false);
        }

        // Render the time remaining at the top of the screen
        Duration timeRemaining = gameState.timeRemaining();
        String timeString = String.format("%02d:%02d", timeRemaining.toMinutesPart(), timeRemaining.toSecondsPart());

        GlyphLayout layout = new GlyphLayout(game.font, timeString);
        float textWidth = layout.width;
        float textX = (viewport.getWorldWidth() - textWidth) / 2;
        float textY = viewport.getWorldHeight() - 20;
        game.font.draw(game.spritebatch, timeString, textX, textY);

        // Render the total count of buildings placed
        if (showDebugInfo) {
            int buildingCount = gameState.getCount();
            String buildingString = String.format("Count: %d / %d", buildingCount, BuildingManager.MAX_BUILDINGS);

            float buildingX = 15;
            float buildingY = 120;
            game.font.draw(game.spritebatch, buildingString, buildingX, buildingY);

            // Render individual building counts
            Map<BuildingType, Integer> buildingCounts = gameState.buildingManager.getCounter().getBuildingMap();
            for (BuildingType type : BuildingType.values()) {
                int count = buildingCounts.get(type);
                String countString = String.format("%c: %d", type.toString().toCharArray()[0], count);
                buildingY += 20;
                game.font.draw(game.spritebatch, countString, buildingX, buildingY);
            }

            game.font.draw(game.spritebatch, String.valueOf(Gdx.graphics.getFramesPerSecond()), 15, (buildingY + 20));
        }

        // Draw the pause menu if paused
        if(gameState.isPaused()) {
            game.spritebatch.setColor(1, 1, 1, 1);
            if (gameState.isTimeUp()) {
                // Covers case where the game is locked paused due to time running out
                game.spritebatch.draw(game.getAsset(AssetPaths.GAME_OVER), 220, 204);
            } else {
                game.spritebatch.draw(pauseMenu, 220, 204);
            }
        }

        game.spritebatch.end();

        // Check for game over
        if (gameState.isTimeUp()) {
            gameState.pause(); // Lock pause
        }
    }

    /**
     * Handles resizing of the game window.
     * 
     * @param width The new width of the window.
     * @param height The new height of the window.
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    /**
     * Handles hiding of the game window.
     */
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    /**
     * Handles closing of the game window.
     */
    @Override
    public void dispose() {
        game.dispose();
    }
}
