package battleship;

/*
 * @author Zhanqian Wu & Yipeng Zhang
 * @date: 04/11/2024
 */


import java.util.ArrayList;

/**
 * Abstract class describing the characteristics of all ships.
 */
public abstract class Ship {
	/*
	 * describes the characteristics of all ships.
	 */

	// the row and column that contains the bow 
	private int bowRow;
	private int bowColumn;
	// the length of the ship
	private int length;
	// represents whether the ship is going to be placed horizontally or vertically
	private boolean horizontal;
	// 4 booleans that indicate whether that part of the ship has been  hit or not
	private boolean[] hit;

	/**
	 * default constructor sets the length property of the particular ship and initializes the hit array based on that length
	 * @param length
	 */
	public Ship(int length) {
		this.length = length;
		this.hit = new boolean[4];
	}

	// Getters
	/**
	 * @return the ship length
	 */
	public int getLength() {
		return this.length;
	}

	/**
	 * 
	 * @return the row corresponding to the position of the bow
	 */
	public int getBowRow() {
		return this.bowRow;
	}

	/**
	 * 
	 * @return the column corresponding to the position of the bow
	 */
	public int getBowColumn() {
		return this.bowColumn;
	}

	/**
	 * 
	 * @return the hit boolean array
	 */
	public boolean[] getHit() {
		return this.hit;
	}

	/**
	 * 
	 * @return whether the ship is horizontal (true) or not (false)
	 */
	public boolean isHorizontal() {
		return this.horizontal;
	}

	// Setters
	/**
	 * Sets the value of bowRow
	 * 
	 * @param row
	 */
	public void setBowRow(int row) {
		this.bowRow = row;
	}

	/**
	 * Sets the value of bowColumn
	 * 
	 * @param column
	 */
	public void setBowColumn(int column) {
		this.bowColumn = column;
	}
	
	/**
	 * Sets the value of the instance variable horizontal 
	 * 
	 * @param horizontal
	 */
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	/**
	 * Sets the value of the hit array at the given index to true.
	 * 
	 * @param index
	 */
	public void setHit(int index) {
		this.hit[index] = true;
	}



	// Abstract methods
	/**
	 * Every specific type of Ship (e.g. BattleShip, Cruiser, etc.) has to override and implement this method 
	 * and return the corresponding ship type.
	 * 
	 * @return the type of ship as a String
	 */
	public abstract String getShipType();

	// Other methods
	/**
	 *  Based on given parameters, returns true if it is okay to put a ship of this length with its bow in this location 
	 *  otherwise, returns false The ship must
	 *  The ship must not overlap another ship or touch another ship (vertically, horizontally, or diagonally), 
	 *  and it must be fully contained within the array. Does not modify the ship or ocean.
	 * 
	 * @param row
	 * @param column
	 * @param horizontal
	 * @param ocean
	 * @return true/false
	 */
	boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
		// if the row or column are not within 0-9
		if (column < 0 || column > 9 || row < 0 || row > 9 ) {
			return false;
		}

		/*
		 * Get the ship's extent using the getShipExtent() helper method. 
		 * If the last element of the array is < 0, the ship's position is invalid.
		 */
		ArrayList<Integer> extentArray = this.getShipExtent(row, column, horizontal);
		if (extentArray.get(extentArray.size() - 1) < 0) {
			return false;
		}

		/*
		 * If any part of the ship overlaps with 1 cell of another ship in the ocean, return false
		 */
		if (this.checkNeighbors(row, column, extentArray, horizontal, ocean)) {
			return false;
		}

