package y111studios.buildings;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import y111studios.buildings.premade_variants.AccommodationVariant;
import y111studios.buildings.premade_variants.VariantProperties;
import y111studios.buildings.premade_variants.CateringVariant;
import y111studios.buildings.premade_variants.RecreationVariant;
import y111studios.buildings.premade_variants.TeachingVariant;
import y111studios.position.GridPosition;
import y111studios.utils.UnreachableException;

/**
 * A factory class for creating new {@link Building} instances.
 *
 * <p>The factory class is used to create new instances of the building classes. The factory class
 * ensures that the constructors of the building classes are correctly defined at startup and uses a
 * function to create a new instance of the building class with any variant at a specified position.
 *
 * @see Building
 * @see VariantProperties
 */
public final class BuildingFactory {

  // Prevent instantiation of the factory class.

  private BuildingFactory() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("BuildingFactory should not be instantiated");
  }

  /**
   * A map of the constructors for each building class. The key is the class of the building and the
   * value is the constructor of the building.
   *
   * @see #tryRegisterConstructor(Class, Class)
   */
  private static final Map<Class<? extends VariantProperties>, Constructor<? extends Building>>
      CONSTRUCTORS;

  // At startup, try to register the constructors of the building classes using each variant. This
  // is done to ensure that the constructors are correctly defined at the start rather than during
  // runtime.

  static {
    CONSTRUCTORS = new HashMap<>(4);
    try {
      tryRegisterConstructor(AccommodationVariant.class, AccommodationBuilding.class);
      tryRegisterConstructor(CateringVariant.class, CateringBuilding.class);
      tryRegisterConstructor(RecreationVariant.class, RecreationBuilding.class);
      tryRegisterConstructor(TeachingVariant.class, TeachingBuilding.class);
    } catch (UnreachableException e) {
      // This should not happen, as the constructors should be defined in the classes
      e.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * Try to register the constructor of the building class attached to the variant. This is done to
   * ensure that each building class has a constructor that takes a {@link GridPosition} and variant
   * class as arguments. If the constructor is not defined, an exception is thrown.
   *
   * @param <V> the type of the variant
   * @param variantClass the class of the variant to register the constructor for
   * @param buildingClass the subclass of {@link Building} to register the constructor for
   * @throws UnreachableException if the constructor is not defined in the class
   */
  private static <V extends VariantProperties> void tryRegisterConstructor(
      Class<V> variantClass, Class<? extends Building> buildingClass) {
    Constructor<? extends Building> constructor;
    try {
      // Get the constructor of the building class
      constructor = buildingClass.getConstructor(GridPosition.class, variantClass);
    } catch (NoSuchMethodException e) {
      // This should not happen, as the constructor should be defined in the class
      throw new UnreachableException("Constructor undefined", e);
    } catch (SecurityException e) {
      // Issue that can arise from constructor access permissions. i.e. private / protected
      // constructors
      throw new UnreachableException("Constructor cannot be accessed", e);
    }
    // Register the constructor to the map
    CONSTRUCTORS.put(variantClass, constructor);
  }

  /**
   * Create a new building instance of the specified variant at the specified position.
   *
   * @param variant the variant of the building
   * @param position the position of the building
   * @return a new instance of the building
   * @throws IllegalArgumentException if the variant or position is null
   */
  public static Building createBuilding(VariantProperties variant, GridPosition position) {
    if (variant == null) {
      throw new IllegalArgumentException("BuildingProperties must not be null");
    }
    if (position == null) {
      throw new IllegalArgumentException("GridPosition must not be null");
    }
    // Get the appropriate constructor for the building class
    Constructor<? extends Building> constructor = CONSTRUCTORS.get(variant.getVariantClass());
    try {
      // Return the new instance of the Building
      return constructor.newInstance(position, variant);
    } catch (Exception e) {
      // This should not happen, as the constructor should be defined in the class
      e.printStackTrace();
      throw new UnreachableException("Constructors defined at startup should be correct");
    }
  }
}
