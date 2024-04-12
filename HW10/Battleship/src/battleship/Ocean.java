package battleship;

/*
 * @author Zhanqian Wu & Yipeng Zhang
 * @date: 04/11/2024
 */


import java.util.Random;

/**
 * Manages a 10x10 grid of Ship objects, representing the "ocean" in a game of Battleship.
 * This class contains methods to manipulate the ocean, including placing ships randomly and tracking game statistics.
 */
public class Ocean {

    /**
     * Represents the playing field, holding references to Ship objects.
     */
    private Ship[][] ships = new Ship[10][10];
    
    /**
     * Tracks the total number of shots fired by the user.
     */
    private int shotsFired;
    
    /**
     * Counts the number of times a shot hit a ship.
     */
    private int hitCount;
    
    /**
     * Records the number of ships sunk, out of a total of 10.
     */
    private int shipsSunk;
    
    /**
     * Tracks which cells have been shot at, to prevent multiple shots on the same location.
     */
    private boolean[][] shotAt = new boolean[10][10];

    /**
     * Constructs an Ocean by filling the grid with EmptySea instances and initializing game statistics.
     */
    public Ocean() {
        // Initialize the grid with EmptySea instances
        for (int m = 0; m < this.ships.length; m++) {
            for (int n = 0; n < this.ships[m].length; n++) {
                ships[m][n] = new EmptySea();
                ships[m][n].placeShipAt(m, n, true, this);
            }
        }
        // Initialize the game statistics
        this.hitCount = 0;
        this.shipsSunk = 0;
        this.shotsFired = 0;
    }

    /**
     * Places all ships randomly on the grid. Ensures that larger ships are placed before smaller ones
     * to avoid running out of space on the grid.
     */
    void placeAllShipsRandomly() {
		// Place all ten ships randomly on the (initially empty) ocean
		// Place larger ships before the smaller ones, or you may end up with no legal place to put a large ship.

		// iterate through the one battleship
		for (int i = 0; i < 1; i++) {
			// initialize the ship
			Battleship battleship = new Battleship();

			boolean satisfied = false;

			Random rand = new Random();
			int row = 0;
			int column = 0;
			boolean horizontal = true;

			while (satisfied == false) {

				// generate the row, column, horizontal variables
				row = rand.nextInt(10);
				column = rand.nextInt(10);
				horizontal = rand.nextBoolean();

				// check whether generated location is okay To Place Ship
				// if it is okay to place ship, get out the while loop
				// if not, generates new variables
				satisfied = battleship.okToPlaceShipAt(row, column, horizontal, this);

			}
			battleship.placeShipAt(row, column, horizontal, this);
		}

		// iterate through the two cruisers
		for (int i = 0; i < 2; i++) {
			// initialize the ship
			Cruiser cruiser = new Cruiser();

			boolean satisfied = false;

			Random rand = new Random();
			int row = 0;
			int column = 0;
			boolean horizontal = true;

			while (satisfied == false) {

				// generate the row, column, horizontal variables
				row = rand.nextInt(10);
				column = rand.nextInt(10);
				horizontal = rand.nextBoolean();

				// check whether generated location is okay To Place Ship
				// if it is okay to place ship, breaks out of the while loop
				// if not, stays in the loop and generates new variables
				satisfied = cruiser.okToPlaceShipAt(row, column, horizontal, this);

			}
			cruiser.placeShipAt(row, column, horizontal, this);
		}

		// iterate through the three destroyers
		for (int i = 0; i < 3; i++) {
			// initialize the ship
			Destroyer destroyer = new Destroyer();

			boolean satisfiedornot = false;

			Random rand = new Random();
			int row = 0;
			int column = 0;
			boolean horizontal = true;

			while (satisfiedornot == false) {

				// generate the row, column, horizontal variables
				row = rand.nextInt(10);
				column = rand.nextInt(10);
				horizontal = rand.nextBoolean();

				// check whether generated location is okay To Place Ship
				// if it is okay to place ship, get out of the while loop
				// if not, generates new variables
				satisfiedornot = destroyer.okToPlaceShipAt(row, column, horizontal, this);

			}
			destroyer.placeShipAt(row, column, horizontal, this);
		}

		// iterate through the four submarines
		for (int j = 0; j < 4; j++) {
			// initialize the ship
			Submarine submarine = new Submarine();

			boolean satisfiedornot = false;

			Random rand = new Random();
			int row = 0;
			int column = 0;
			boolean horizontal = true;

			while (satisfiedornot == false) {

				// generate the row, column, horizontal variables
				row = rand.nextInt(10);
				column = rand.nextInt(10);
				horizontal = rand.nextBoolean();

				// check whether generated location is okay To Place Ship
				// if it is okay to place ship, get out of the while loop
				// if not,  generates new variables
				satisfiedornot = submarine.okToPlaceShipAt(row, column, horizontal, this);

			}
			submarine.placeShipAt(row, column, horizontal, this);
		}
	}

    /**
     * Places a specific type of ship a certain number of times on the grid.
     *
     * @param ship    The ship to place.
     * @param count   How many of these ships to place.
     */
    private void placeShip(Ship ship, int count) {
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            boolean placed = false;
            while (!placed) {
                int row = rand.nextInt(10);
                int column = rand.nextInt(10);
                boolean horizontal = rand.nextBoolean();
                placed = ship.okToPlaceShipAt(row, column, horizontal, this);
                if (placed) {
                    ship.placeShipAt(row, column, horizontal, this);
                }
            }
        }
    }

    /**
     * Returns true if the specified location contains a ship that is not an EmptySea.
     *
     * @param row    Row index of the location to check.
     * @param column Column index of the location to check.
     * @return true if the location contains a ship, false otherwise.
     */
    boolean isOccupied(int row, int column) {
        return !(ships[row][column] instanceof EmptySea);
    }

    /**
     * Shoots at the specified location, updating game statistics and the state of the ship at that location.
     *
     * @param row    Row index where the shot is fired.
     * @param column Column index where the shot is fired.
     * @return true if a ship is hit, false otherwise.
     */
    boolean shootAt(int row, int column) {
        shotsFired++;
        shotAt[row][column] = true;
        if (ships[row][column].shootAt(row, column)) {
            hitCount++;
            if (ships[row][column].isSunk()) {
                shipsSunk++;
            }
            return true;
        }
        return false;
    }

    /**
     * @return The total number of shots fired in the game.
     */
    int getShotsFired() {
        return shotsFired;
    }

    /**
     * @return The number of times ships have been hit.
     */
    int getHitCount() {
        return hitCount;
    }

    /**
     * @return The number of ships sunk in the game.
     */
    int getShipsSunk() {
        return shipsSunk;
    }

    /**
     * @return true if all ships have been sunk, thus ending the game.
     */
    boolean isGameOver() {
        return shipsSunk == 10;
    }

    /**
     * @return The 10x10 grid of ships.
     */
    public Ship[][] getShipArray() {
        return ships;
    }

    /**
     * Prints the current state of the ocean, marking hits and misses on the grid.
     */
    void print() {
        System.out.println("  0 1 2 3 4 5 6 7 8 9");
        for (int i = 0; i < 10; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 10; j++) {
                if (shotAt[i][j]) {
                    System.out.print(ships[i][j].toString() + " ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }
}

