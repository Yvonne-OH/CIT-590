package hw8;

import java.util.Random;
import java.util.Scanner;

public class GameControl {
	
	/**
	 * Computer player
	 */
	Computer computer;
	
	/**
	 * Human player
	 */
	Human human;
	
	/**
	 * A random number generator to be used for returning random dice result (1-6) for both computer and human player
	 */
	Random random = new Random();
	
	/**
	 * The main method just creates a GameControl object and calls its run method
	 * @param args not used
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner (System.in);
		System.out.println("Welcome to Pig!");
		
		GameControl gc = new GameControl();
		while (true) {
			gc.run(sc);
			System.out.println("--------------------");
			System.out.println("Do you want to play again?");
			
			boolean check = gc.askYesNo(sc);
			if (!check) {
				System.out.println("Goodbye!");
				sc.close();
				break;
			}
		}
	}
	
	/**
     * Calls methods to perform each of the following actions:
     * - Ask user to input the name for human player, and print welcome with the name
     * - Create the players (one human and one computer)
     * - Control the play of the game
     * - Print the final results
	 * @param sc to use for getting user input
	 */

	public void run(Scanner sc) {
	    // Prompt the user for the human player's name and read it from the input.
	    System.out.println("Enter the name for the human player:");
	    String humanName = sc.nextLine();

	    // Initialize game players using the provided human player name.
	    createPlayers(humanName);

	    // Flags to track if the computer or human player has reached a score of 50, and whether an additional turn is needed.
	    boolean computerReached50 = false;
	    boolean humanReached50 = false;
	    boolean additionalTurn = false;

	    // Main game loop continues until a winning condition is met or an additional turn is needed.
	    while (!checkWinningStatus() || additionalTurn) {
	        // Computer's turn to play if it hasn't reached 50 points, or if it's the only player to have reached 50.
	        if (!computerReached50 || (computerReached50 && !humanReached50)) {
	            System.out.println("---------------------------");
	            System.out.println("Computer's turn:");
	            int computerScoreThisRound = computer.move(human, random); // Computer makes its move.
	            // Print out the computer's score for this round and its total score.
	            System.out.println("Computer's score in this round: " + computerScoreThisRound);
	            System.out.println("Computer's total score is: " + computer.getScore());
	            System.out.println("---------------------------");
	            // Check if the computer has reached 50 points.
	            if (computer.getScore() >= 50) {
	                computerReached50 = true;
	            }
	        }

	        // Human player's turn to play if the game hasn't been won or if the computer has reached 50 points.
	        if (!checkWinningStatus() || computerReached50) {
	            System.out.println("---------------------------");
	            System.out.println(human.getName() + "'s turn:");
	            int humanScoreThisRound = human.move(computer, random, sc); // Human makes their move.
	            // Print out the human's score for this round and their total score.
	            System.out.println(human.getName() + "'s score in this round: " + humanScoreThisRound);
	            System.out.println(human.getName() + "'s total score is: " + human.getScore());
	            System.out.println("---------------------------");
	            // Check if the human has reached 50 points.
	            if (human.getScore() >= 50) {
	                humanReached50 = true;
	            }
	        }

	        // Determine if an additional turn is warranted based on the game's rules.
	        additionalTurn = false; // Reset the flag for additional turn.
	        if (computerReached50 && !humanReached50) {
	            // If the computer reached 50 first and the human hasn't had an additional turn.
	            additionalTurn = true;
	            humanReached50 = true; // Mark that the human is getting an additional turn.
	        } else if (humanReached50 && computerReached50) {
	            // If both have reached 50 or more, and the scores are tied.
	            if (human.getScore() == computer.getScore()) {
	                additionalTurn = true; // Continue the game until the tie is broken.
	            }
	        }
	    }

	    // Once the game loop ends, print the final results and announce the winner.
	    printResults();
	    printWinner();
	}

	
	/**
     * Creates one human player with the given humanName, and one computer player with a name.
     * @param humanName for human player
     */
	public void createPlayers(String humanName) {
        human = new Human(humanName);
        computer = new Computer();
	}
	
	/**
     * Checks if a winning status has been achieved
     * @return true if one player has won the game
     */
	public boolean checkWinningStatus() {
		return human.getScore() >= 50 || computer.getScore() >= 50;
	}
	
	/**
	 * Prints the final scores of the human player and computer player
	 */
	public void printResults() {
	    // Print the header row.
	    System.out.printf("%-20s %s%n", "Player", "Score");
	    // Print a separator for clarity.
	    System.out.println("---------------------------");
	    // Print the scores for the human and computer players, aligning text to the left.
	    System.out.printf("%-20s %d%n", human.getName(), human.getScore());
	    System.out.printf("%-20s %d%n", "Computer", computer.getScore());
	}

	
	/**
     * Determines who won the game, and prints the results
     */
	public void printWinner() {
	    if (human.getScore() > 50 && computer.getScore() > 50) {
	        System.out.println("It's a tie!"); // If both players score above 50, it's a tie.
	    } else if (human.getScore() > computer.getScore()) {
	        System.out.println(human.getName() + " wins!");
	    } else if (computer.getScore() > human.getScore()) {
	        System.out.println("Computer wins!");
	    } else {
	        System.out.println("Game Continue"); // Covers the case where both scores are equal and not necessarily above 50.
	    }
	}
	
	/**
	 * If the user responds a string starting with "y" or "Y", the function returns True. 
	 * If the  user responds a string starting with "n" or "N", the function returns False. 
	 * Any other response will cause the question to be repeated until the user provides an acceptable response. 
	 * @param sc to use for getting user input
	 * @return true if user responds with "y" or "Y"
	 */
	public boolean askYesNo(Scanner sc) {
        String answer;
        do {
            answer = sc.nextLine().trim().toLowerCase();
            if (answer.startsWith("y")) {
                return true;
            } else if (answer.startsWith("n")) {
                return false;
            } else {
                System.out.println("Please answer with 'Y' or 'N'.");
            }
        } while (true);
		
	}
	
}
