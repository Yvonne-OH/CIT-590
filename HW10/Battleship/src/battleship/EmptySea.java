package battleship;

/**
 * Represents a segment of the ocean that does not contain any ships. This class extends the abstract
 * Ship class to allow uniform handling of all grid elements in the Battleship game. It functions
 * primarily as a placeholder to manage interactions with empty sections of the game grid.
 */
public class EmptySea extends Ship {

    // Constants for the length and type of an EmptySea object.
    static final int length = 1;
    static final String type = "empty";

    /**
     * Constructs an EmptySea object with a length of one. This setup utilizes the constructor
     * from the Ship superclass, simplifying interaction with the game grid by treating empty
     * areas as ship objects of length one.
     */
    public EmptySea() {
        super(EmptySea.length);
    }

    /**
     * Retrieves the type of ship, which in this case, indicates an empty segment of the ocean.
     * 
     * @return A string "empty", signifying that this segment of the ocean does not contain a ship.
     */
    @Override
    public String getShipType() {
        return EmptySea.type;
    }

    /**
     * Simulates the effect of shooting at an empty segment of the ocean, which will always result
     * in a miss. This method overrides the corresponding method in the Ship class.
     * 
     * @param row The row coordinate of the shot.
     * @param column The column coordinate of the shot.
     * @return false always, as there is no ship to hit in this section.
     */
    @Override
    boolean shootAt(int row, int column) {
        return false;
    }

    /**
     * Confirms that this section of the ocean cannot be sunk. Overrides the isSunk() method in
     * the Ship superclass to always return false.
     * 
     * @return false always, as there are no conditions under which an empty sea segment can be sunk.
     */
    @Override
    boolean isSunk() {
        return false;
    }

    /**
     * Provides a visual representation of this object when a shot hits this part of the ocean.
     * Returns a single character "-", indicating a missed shot.
     * 
     * @return A string "-", representing a shot that hit the ocean but not a ship.
     */
    @Override
    public String toString() {
        return "-";
    }
}
