package y111studios.buildings;

import y111studios.buildings.premade_variants.CateringVariant;
import y111studios.position.GridPosition;

/**
 * A class representing a catering building within the game. This class extends the
 * {@link Building} class.
 * 
 * @see Building
 */
public class CateringBuilding extends Building {

    /**
     * Creates a new catering building at the given position of the given variant.
     * 
     * @param position The position of the building
     * @param variant The variant type of the building
     */
    public CateringBuilding(GridPosition position, CateringVariant variant) {
        super(position, variant);
    }

}
