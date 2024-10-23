package y111studios;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// Loading assests at game start
public class Main extends Game {

    public AssetLibrary assetLib;
    public SpriteBatch spritebatch;
    public BitmapFont font;

    // Templates
    // Texture backgroundTexture;
    // Texture bucketTexture;
    // Texture dropTexture;
    // Sound dropSound;
    // Music music;
    // Templates
    @Override
    public void create() {
        assetLib = AssetLibrary.getInstance();
        assetLib.init();
        spritebatch = new SpriteBatch();
        font = new BitmapFont();
        Gdx.graphics.setWindowedMode(640, 480);
        while (!assetLib.manager.update()) {
            continue;
        }
        this.setScreen(new StartScreen(this));

        // Templates
        // backgroundTexture = new Texture("background.png");
        // bucketTexture = new Texture("bucket.png");
        // dropTexture = new Texture("drop.png");
        // Templates
    }

    @Override
    public void dispose() {
        spritebatch.dispose();
        font.dispose();
        assetLib.manager.dispose();
    }
    /*@Override
    public void pause() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pause'");
    }*/
    @Override
    public void render() {
        super.render();
    }
    /*@Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resize'");
    }
    @Override
    public void resume() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resume'");
    }*/
}

