package battleship;

import java.util.Scanner;

/**
 * Main class for a human vs. computer version of Battleship.
 * This class initializes the game environment, handles user interactions,
 * and manages the game flow against an automated opponent.
 * It manages a game session where the user inputs coordinates to target ships placed randomly by the computer.
 * 
 * @author Zhanqian Wu & Yipeng Zhang
 * @date: 04/11/2024
 */
public class BattleshipGame {

    /**
     * Main method to start the game. It creates an instance of BattleshipGame and starts the game loop.
     */
    public static void main(String[] args) {
        new BattleshipGame().run();
    }

    /**
     * Runs the main game loop and controls the game flow.
     */
    public void run() {
        Ocean ocean = new Ocean();   // Create a new game ocean.
        ocean.placeAllShipsRandomly(); // Place ships randomly on the ocean grid.

        Scanner scanner = new Scanner(System.in); // Scanner for user input.

        printWelcome(); // Print the initial welcome message to the user.

        boolean gameOver = false;
        while (!gameOver) {
            ocean.print(); // Display the current state of the ocean.
            askNextShot(); // Prompt the user for coordinates to shoot.

            boolean validInput = false;
            int row = -1, col = -1;
            while (!validInput) {
                String entry = scanner.nextLine();
                String[] move = entry.split(",");
                try {
                    row = Integer.parseInt(move[0].trim());
                    col = Integer.parseInt(move[1].trim());
                    // Validate the coordinates are within the ocean grid.
                    if (row >= 0 && row < 10 && col >= 0 && col < 10) {
                        validInput = true;
                    } else {
                        System.out.println("Error: Input coordinates are out of bounds. Please enter valid row and column numbers (0 to 9).");
                        askNextShot();
                    }
                } catch (Exception e) {
                    // Handle exceptions if input is not an integer or improperly formatted.
                    System.out.println("Error: Invalid input format. Please enter coordinates in the format row,column (e.g., 3,4).");
                    askNextShot();
                }
            }

            // Proceed with the valid shot.
            boolean hit = ocean.shootAt(row, col);
            if (hit) {
                System.out.println("Hit!");
                Ship ship = ocean.getShipArray()[row][col];
                if (ship.isSunk()) {
                    System.out.println("You sank a " + ship.getShipType() + "!");
                }
            } else {
                System.out.println("Miss.");
            }

            // Print feedback on the number of shots fired.
            int shotsFired = ocean.getShotsFired();
            System.out.println("\nYou've fired " + shotsFired + " shot(s) so far.\n");

            gameOver = ocean.isGameOver(); // Check if the game is over.
        }

        System.out.println("Game over! It took you " + ocean.getShotsFired() + " shots to win.");
        scanner.close(); // Close the scanner to prevent resource leaks.
    }

    /**
     * Prints a welcome message to the player, explaining the game setup and objectives.
     */
    public void printWelcome() {
        System.out.println("Welcome to Battleship!");
        System.out.println("This is a one-player variant of the popular game.");
        System.out.println("The computer will place its ships randomly across a 10x10 board.");
        System.out.println("Your goal is to sink all of the computer's ships in as few moves as possible.");
        System.out.println("Here's how the board looks now.");
        System.out.println("");
    }

    /**
     * Prompts the player to input the next shot's coordinates in the format "row,column".
     */
    public void askNextShot() {
        System.out.println("\nWhere do you want to fire your next shot (row,column)? ");
        System.out.println("");
    }
}
