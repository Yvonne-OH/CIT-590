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
        System.out.println("Enter the name for the human player:");
        String humanName = sc.nextLine();
        createPlayers(humanName);
        while (!checkWinningStatus()) {
            System.out.println(human.getName() + "'s turn:");
            human.move(computer, random, sc);
            if (checkWinningStatus()) break;
            System.out.println("Computer's turn:");
            computer.move(human, random);
        }
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
		System.out.println("Final scores:");
        System.out.println(human.getName() + ": " + human.getScore());
	    System.out.println("Computer: " + computer.getScore());
	}
	
	/**
     * Determines who won the game, and prints the results
     */
	public void printWinner() {
		if (human.getScore() > computer.getScore()) {
            System.out.println(human.getName() + " wins!");
        } else if (computer.getScore() > human.getScore()) {
            System.out.println("Computer wins!");
        } else {
            System.out.println("It's a tie!");
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
