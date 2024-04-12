package battleship;

/**
 * Represents a Submarine, a specific type of Ship with a fixed length of 1.
 */
public class Submarine extends Ship {

    static final int length = 1;
    static final String type = "submarine";

    /**
     * Constructs a Submarine with a predefined length by invoking the constructor of the superclass, Ship.
     */
    public Submarine() {
        super(Submarine.length);
    }

    /**
     * Returns the type of the ship, specifically identifying it as a "submarine".
     * This method overrides the abstract getShipType method defined in the Ship class.
     * @return The string "submarine"
     */
    @Override
    public String getShipType() {
        return Submarine.type;
    }

}
