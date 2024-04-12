package battleship;

/**
 * Represents a Battleship, which is a type of ship with a fixed length of 4 in the game of Battleship.
 * This class extends the abstract Ship class, providing specific implementations for a Battleship.
 */
public class Battleship extends Ship {

    // Constant for the length of a Battleship.
    private static final int LENGTH = 4;

    // Constant for the type of ship.
    private static final String TYPE = "battleship";

    /**
     * Constructs a Battleship by passing the predefined length to the superclass constructor.
     */
    public Battleship() {
        super(Battleship.LENGTH);
    }

    /**
     * Returns the type of the ship as a string.
     * @return The type of this ship, which is "battleship".
     */
    @Override
    public String getShipType() {
        return Battleship.TYPE;
    }
}
