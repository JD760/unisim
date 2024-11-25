package y111studios.buildings;

import y111studios.buildings.premade_variants.TeachingVariant;
import y111studios.position.GridPosition;

/**
 * A class representing a teaching building within the game. This class extends the
 * {@link Building} class.
 * 
 * @see Building
 */
public class TeachingBuilding extends Building {

    /**
     * Creates a new teaching building at the given position of the given variant.
     * 
     * @param position The position of the building
     * @param variant The variant type of the building
     */
    public TeachingBuilding(GridPosition position, TeachingVariant variant) {
        super(position, variant);
    }

}
