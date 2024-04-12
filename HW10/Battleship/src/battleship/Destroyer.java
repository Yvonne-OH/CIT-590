package battleship;

/**
 * Represents a Destroyer, a type of ship in the game of Battleship with a fixed length of 2.
 * This class extends the abstract Ship class, providing specific implementations for a Destroyer.
 * Destroyers are smaller ships, making them harder to hit but easier to sink due to their size.
 */
public class Destroyer extends Ship {

    // Constant for the length of a Destroyer.
    private static final int LENGTH = 2;

    // Constant for the type of ship.
    private static final String TYPE = "destroyer";

    /**
     * Constructs a Destroyer by passing the predefined length to the superclass constructor.
     * Initializes the ship with its specific length.
     */
    public Destroyer() {
        super(Destroyer.LENGTH);
    }

    /**
     * Returns the type of the ship as a string.
     * This method overrides the getShipType() method in the abstract Ship class.
     * 
     * @return The type of this ship, which is "destroyer".
     */
    @Override
    public String getShipType() {
        return Destroyer.TYPE;
    }
}
