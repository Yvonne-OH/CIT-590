/**
 * Zhanqian Wu 52463471
 * zhanqian@seas.upenn.edu
 */


import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import expenses.Expense;
import expenses.Expenses;

class ExpenseManagementTest {

	//define instance of expenses class for testing
	Expenses expenses;
	
	@BeforeEach
	void setUp() throws Exception {
		
		////////////////////////////////////////////////
		///// DO NOT MODIFY THE TEST CODE BELOW! ///////
		
		this.expenses = new Expenses();
		
		//initialize individual expenses and add for testing
		Expense expense = new Expense(12, 2.34);
		this.expenses.addExpense(expense);
		
		expense = new Expense(10, 98.34);
		this.expenses.addExpense(expense);
		
		expense = new Expense(2, 44.00);
		this.expenses.addExpense(expense);
		
		expense = new Expense(12, 12.01);
		this.expenses.addExpense(expense);
		
		///// DO NOT MODIFY THE TEST CODE ABOVE! ///////
		////////////////////////////////////////////////
		
	}

	@Test
	void testGetMonthlyExpensesIntListOfExpense() {
		
		////////////////////////////////////////////////
		///// DO NOT MODIFY THE TEST CODE BELOW! ///////
		
		//create list of expected monthly expense amounts
		List<Double> monthlyExpensesExpected = new ArrayList<Double>();
		monthlyExpensesExpected.add(98.34);
		
		//get actual monthly expense amounts for oct (10)
		List<Double> monthlyExpenses = ExpenseManagement.getMonthlyExpenses(10, this.expenses.getMonthlyExpenses());
		
		//confirm expected is equal to actual
		assertEquals(monthlyExpensesExpected, monthlyExpenses, "The expected list of monthly expenses for Oct. do not match the actual list of monthly expenses for Oct.");
		
		///// DO NOT MODIFY THE TEST CODE ABOVE! ///////
		////////////////////////////////////////////////
		
		
		// TODO insert 2 additional test cases
        // Hint(s): Create additional lists of expected monthly expense amounts 
		// and compare to actual monthly expense amounts for particular months
		List<Double> expectedMultipleExpenses = new ArrayList<>();
        expectedMultipleExpenses.add(2.34);
        expectedMultipleExpenses.add(12.01);
        List<Double> actualMultipleExpenses = ExpenseManagement.getMonthlyExpenses(12, this.expenses.getMonthlyExpenses());
        assertEquals(expectedMultipleExpenses, actualMultipleExpenses, "The expected list of monthly expenses for Dec. does not match the actual list.");

        // Additional test case 2: Test for month with no expenses
        List<Double> expectedNoExpenses = new ArrayList<>();
        List<Double> actualNoExpenses = ExpenseManagement.getMonthlyExpenses(1, this.expenses.getMonthlyExpenses());
        assertEquals(expectedNoExpenses, actualNoExpenses, "The expected list of monthly expenses for Jan. does not match the actual list (should be empty).");
		
	}

	@Test
	void testGetMonthlyExpensesStringListOfExpense() {
		
		////////////////////////////////////////////////
		///// DO NOT MODIFY THE TEST CODE BELOW! ///////
		
		//create list of expected monthly expense amounts
		List<Double> monthlyExpensesExpected = new ArrayList<Double>();
		monthlyExpensesExpected.add(98.34);
		
		//get actual monthly expense amounts for oct
		List<Double> monthlyExpenses = ExpenseManagement.getMonthlyExpenses("oct", this.expenses.getMonthlyExpenses());
		
		//confirm expected is equal to actual
		assertEquals(monthlyExpensesExpected, monthlyExpenses, "The expected list of monthly expenses for Oct. do not match the actual list of monthly expenses for Oct.");
		
		///// DO NOT MODIFY THE TEST CODE ABOVE! ///////
		////////////////////////////////////////////////
		
		
		// TODO insert 2 additional test cases
        // Hint(s): Create additional lists of expected monthly expense amounts 
		// and compare to actual monthly expense amounts for particular months
		this.expenses.addExpense(new Expense(1, 201.00));
        Expense mostExpensiveMonthNew = new Expense(1, 201.00);
        Expense actualMostExpensiveMonthNew = ExpenseManagement.getMostExpensiveMonth(this.expenses.getMonthlyExpenses());
        assertEquals(mostExpensiveMonthNew, actualMostExpensiveMonthNew, "The expected most expensive month does not match the actual most expensive month after adding new expenses.");

        // Additional test case 2: Add multiple expenses to a different month
        this.expenses.addExpense(new Expense(3, 50.00));
        this.expenses.addExpense(new Expense(8, 600.00));
        Expense mostExpensiveMonthMultiple = new Expense(8, 600.00);
        Expense actualMostExpensiveMonthMultiple = ExpenseManagement.getMostExpensiveMonth(this.expenses.getMonthlyExpenses());
        assertEquals(mostExpensiveMonthMultiple, actualMostExpensiveMonthMultiple, "The expected most expensive month does not match the actual most expensive month after adding multiple new expenses.");
	}

