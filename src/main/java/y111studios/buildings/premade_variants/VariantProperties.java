package y111studios.buildings.premade_variants;

import y111studios.AssetPaths;

/**
 * An interface representing the properties that all variants of a
 * {@link y111studios.buildings.Building Building} must have. This interface is
 * used as a type bound
 * for generic variants to construct a building.
 */
public interface VariantProperties {
  int getWidth();

  int getHeight();

  AssetPaths getTexturePath();

  Class<? extends VariantProperties> getVariantClass();
}
