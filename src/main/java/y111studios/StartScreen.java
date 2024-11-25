package y111studios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Texture;

public class StartScreen extends ScreenAdapter {

    final Main game;

    Texture startScreen;

    OrthographicCamera camera;

    /**
     * Sets up the camera and loads the background
     * 
     * @param game reference to game manager
     */
    public StartScreen(final Main game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, MapScreen.WIDTH, MapScreen.HEIGHT);
        startScreen = game.assetLib.manager.get(AssetPaths.START_SCREEN.getPath());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) {
                    game.setScreen(new MapScreen(game));
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 0);

        camera.update();
        game.spritebatch.setProjectionMatrix(camera.combined);

        game.spritebatch.begin();
        game.spritebatch.draw(startScreen, 0, 0);
        game.spritebatch.end();
    }

    /*@Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resize'");
    }*/

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        game.dispose();
    }

}
