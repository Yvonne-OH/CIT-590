package battleship;

/*
 * @author Zhanqian Wu & Yipeng Zhang
 * @date: 04/11/2024
 */

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OceanTest {

	Ocean ocean;
	
	static int NUM_BATTLESHIPS = 1;
	static int NUM_CRUISERS = 2;
	static int NUM_DESTROYERS = 3;
	static int NUM_SUBMARINES = 4;
	static int OCEAN_SIZE = 10;
	
	@BeforeEach
	void setUp() throws Exception {
		ocean = new Ocean();
	}
	
	@Test
	void testEmptyOcean() {
		
		// Tests that all locations in the ocean are "empty"
		
		Ship[][] ships = ocean.getShipArray();
		
		for (int i = 0; i < ships.length; i++) {
			for (int j = 0; j < ships[i].length; j++) {
				Ship ship = ships[i][j];
				
				assertEquals("empty", ship.getShipType());
			}
		}
		
		assertEquals(0, ships[0][0].getBowRow());
		assertEquals(0, ships[0][0].getBowColumn());
		
		assertEquals(5, ships[5][5].getBowRow());
		assertEquals(5, ships[5][5].getBowColumn());
		
		assertEquals(9, ships[9][0].getBowRow());
		assertEquals(0, ships[9][0].getBowColumn());
	}
	
	@Test
	void testPlaceAllShipsRandomly() {
		
		// Tests that the correct number of each ship type is placed in the ocean
		
		ocean.placeAllShipsRandomly();

		Ship[][] ships = ocean.getShipArray();
		ArrayList<Ship> shipsFound = new ArrayList<Ship>();
		
		int numBattlehips = 0;
		int numCruisers = 0;
		int numDestroyers = 0;
		int numSubmarines = 0;
		int numEmptySeas = 0;
		
		for (int i = 0; i < ships.length; i++) {
			for (int j = 0; j < ships[i].length; j++) {
				Ship ship = ships[i][j];
				if (!shipsFound.contains(ship)) {
					shipsFound.add(ship);
				}
			}
		}
		
		for (Ship ship : shipsFound) {
			if ("battleship".equals(ship.getShipType())) {		
				numBattlehips++;
			} else if ("cruiser".equals(ship.getShipType())) {
				numCruisers++;
			} else if ("destroyer".equals(ship.getShipType())) {
				numDestroyers++;
			} else if ("submarine".equals(ship.getShipType())) {
				numSubmarines++;
			} else if ("empty".equals(ship.getShipType())) {
				numEmptySeas++;
			}
		}
		
		assertEquals(NUM_BATTLESHIPS, numBattlehips);
		assertEquals(NUM_CRUISERS, numCruisers);
		assertEquals(NUM_DESTROYERS, numDestroyers);
		assertEquals(NUM_SUBMARINES, numSubmarines);
		
		// Calculate total number of available spaces and occupied spaces
		int totalSpaces = OCEAN_SIZE * OCEAN_SIZE; 
		int occupiedSpaces = (NUM_BATTLESHIPS * 4)
				+ (NUM_CRUISERS * 3)
				+ (NUM_DESTROYERS * 2)
				+ (NUM_SUBMARINES * 1);
		
		// Test number of empty seas, each with length of 1
		assertEquals(totalSpaces - occupiedSpaces, numEmptySeas);
	}

	@Test
	void testIsOccupied() {

		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		Ship submarine = new Submarine();
		row = 0;
		column = 0;
		horizontal = false;
		submarine.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.isOccupied(1, 5));
		
		
		//TODO
		// More tests
		// Confirm (0,0) has been occupied by submarine
		assertTrue(ocean.isOccupied(0, 0));
		// Non-occupied area
		assertFalse(ocean.isOccupied(0, 1));
		// Check the boundary
		assertFalse(ocean.isOccupied(9, 1));
		
		assertTrue(ocean.isOccupied(0, 5));
		assertFalse(ocean.isOccupied(0, 4));
		assertFalse(ocean.isOccupied(1, 6));

		Ship battleship = new Battleship();
		row = 3;
		column = 7;
		horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);

		assertTrue(ocean.isOccupied(3, 6));
		assertTrue(ocean.isOccupied(3, 4));
		assertFalse(ocean.isOccupied(2, 7));

		Ship cruiser = new Cruiser();
		row = 5;
		column = 9;
		horizontal = true;
		cruiser.placeShipAt(row, column, horizontal, ocean);

		assertTrue(ocean.isOccupied(5, 8));
		assertTrue(ocean.isOccupied(5, 7));
		assertFalse(ocean.isOccupied(4, 7));		
	}

	
	@Test
	void testShootAt() {
	
		assertFalse(ocean.shootAt(0, 1));
		
		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.shootAt(1, 5));
		assertFalse(destroyer.isSunk());
		assertTrue(ocean.shootAt(0, 5));
		
		
		//TODO
		//More tests
		
		// When the destroyer is sunk because shoot at (0,5) and (1,5)
		assertTrue(destroyer.isSunk());
		
		// Test out of range shoot 
		assertFalse(ocean.shootAt(0, 6));
		assertFalse(ocean.shootAt(1, 4));

		// Test submarine whether has been sunk
		Submarine submarine = new Submarine();
		row = 0;
		column = 0;
		horizontal = false;
		submarine.placeShipAt(row, column, horizontal, ocean);

		assertTrue(ocean.shootAt(0, 0));
		assertTrue(submarine.isSunk());

		Cruiser cruiser = new Cruiser();
		row = 3;
		column = 7;
		horizontal = true;
		cruiser.placeShipAt(row, column, horizontal, ocean);

		assertTrue(ocean.shootAt(3, 7));
		assertFalse(cruiser.isSunk());
		assertTrue(ocean.shootAt(3, 6));
		assertTrue(ocean.shootAt(3, 5));
		assertTrue(cruiser.isSunk());
		
		// Test battleship whether has been sunk
		Ship battleship = new Battleship();
		row = 9;
		column = 3;
		horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		assertTrue(ocean.shootAt(9, 0));
		assertTrue(ocean.shootAt(9, 1));
		assertTrue(ocean.shootAt(9, 2));
		assertTrue(ocean.shootAt(9, 3));
		assertTrue(battleship.isSunk());
	}

	
	@Test
	void testGetShotsFired() {
		
		//should be all false - no ships added yet
		assertFalse(ocean.shootAt(0, 1));
		assertFalse(ocean.shootAt(1, 0));
		assertFalse(ocean.shootAt(3, 3));
		assertFalse(ocean.shootAt(9, 9));
		assertEquals(4, ocean.getShotsFired());
		
		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		Ship submarine = new Submarine();
		row = 0;
		column = 0;
		horizontal = false;
		submarine.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.shootAt(1, 5));
		assertFalse(destroyer.isSunk());
		assertTrue(ocean.shootAt(0, 5));
		assertTrue(destroyer.isSunk());
		assertEquals(6, ocean.getShotsFired());
		
		
		//TODO
		//More tests
		Cruiser cruiser = new Cruiser();
		row = 3;
		column = 7;
		horizontal = true;
		cruiser.placeShipAt(row, column, horizontal, ocean);

		assertTrue(ocean.shootAt(3, 7));
		assertFalse(cruiser.isSunk());
		assertTrue(ocean.shootAt(3, 6));
		assertTrue(ocean.shootAt(3, 5));
		assertTrue(cruiser.isSunk());
		assertEquals(9, ocean.getShotsFired());	
		
		// Count the times that ships has been fired when add a submarine
		Ship submarine1 = new Submarine();
		row = 0;
		column = 0;
		horizontal = true;
		submarine1.placeShipAt(row, column, horizontal, ocean);
		assertFalse(ocean.shootAt(0, 1));
		assertFalse(ocean.shootAt(1, 0));
		assertTrue(ocean.shootAt(0, 0));
		assertTrue(submarine1.isSunk());
		assertEquals(12, ocean.getShotsFired());
		
		// Count the times that ships has been fired when add a battleship
		Ship battleship = new Battleship();
		row = 9;
		column = 3;
		horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		assertFalse(ocean.shootAt(0, 1));
		assertFalse(ocean.shootAt(1, 0));
		assertFalse(ocean.shootAt(0, 0));
		assertFalse(battleship.isSunk());
		assertTrue(ocean.shootAt(9, 0));
		assertTrue(ocean.shootAt(9, 1));
		assertTrue(ocean.shootAt(9, 2));
		assertTrue(ocean.shootAt(9, 3));
		assertTrue(battleship.isSunk());
		assertEquals(19, ocean.getShotsFired());
	}

	
	@Test
	void testGetHitCount() {
		
		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.shootAt(1, 5));
		assertFalse(destroyer.isSunk());
		assertEquals(1, ocean.getHitCount());
		
		
		//TODO
		//More tests
		assertTrue(ocean.shootAt(0, 5));
		assertTrue(destroyer.isSunk());
		assertEquals(2, ocean.getHitCount());

		Cruiser cruiser = new Cruiser();
		row = 3;
		column = 7;
		horizontal = true;
		cruiser.placeShipAt(row, column, horizontal, ocean);

		assertTrue(ocean.shootAt(3, 7));
		assertFalse(cruiser.isSunk());
		assertTrue(ocean.shootAt(3, 6));
		assertTrue(ocean.shootAt(3, 5));
		assertTrue(cruiser.isSunk());
		assertEquals(5, ocean.getHitCount());

		// Count the times ships has been hit when add a submarine
		Ship submarine = new Submarine();
		row = 0;
		column = 0;
		horizontal = false;
		submarine.placeShipAt(row, column, horizontal, ocean);

		assertTrue(ocean.shootAt(0, 0));
		assertTrue(submarine.isSunk());
		assertFalse(ocean.shootAt(0, 1));
		assertFalse(ocean.shootAt(1, 0));
		assertEquals(6, ocean.getHitCount());
		
		// Count the times ships has been hit when add a battleship
		Ship battleship = new Battleship();
		row = 9;
		column = 3;
		horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		assertFalse(ocean.shootAt(0, 1));
		assertFalse(ocean.shootAt(1, 0));
		assertFalse(ocean.shootAt(0, 0));
		assertTrue(ocean.shootAt(9, 0));
		assertTrue(ocean.shootAt(9, 1));
		assertTrue(ocean.shootAt(9, 2));
		assertTrue(ocean.shootAt(9, 3));
		assertEquals(10, ocean.getHitCount());
	}
	
	
	@Test
	void testGetShipsSunk() {
		
		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.shootAt(1, 5));
		assertFalse(destroyer.isSunk());
		assertEquals(1, ocean.getHitCount());
		assertEquals(0, ocean.getShipsSunk());
		
		
		//TODO
		//More tests
		// Confirm the destroyer has been sunk and count the times of getting ship sunk
		assertTrue(ocean.shootAt(0, 5));
		assertTrue(destroyer.isSunk());
		assertEquals(2, ocean.getHitCount());
		assertEquals(1, ocean.getShipsSunk());

		//confirm the destroyer and the submarine has been sunk 
		Ship submarine = new Submarine();
		row = 0;
		column = 0;
		horizontal = false;
		submarine.placeShipAt(row, column, horizontal, ocean);

		assertTrue(ocean.shootAt(0, 0));
		assertTrue(submarine.isSunk());
		assertEquals(3, ocean.getHitCount());
		assertEquals(2, ocean.getShipsSunk());
		
		//try hit again when the submarine has already been hit
		assertFalse(ocean.shootAt(0, 0));
		assertEquals(3, ocean.getHitCount());

		Cruiser cruiser = new Cruiser();
		row = 3;
		column = 7;
		horizontal = true;
		cruiser.placeShipAt(row, column, horizontal, ocean);

		assertTrue(ocean.shootAt(3, 7));
		assertFalse(cruiser.isSunk());
		assertTrue(ocean.shootAt(3, 6));
		assertTrue(ocean.shootAt(3, 5));
		assertTrue(cruiser.isSunk());
		assertEquals(6, ocean.getHitCount());
		assertEquals(3, ocean.getShipsSunk());
		
		// Confirm the battleship has been sunk
		Ship battleship = new Battleship();
		row = 9;
		column = 3;
		horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		assertFalse(ocean.shootAt(0, 1));
		assertFalse(ocean.shootAt(1, 0));
		assertFalse(ocean.shootAt(0, 0));
		assertTrue(ocean.shootAt(9, 0));
		assertTrue(ocean.shootAt(9, 1));
		assertTrue(ocean.shootAt(9, 2));
		assertTrue(ocean.shootAt(9, 3));
		assertEquals(10, ocean.getHitCount());
		assertEquals(4, ocean.getShipsSunk());
	}

	
	@Test
	void testGetShipArray() {
		
		Ship[][] shipArray = ocean.getShipArray();
		assertEquals(OCEAN_SIZE, shipArray.length);
		assertEquals(OCEAN_SIZE, shipArray[0].length);
		
		assertEquals("empty", shipArray[0][0].getShipType());
		
		
		//TODO
		//More tests
		
		// Confirm the submarine
		Ship submarine = new Submarine();
		int row = 0;
		int column = 0;
		boolean horizontal = false;
		submarine.placeShipAt(row, column, horizontal, ocean);

		assertEquals("submarine", shipArray[0][0].getShipType());

		// Confirm the destroyer
		Destroyer destroyer = new Destroyer();
		row = 1;
		column = 5;
		horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);

		assertEquals("destroyer", shipArray[0][5].getShipType());
		
		// Confirm the battleship
		Ship battleship = new Battleship();
		row = 9;
		column = 3;
		horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		assertEquals("battleship", shipArray[9][3].getShipType());
	}

}
