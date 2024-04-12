package battleship;

/**
 * Represents a Cruiser, which is a type of ship in the game of Battleship with a fixed length of 3.
 * This class extends the abstract Ship class, providing specific implementations for a Cruiser.
 * 
 * @author Zhanqian Wu & Yipeng Zhang
 * @date: 04/11/2024
 */
public class Cruiser extends Ship {

    // Constant for the length of a Cruiser.
    private static final int LENGTH = 3;

    // Constant for the type of ship.
    private static final String TYPE = "cruiser";

    /**
     * Constructs a Cruiser by passing the predefined length to the superclass constructor.
     */
    public Cruiser() {
        super(Cruiser.LENGTH);
    }

    /**
     * Returns the type of the ship as a string.
     * This method overrides the getShipType() method in the abstract Ship class.
     * 
     * @return The type of this ship, which is "cruiser".
     */
    @Override
    public String getShipType() {
        return Cruiser.TYPE;
    }
}
