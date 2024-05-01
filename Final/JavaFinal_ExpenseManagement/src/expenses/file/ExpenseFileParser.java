package expenses.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the loading and parsing of expense files.
 * @author lbrandon
 *
 */
public class ExpenseFileParser {

	/**
	 * Parses month and expense amount from given list of expenses (returned from ExpenseFileReader.loadExpenses).
	 * Each row has a month number, a delimiter, and an expense amount.
	 * Delimiters can include a comma (,), a colon (:), or multiple spaces ('    ').
	 * 
	 * Stores expenses in list of maps, each one consisting of a month number and an expense amount.
	 * For example:
	 *   [{1=57.38}, {5=5.06}, {10=456.99}, {5=3.99}, ...]
	 *   Where 1 is the month (for jan) and 57.38 is an expense for that month.  
	 *   5 is the month (for may) and 5.06 is an expense amount for that month.
	 *   3.99 is another expense amount for month 5.
	 * @param list of expenses to parse
	 * @return list of maps of monthly expenses
	 */
	public static List<Map<Integer, Double>> parseExpenses(List<String> expenseList) {
		
		// TODO Implement method
		// Hint: Iterate over each expense line, e.g. "1,57.38"	
		// Split expense line, based on the delimiters, into an array, e.g. ["1", "57.38"]
		// Get the month number and expense amount from the array		
		// Create a hashmap with month number as key and expense amount as value, e.g. {1=57.38}
		// Add hashmap to list of hashmaps
		// Return list of hashmaps
		
		 List<Map<Integer, Double>> monthlyExpenses = new ArrayList<>();
	        for (String expense : expenseList) {
	            String[] parts = expense.split("[,:\\s]+"); // Regular expression to split by comma, colon, or whitespace.
	            int month = Integer.parseInt(parts[0]);
	            double amount = Double.parseDouble(parts[1]);
	            Map<Integer, Double> expenseMap = new HashMap<>();
	            expenseMap.put(month, amount);
	            monthlyExpenses.add(expenseMap);
	        }
	        return monthlyExpenses;
	}
	
	///// DO NOT CHANGE CODE IN MAIN METHOD! /////
	public static void main(String[] args) {
		
		//load expenses_sample.txt file and get list of expenses
		List<String> expensesListSample = ExpenseFileReader.loadExpenses("expenses_sample.txt");
		
		//parse list of sample expenses into a list of expense maps
		List<Map<Integer, Double>> monthlyExpensesSample = ExpenseFileParser.parseExpenses(expensesListSample);
				
		//print list of sample expense maps for debugging purposes
		System.out.println("SAMPLE EXPENSES\n");
		for (Map<Integer, Double> monthlyExpense : monthlyExpensesSample) {
			System.out.println(monthlyExpense);
		}
		
		//blank line
		System.out.println();
		
		//load expenses.txt file and get list of expenses
		List<String> expensesList = ExpenseFileReader.loadExpenses("expenses.txt");
		
		//parse list of expenses into a list of expense maps
		List<Map<Integer, Double>> monthlyExpenses = ExpenseFileParser.parseExpenses(expensesList);
				
		//print list of expense maps for debugging purposes
		System.out.println("EXPENSES\n");
		for (Map<Integer, Double> monthlyExpense : monthlyExpenses) {
			System.out.println(monthlyExpense);
		}
		
	}
}