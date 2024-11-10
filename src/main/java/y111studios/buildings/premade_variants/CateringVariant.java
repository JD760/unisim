package y111studios.buildings.premade_variants;

import y111studios.AssetPaths;

/**
 * An enum representing the different predefined variants a
 * {@link y111studios.buildings.CateringBuilding CateringBuilding}.
 * 
 * This enum is used in conjunction with the {@link y111studios.buildings.BuildingFactory
 * BuildingFactory} class to create instances of {@link y111studios.buildings.CateringBuilding
 * CateringBuilding}.
 */
public enum CateringVariant implements VariantProperties {
    FAST_FOOD(2, 1, AssetPaths.CATER1),;

    private final int width;
    private final int height;
    private final AssetPaths texturePath;

    CateringVariant(int width, int height, AssetPaths texturePath) {
        this.width = width;
        this.height = height;
        this.texturePath = texturePath;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public AssetPaths getTexturePath() {
        return texturePath;
    }

    @Override
    public Class<? extends VariantProperties> getVariantClass() {
        return CateringVariant.class;
    }

}
