/**
 * Zhanqian Wu 52463471
 * zhanqian@seas.upenn.edu
 */


package expenses.file;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExpenseFileParserTest {

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
	void testParseExpenses() {
		
		////////////////////////////////////////////////
		///// DO NOT MODIFY THE TEST CODE BELOW! ///////
		
		//parse list of sample expenses into a list of expense maps
		List<Map<Integer, Double>> monthlyExpensesSample = ExpenseFileParser.parseExpenses(this.expensesListSample);
		
		//create arraylist with expected expense maps
		List<Map<Integer, Double>> expectedMonthlyExpensesSample = new ArrayList<Map<Integer, Double>>();
		
		Map<Integer, Double> sampleExpenseMap = new HashMap<Integer, Double>();
		sampleExpenseMap.put(1, 57.38);
		expectedMonthlyExpensesSample.add(sampleExpenseMap);
		
		sampleExpenseMap = new HashMap<Integer, Double>();
		sampleExpenseMap.put(2, 32.88);
		expectedMonthlyExpensesSample.add(sampleExpenseMap);
		
		sampleExpenseMap = new HashMap<Integer, Double>();
		sampleExpenseMap.put(3, 44.64);
		expectedMonthlyExpensesSample.add(sampleExpenseMap);
		
		//compare to actual expense maps
		assertEquals(expectedMonthlyExpensesSample, monthlyExpensesSample, "The expected 3 expense maps do not match the actual 3 expense maps from the sample expenses.");
		
		///// DO NOT MODIFY THE TEST CODE ABOVE! ///////
		////////////////////////////////////////////////
		
		
		// TODO insert 1 additional test case
        // Hint(s): Parse and test list of expenses from expenses.txt
		// Simulated test case: Parsing expenses from a hypothetical "expenses.txt" file content
		List<String> expensesTxtSample = new ArrayList<>();
	    expensesTxtSample.add("4,78.25");
	    expensesTxtSample.add("5,100.50");
	    expensesTxtSample.add("4,22.75"); // Mock data simulating file contents
	    List<Map<Integer, Double>> parsedExpenses = ExpenseFileParser.parseExpenses(expensesTxtSample);
	    
	    List<Map<Integer, Double>> expectedParsedExpenses = new ArrayList<>();
	    sampleExpenseMap = new HashMap<>();
	    sampleExpenseMap.put(4, 78.25);
	    expectedParsedExpenses.add(sampleExpenseMap);
	    
	    sampleExpenseMap = new HashMap<>();
	    sampleExpenseMap.put(5, 100.50);
	    expectedParsedExpenses.add(sampleExpenseMap);
	    
	    sampleExpenseMap = new HashMap<>();
	    sampleExpenseMap.put(4, 22.75);
	    expectedParsedExpenses.add(sampleExpenseMap);
	    
	    // Compare the parsed expenses from the simulated file to the expected result
	    assertEquals(expectedParsedExpenses, parsedExpenses, "The expenses parsed from the simulated expenses.txt do not match the expected results.");
			
	}

}