		// otherwise return true
		return true;

	}
	
	
	/**
	 * “Puts” the ship in the ocean. This involves giving values to the given parameters. 
	 *it also involves putting a reference to the ship in each of 1 or more locations (up to 4) in the ships array in the Ocean object.
	 * Assume that horizontal ships have their bow at the right end, and vertical ships have their bow at the bottom end
	 * 
	 * @param row
	 * @param column
	 * @param horizontal
	 * @param ocean
	 */
	void placeShipAt(int row, int column, boolean horizontal, Ocean ocean) {

		Ship[][] ships = ocean.getShipArray();
		this.setBowRow(row);
		this.setBowColumn(column);
		this.setHorizontal(horizontal);

		if (horizontal) {
			// mark all points from [row][column] to [row][column - length + 1] to be the ship  if horizontally  oriented
			for (int i = column - this.length + 1; i <= column; i++) {
				ships[row][i] = this;
			}
		} 
		else {
			// mark all points from [row][column] to [row - length + 1][column] to be the ship if vertically oriented
			for (int i = row - this.length + 1; i <= row; i++) {
				ships[i][column] = this;
			}
		}
	}

	/**
	 * Finds the ship's "extent"
	 * E.g., a horizontal battleship with its bow placed in column 9
	 * would return the ArrayList: [9, 8, 7, 6], signifying that it extends over 4 columns.
	 * 
	 * @param row
	 * @param column
	 * @param horizontal
	 * @return
	 */
	private ArrayList<Integer> getShipExtent(int row, int column, boolean horizontal) {
		ArrayList<Integer> extent = new ArrayList<Integer>();

		int shipLength = this.getLength();

		if (horizontal) {
			// if horizontally oriented, take column coordinate of ship's bow and subtract ship's length
			for (int i = 0; i < shipLength; i++) {
				extent.add(column - i);
			}
		} else {
			// if vertically oriented, take row coordinate of ship's bow and subtract ship's length
			for (int i = 0; i < shipLength; i++) {
				extent.add(row - i);
			}
		}
		return extent;
	}

	/**
	 * Check all the neighboring cells for a proposed ship. 
	 * If occupied, return true. Otherwise, return false.
	 * 
	 * @param row
	 * @param column
	 * @param extent
	 * @param horizontal
	 * @param ocean
	 * @return
	 */
	private boolean checkNeighbors(int row, int column, ArrayList<Integer> extent, boolean horizontal, Ocean ocean) {

		// initialize an occupied boolean variable
		boolean occupied;

		/*
		 *  Increase the range of ships in both directions by 1. 
		 *  This takes into account any, possibly diagonally adjacent, ships. 
		 *  The first element of the range is always the largest, so add 1. 
		 *  The first element of the ArrayList is always the largest value, so add 1. 
		 *  The last element is always the smallest, so subtract 1.
		 */
		if (extent.get(0) < 9) {
			extent.add(0, extent.get(0) + 1);
		}

		if (extent.get(extent.size() - 1) > 0) {
			extent.add(extent.get(extent.size() - 1) - 1);
		}

		// if the ship is horizontally oriented
		if (horizontal) {
			// consider the cells in the row above and below the ship as well
			ArrayList<Integer> rows = new ArrayList<Integer>();
			if (row == 9) {
				rows.add(row - 1);
				rows.add(row);
			} else if (row == 0) {
				rows.add(row);
				rows.add(row + 1);
			} else {
				rows.add(row - 1);
				rows.add(row);
				rows.add(row + 1);
			}

			// column 
			for (int i = 0; i < extent.size(); i++) {
				//row 
				for (int j = 0; j < rows.size(); j++) {
					// check if is occupied
					occupied = ocean.isOccupied(rows.get(j), extent.get(i));
					if (occupied) {
						return true;
					}
				}
			}
			// if the ship is vertically oriented
		} else {
			// consider the cells in the column to the left and right of the ship as well
			ArrayList<Integer> cols = new ArrayList<Integer>();
			if (column == 9) {
				cols.add(column - 1);
				cols.add(column);
			} else if (column == 0) {
				cols.add(column);
				cols.add(column + 1);
			} else {
				cols.add(column - 1);
				cols.add(column);
				cols.add(column + 1);
			}

			// rows
			for (int i = 0; i < extent.size(); i++) {
				// columns
				for (int j = 0; j < cols.size(); j++) {
					// check if that cell is occupied
					occupied = ocean.isOccupied(extent.get(i), cols.get(j));
					if (occupied) {
						return true;
					}
				}
			}
		}
		return false;
	}



	/**
	 * If a part of the ship occupies the given row and column,and the ship hasn’t been sunk, mark that part of the ship as “hit”
	 * (in the hit array, index 0 indicates the bow) and return true; otherwise return false.
	 * 
	 * @param row
	 * @param column
	 * @return true/false
	 */
	boolean shootAt(int row, int column) {

		int bowColumn = this.getBowColumn();
		int bowRow = this.getBowRow();
		boolean sunk = this.isSunk();

		// check if horizontally oriented and not sunk
		if (horizontal && sunk == false) {

			// check if the bowRow is on the same row with the shot
			if (bowRow == row) {
				// if bowRow equals row, iterate through 
				for (int i = bowColumn - length + 1; i <= bowColumn; i++) {
					if (i == column) {
						// if is occupied by a ship,
						// set the corresponding element in the hit array to be true
						// index 0 indicates the bow
						// the corresponding index in the hit array is bowColumn - i
						this.setHit(bowColumn - i);
						return true;
					}
				}
			}
			// if the ship is vertically oriented and not sunk
		} else if (!horizontal && sunk == false) {

			// check if the bowColumn is in the same column with the shot
			if (bowColumn == column) {
				// if bowColumn equals column, iterate through all rows occupied by the ship
				for (int i = bowRow - length + 1; i <= bowRow; i++) {
					if (i == row) {
						// if is occupied by a ship,
						// set the corresponding element in the hit array to be true
						// index 0 indicates the bow
						// so the corresponding index in hit array is bowRow - i
						this.setHit(bowRow - i);
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @return true if every part of the ship has been hit, false otherwise
	 */
	boolean isSunk() {
		// initialize a counter for the number of hits
		int hits = 0;
		boolean[] hit = this.getHit();
		int shipLength = this.getLength();

		
		// Loop over the first n elements of the hit array, n is the length of the ship.
		for (int i = 0; i < shipLength; i++) {
			if (hit[i]) {
				hits++;
			}
		}

		//If the number of hits is equal to the length of the ship, the ship was sunk,
		if (hits == shipLength) {
			return true;
		} 
		else {
			return false;
		}
	}

	/**
	 * Returns a single-character String to use in the Ocean’s print method.
	 * Returns "s" if the ship has been sunk and "x" if not. 
	 * This method can be used to print out locations in the ocean that have been shot at.
	 */
	@Override
	public String toString() {

		if (this.isSunk()) {
			return "s";
		} else {
			return "x";
		}

	}

}