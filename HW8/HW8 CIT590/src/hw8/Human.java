package hw8;

import java.util.Random;
import java.util.Scanner;

public class Human {
	
	/**
	 * The name of the player
	 */
	private String name;
	
	/**
	 * The score of the current player
	 */
	private int score = 0;
	
	
	/**
	 * Constructs a human player with the given name
	 * @param name of human
	 */
	public Human(String name) {
		this.name = name;
	}

	/**
	 * Controls human player to roll the dice, and prints the related information for each roll.
	 * First of all, this method will automatically roll one time for the human player, 
	 * if the result is 6, the player will have no right to roll again and this method should return 0; 
	 * else, this roll will be added to the total score for this turn.
	 * User should answer Y or N (y or n) to determine whether they want to roll the dice again 
	 * or stop with the current score.
	 * 
	 * This method will also update the human's total score.
	 * -- You can either add the total of all the rolls to the human's total score, within this move method
	 * e.g. this.score += scoreOneRound;
	 *  
	 * -- or you can call the setScore method from outside of this class, after calling this move method 
	 * e.g. int scoreOneRound = human.move(computer, random, sc);
	 *      human.setScore(scoreOneRound + human.getScore());
	 * 
	 * @param computer player
	 * @param random number generator
	 * @param sc to get input from user
	 * @return the score this round (for example, 
	 * 1. the player rolls: 2, 6, then this method should return 0
	 * 2. the player rolls: 5, 5, 2, then this method should return 12
	 * )
	 */
	 public int move(Computer computer, Random random, Scanner sc) {
	        int scoreThisRound = 0;
	        boolean rolling = true;
	        while (rolling) {
	            int roll = random.nextInt(6) + 1; // Roll the dice (values 1-6)
	            System.out.println(name + " rolls: " + roll);
	            if (roll == 6) {
	                // If the roll is 6, round ends with a score of 0
	                return 0;
	            } else {
	                scoreThisRound += roll;
	                System.out.println("Current score this round: " + scoreThisRound + ". Roll again? (Y/N)");
	                String input = sc.nextLine().trim().toUpperCase();
	                if (!input.equals("Y")) {
	                    rolling = false;
	                }
	            }
	        }
	        this.score += scoreThisRound; // Update the total score
	        return scoreThisRound; // Return the score of this round
	    }

	
	 
	/**
	 * Get the name of human
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the score of human
	 * @return score
	 */
	public int getScore() {
		return this.score;
	}
	
	/**
	 * Set the score of human
	 * @param score
	 */
	public void setScore(int score) {
		this.score = score;
	}
	
}