	@Test
	void testGetTotalMonthlyExpenses() {
		
		////////////////////////////////////////////////
		///// DO NOT MODIFY THE TEST CODE BELOW! ///////
		
		//get actual total monthly expense amount for oct
		double totalMonthlyExpenses = ExpenseManagement.getTotalMonthlyExpenses("oct", this.expenses.getMonthlyExpenses());
		
		//confirm it is 98.34
		assertEquals(98.34, totalMonthlyExpenses, "The expected total of monthly expenses for Oct. does not match the actual total of monthly expenses for Oct.");
		
		///// DO NOT MODIFY THE TEST CODE ABOVE! ///////
		////////////////////////////////////////////////
		
		
		// TODO insert 2 additional test cases
        // Hint(s): Test other total monthly expense amounts for particular months
		// Test case 1: Checking total monthly expenses for January
		this.expenses.addExpense(new Expense(1, 150.75));
	    double januaryExpenses = ExpenseManagement.getTotalMonthlyExpenses("jan", this.expenses.getMonthlyExpenses());
	    assertEquals(150.75, januaryExpenses, "The expected total of monthly expenses for Jan. does not match the actual total of monthly expenses for Jan.");
	    
	    // Test case 2: Checking total monthly expenses for July
	    this.expenses.addExpense(new Expense(7, 120.50));
	    double julyExpenses = ExpenseManagement.getTotalMonthlyExpenses("july", this.expenses.getMonthlyExpenses());
	    assertEquals(120.50, julyExpenses, "The expected total of monthly expenses for Jul. does not match the actual total of monthly expenses for Jul.");
	}

	@Test
	void testGetMostExpensiveMonth() {
		
		////////////////////////////////////////////////
		///// DO NOT MODIFY THE TEST CODE BELOW! ///////
		
		//create expense object for expected most expensive month 10 (oct)
		Expense mostExpensiveMonthCompare = new Expense(10, 98.34);
		
		//get expense object for actual most expensive month
		Expense mostExpensiveMonth = ExpenseManagement.getMostExpensiveMonth(this.expenses.getMonthlyExpenses());
		
		//compare expected expense object with actual expense object
		assertEquals(mostExpensiveMonthCompare, mostExpensiveMonth, "The expected most expensive month does not match the actual most expensive month.");
		
		///// DO NOT MODIFY THE TEST CODE ABOVE! ///////
		////////////////////////////////////////////////
		
		
		// TODO insert 2 additional test cases
        // Hint(s): Create additional expense object for a particular month and add to monthly expenses
		// Test if it's the most expensive month
		 // Test case 1: Verify if December becomes the most expensive month when its expenses are increased
	    this.expenses.addExpense(new Expense(12, 105.00)); 
	    Expense expectedDecember = new Expense(12, 119.35);
	    Expense actualMostExpensiveAfterUpdate = ExpenseManagement.getMostExpensiveMonth(this.expenses.getMonthlyExpenses());
	    assertEquals(expectedDecember, actualMostExpensiveAfterUpdate, "December should now be the most expensive month.");

	    // Test case 2: Verify if January becomes the most expensive month with new expense data
	    this.expenses.addExpense(new Expense(1, 120.00)); // Adding higher expense for January
	    Expense expectedJanuary = new Expense(1, 120.00);
	    actualMostExpensiveAfterUpdate = ExpenseManagement.getMostExpensiveMonth(this.expenses.getMonthlyExpenses());
	    assertEquals(expectedJanuary, actualMostExpensiveAfterUpdate, "January should now be the most expensive month.");
	}

}
