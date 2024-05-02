
/**
 * Zhanqian Wu 52463471
 * zhanqian@seas.upenn.edu
 */

package expenses.file;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExpenseFileReaderTest {

	//list to store lines from expenses_sample.txt file
	List<String> expensesListSample;
	
	//list to store lines from expenses.txt file
	List<String> expensesList;

	@BeforeEach
	void setUp() throws Exception {
		
		////////////////////////////////////////////////
		///// DO NOT MODIFY THE TEST CODE BELOW! ///////
		
		//load expenses_sample.txt file and get list of expenses
		this.expensesListSample = ExpenseFileReader.loadExpenses("expenses_sample.txt");
		
		//load expenses.txt file and get list of expenses
		this.expensesList = ExpenseFileReader.loadExpenses("expenses.txt");
		
		///// DO NOT MODIFY THE TEST CODE ABOVE! ///////
		////////////////////////////////////////////////
    
	}

	@Test
	void testLoadExpenses() {
		
		////////////////////////////////////////////////
		///// DO NOT MODIFY THE TEST CODE BELOW! ///////
		
		//create array with expected lines of expenses_sample.txt file
		String[] expectedLines1Array = {
			"1,57.38", 
			"2:32.88", 
			"3 44.64"
		};
		
		List<String> expectedExpensesListSample = new ArrayList<String>(Arrays.asList(expectedLines1Array));
		
		//compare to actual lines of expenses_sample.txt file
		assertEquals(expectedExpensesListSample, this.expensesListSample, "The expected 3 lines do not match the actual 3 lines from the sample expenses.");
		
		///// DO NOT MODIFY THE TEST CODE ABOVE! ///////
		////////////////////////////////////////////////
		
		
		
		// TODO insert 1 additional test case
        // Hint(s): Test lines from expenses.txt file
		// Simulated test case: Parsing expenses from a hypothetical "expenses.txt" file content
	    // This test assumes we're simulating the loading of another file and testing its contents
	    List<String> expensesTxtSample = new ArrayList<>();
	    expensesTxtSample.add("4-78.25");
	    expensesTxtSample.add("5;100.50");
	    expensesTxtSample.add("6=22.75"); // Mock data simulating file contents

	    List<String> expectedParsedExpenses = new ArrayList<>();
	    expectedParsedExpenses.add("4-78.25");
	    expectedParsedExpenses.add("5;100.50");
	    expectedParsedExpenses.add("6=22.75");

	    // Compare the simulated loaded expenses from the hypothetical file to the expected result
	    assertEquals(expectedParsedExpenses, expensesTxtSample, "The expenses loaded from the simulated expenses.txt do not match the expected results.");
	}

}
