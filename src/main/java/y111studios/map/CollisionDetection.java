package y111studios.map;

import y111studios.position.GridArea;
import y111studios.position.GridPosition;

/**
 * A class for detecting collisions between buildings on the map.
 * 
 * <p>
 * The collision detection class is used to determine if a building can be placed on the map given a
 * position and area. The class also provides methods for updating the internal data of this class
 * to reflect the new building placements.
 * </p>
 */
public class CollisionDetection {
    /**
     * Boolean array representing the grid of the map. This is a false initialized grid where true
     * values represent occupied cells, and false values represent empty cells.
     */
    private boolean[][] buildingGrid;
    /**
     * The total area of the map. This is used to determine the bounds of the map.
     */
    private GridArea mapArea;

    /**
     * Constructs a new collision detection object with the specified width and height.
     * 
     * @param width the width of the map
     * @param height the height of the map
     */
    public CollisionDetection(int width, int height) {
        buildingGrid = new boolean[width][height];
        mapArea = new GridArea(0, 0, width, height);
    }

    public CollisionDetection(int width, int height, int[][] staticObjects) {
        this(width, height);
        for(int i = 0; i < staticObjects.length; i++) {
            fillArea(new GridArea(staticObjects[i][0], staticObjects[i][1], staticObjects[i][2], staticObjects[i][3]), true);
        }
    }

    /**
     * Checks if a given building's values are valid
     * 
     * @param area the area to check
     * @return true if the building will have valid bounds if placed on the map, false otherwise
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean withinBounds(GridArea area) {
        return mapArea.contains(area);
    }

    /**
     * Checks if a given position is within the bounds of the map.
     * 
     * @param position the position to check
     * @return true if the position is within the bounds of the map, false otherwise
     */
    private boolean withinBounds(GridPosition position) {
        return mapArea.contains(position);
    }

    /**
     * Checks if a building can be placed on the terrain in the given area.
     *
     * @param area the area to check
     */
    public boolean canPlaceBuilding(GridArea area) {
        if (!withinBounds(area)) {
            return false;
        }
        for (int x = area.getX(); x < area.getX() + area.getWidth(); x++) {
            for (int y = area.getY(); y < area.getY() + area.getHeight(); y++) {
                if (buildingGrid[x][y]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the given position is a valid place to build. This method checks only a single
     * cell.
     * 
     * @param position the position to check
     * @return true if the position is a valid place to build, false otherwise
     */
    public boolean canPlaceBuilding(GridPosition position) {
        if (!withinBounds(position)) {
            return false;
        }
        return buildingGrid[position.getX()][position.getY()];
    }

    /**
     * Attempts to place a building on the grid in the given area. If the building can be placed, it
     * marks the grid cells as occupied.
     *
     * @param area the area to place the building
     */
    public boolean placeBuilding(GridArea area) {
        if (!canPlaceBuilding(area)) {
            return false;
        }
        fillArea(area, true);
        return true;
    }

    /**
     * Removes a building from the grid in the given area.
     *
     * @param area the area to remove the building from
     */
    public boolean removeBuilding(GridArea area) {
        if (!withinBounds(area)) {
            return false;
        }
        fillArea(area, false);
        return true;
    }

    /**
     * Fills the given area of the grid with the specified value.
     * 
     * @param area the area to fill
     * @param value the value to fill the area with
     */
    private void fillArea(GridArea area, boolean value) {
        for (int x = area.getX(); x < area.getX() + area.getWidth(); x++) {
            for (int y = area.getY(); y < area.getY() + area.getHeight(); y++) {
                buildingGrid[x][y] = value;
            }
        }
    }
}
