
/**
 * Zhanqian Wu 52463471
 * zhanqian@seas.upenn.edu
 */

package expenses;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ExpenseTest {

	@Test
	void testEquals() {

		////////////////////////////////////////////////
		///// DO NOT MODIFY THE TEST CODE BELOW! ///////
		
		//create individual expenses
		Expense expense1 = new Expense(12, 2.34);
		Expense expense2 = new Expense(12, 2.34);
		
		//compare for equality
		assertEquals(expense1, expense2, "The 2 expenses should be considered equal. They are for the same amount and the same month.");
		
		///// DO NOT MODIFY THE TEST CODE ABOVE! ///////
		////////////////////////////////////////////////
		
		
		// TODO insert 1 additional test case
        // Hint(s): Create additional expense objects and compare
		// Test case: Verify expenses are not equal if they differ in month or amount
        Expense expense3 = new Expense(11, 2.34); // Different month, same amount
        Expense expense4 = new Expense(12, 2.35); // Same month, different amount
        assertNotEquals(expense1, expense3, "The expenses should not be equal as they are for different months.");
        assertNotEquals(expense1, expense4, "The expenses should not be equal as they are for different amounts.");
	}

}
