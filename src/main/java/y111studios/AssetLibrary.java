package y111studios;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;

/**
 * Handles all project assets and allows for preloading which improves
 * performance
 * by sending assets to graphical memory ahead of their usage.
 */
public class AssetLibrary {
  public AssetManager manager;
  private boolean initialised;

  private AssetLibrary() {
    initialised = false;
  }

  private static class Holder {
    private static final AssetLibrary INSTANCE = new AssetLibrary();
  }

  public static AssetLibrary getInstance() {
    // singleton as only one should exist
    return Holder.INSTANCE;
  }

  /**
   * Create a new {@link AssetManager} and preload all project textures.
   *
   * @throws IllegalStateException If the Asset Library has already been
   *                               initialised
   */
  public void init() {
    if (initialised) {
      throw new IllegalStateException("Asset Library is already initialised");
    }
    manager = new AssetManager(new InternalFileHandleResolver());
    initialised = true;
    preload();
  }

  public void dispose() {
    manager.dispose();
    initialised = false;
  }

  /**
   * Loads all the assets before the game starts to improve performance.
   */
  private void preload() {
    for (AssetPaths path : AssetPaths.values()) {
      manager.load(path.getPath(), Texture.class);
    }
    manager.finishLoading();
  }

}
