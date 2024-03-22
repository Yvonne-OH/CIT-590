package hw8;

import java.util.Random;

public class Computer {
	
	/**
	 * The score of the computer
	 */
	private int score = 0;
	
	/**
	 * Controls computer player to roll the dice, and prints the related information for each roll
	 * First of all, this method will automatically roll one time for the computer player, 
	 * if the result is 6, the computer will have no right to roll and this method should return 0; 
	 * else, this roll will be added to the total score for this turn. 
	 * The computer should play intelligently to determine whether they want to roll the dice again 
	 * or stop with the current score.  It depends on you how to design the strategy for the computer.
	 * 
	 * This method will also update the computer's total score.
	 * -- You can either add the total of all the rolls to the computer's total score, within this move method
	 * e.g. this.score += scoreOneRound;
	 *  
	 * -- or you can call the setScore method from outside of this class, after calling this move method
	 * e.g. int scoreOneRound = computer.move(human, random);
	 *      computer.setScore(scoreOneRound + computer.getScore());
	 * 
	 * @param human player
	 * @param random number generator
	 * @return the score this round (for example, 
	 * 1. the computer rolls: 2, 6, then this method should return 0;
	 * 2. the computer rolls: 5, 5, 2, then this method should return 12;
	 * )
	 */

    public int move(Human human, Random random) {
        int scoreThisRound = 0;
        boolean rolling = true;
        while (rolling) {
            int roll = random.nextInt(6) + 1; // Roll the dice (values 1-6)
            System.out.println("Computer rolls: " + roll);
            if (roll == 6) {
                // If the roll is 6, round ends with score of 0
                return 0;
            } else {
                scoreThisRound += roll;
                // Simple strategy: stop if this round's score >= 10 or total score after this round beats human
                if (scoreThisRound >= 10 || (this.score + scoreThisRound) > human.getScore()) {
                    rolling = false;
                }
            }
        }
        this.score += scoreThisRound; // Update total score
        return scoreThisRound; // Return the score of this round
    }
    
	
	/**
	 * Get the score of computer
	 * @return score
	 */
	public int getScore() {
		return this.score;
	}
	
	/**
	 * Set the score of computer
	 * @param score
	 */
	public void setScore(int score) {
		this.score = score;
	}
}
