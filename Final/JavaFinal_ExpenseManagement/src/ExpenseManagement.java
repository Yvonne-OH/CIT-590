

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import expenses.Expense;
import expenses.Expenses;
import expenses.file.ExpenseFileParser;
import expenses.file.ExpenseFileReader;

/**
 * Provides static methods for extracting information from monthly expenses.
 * @author lbrandon
 */
public class ExpenseManagement {

	/**
	 * Mappings to match a month abbreviation with a month number.
	 * For example, the map should look like this:
	 *   {"jan"=1, "feb"=2, ...}
	 */
	private static Map<String, Integer> MONTHLY_MAPPINGS;
	
	static {
		
		//Initializes map of month abbreviations and creates mappings 
		ExpenseManagement.MONTHLY_MAPPINGS = new HashMap<String, Integer>();
		
		//populate map with keys (month abbreviations) and values (month numbers)
		ExpenseManagement.MONTHLY_MAPPINGS.put("jan", 1);
		ExpenseManagement.MONTHLY_MAPPINGS.put("feb", 2);
		ExpenseManagement.MONTHLY_MAPPINGS.put("march", 3);
		ExpenseManagement.MONTHLY_MAPPINGS.put("april", 4);
		ExpenseManagement.MONTHLY_MAPPINGS.put("may", 5);
		ExpenseManagement.MONTHLY_MAPPINGS.put("june", 6);
		ExpenseManagement.MONTHLY_MAPPINGS.put("july", 7);
		ExpenseManagement.MONTHLY_MAPPINGS.put("aug", 8);
		ExpenseManagement.MONTHLY_MAPPINGS.put("sept", 9);
		ExpenseManagement.MONTHLY_MAPPINGS.put("oct", 10);
		ExpenseManagement.MONTHLY_MAPPINGS.put("nov", 11);
		ExpenseManagement.MONTHLY_MAPPINGS.put("dec", 12);
	}
	
	/**
	 * Static method that gets mappings to match a month abbreviation with a month number.
	 * @return map of month names and month numbers
	 */
	public static Map<String, Integer> getMonthlyMappings() {
		return ExpenseManagement.MONTHLY_MAPPINGS;
	}
	
	/**
	 * Static method that gets list of expense amounts for given month number.
	 * @param month to look up
	 * @param list of monthly expenses to search
	 * @return list of expense amounts for given month
	 */
	public static List<Double> getMonthlyExpenses(int month, List<Expense> monthlyExpenses) {
		
		// TODO Implement method
		// Hint: Iterate over list of monthly expenses and find the expense amounts for given month
        List<Double> expensesForMonth = new ArrayList<>();
        // Iterate over the provided list of expenses and collect the amounts for the specified month.
        for (Expense expense : monthlyExpenses) {
            if (expense.getMonth() == month) {
                expensesForMonth.add(expense.getAmount());
            }
        }
        return expensesForMonth;
	}
	
	/**
	 * Static method that gets list of expense amounts for given month abbreviation.
	 * Maps given month name to month number.
	 * @param month to look up
	 * @param list of monthly expenses to search
	 * @return list of expense amounts for given month
	 */
	public static List<Double> getMonthlyExpenses(String month, List<Expense> monthlyExpenses) {
		
		// TODO Implement method
		// Hint: Get number associated with given month
		// Iterate over list of monthly expenses and find the expense amounts for given numeric month
        Integer monthNumber = MONTHLY_MAPPINGS.get(month);
        return getMonthlyExpenses(monthNumber, monthlyExpenses);
	}

	/**
	 * Static method that gets total expense amount for given month abbreviation.
	 * @param month to look up
	 * @param list of monthly expenses to search
	 * @return total expense amount for given month
	 */
	public static double getTotalMonthlyExpenses(String month, List<Expense> monthlyExpenses) {

		// TODO Implement method
		// Hint: Get expense amounts for given month
		// Calculate sum of all expense amounts
	    List<Double> expenses = getMonthlyExpenses(month, monthlyExpenses);
	    // Sum up all the expenses retrieved for the given month to calculate the total.
	    return expenses.stream().mapToDouble(Double::doubleValue).sum();
	}
	
	/**
	 * Static method that identifies the month with the highest expense amount.
	 * First, gets the total expense amount for each month, then gets the greatest one.
	 * @param list of monthly expenses to search
	 * @return Expense object with information for most expensive month
	 */
	public static Expense getMostExpensiveMonth(List<Expense> monthlyExpenses) {

		// TODO Implement method
		// Iterate over each month and get the total expenses for each
		// Identify the month with the greatest expense amount
		double maxExpense = 0;
        int monthWithMaxExpense = 1; // Default to January if no expenses are recorded.
        Map<Integer, Double> totalExpensesByMonth = new HashMap<>();

        // Calculate total expenses for each month.
        for (Expense expense : monthlyExpenses) {
            totalExpensesByMonth.merge(expense.getMonth(), expense.getAmount(), Double::sum);
        }

        // Determine the month with the highest total expenses.
        for (Map.Entry<Integer, Double> entry : totalExpensesByMonth.entrySet()) {
            if (entry.getValue() > maxExpense) {
                maxExpense = entry.getValue();
                monthWithMaxExpense = entry.getKey();
            }
        }

        return new Expense(monthWithMaxExpense, maxExpense);
	}
	
	///// DO NOT CHANGE CODE IN MAIN METHOD! /////
	public static void main(String[] args) {
		
		//load expenses.txt file and get list of expenses
		List<String> expensesList = ExpenseFileReader.loadExpenses("expenses.txt");
		
		//parse list of expenses into a list of expense maps
		List<Map<Integer, Double>> monthlyExpenses = ExpenseFileParser.parseExpenses(expensesList);
				
		//print list of expense maps for debugging purposes
		System.out.println(monthlyExpenses);
		
		//create instance of expenses class
		Expenses expenses = new Expenses();
				
		//add list of maps to internal list of expense objects
		expenses.addExpenses(monthlyExpenses);	
		
		//get list of expense amounts for 10 (oct)
		List<Double> octMonthlyExpenses = ExpenseManagement.getMonthlyExpenses(10, 
				expenses.getMonthlyExpenses());
		System.out.println("Oct. Expenses: " + octMonthlyExpenses);
		
		System.out.println();
		
		//get list of expense amounts for jan
		List<Double> janMonthlyExpenses = ExpenseManagement.getMonthlyExpenses("jan", 
				expenses.getMonthlyExpenses());
		System.out.println("Jan. Expenses: " + janMonthlyExpenses);
		
		//get total expense amount for jan
		double totalJanMonthlyExpenses = ExpenseManagement.getTotalMonthlyExpenses("jan",
				expenses.getMonthlyExpenses());
		System.out.println("Total: " + totalJanMonthlyExpenses);
				
		System.out.println();
		
		//get list of expense amounts for 2 (feb)
		List<Double> febMonthlyExpenses = ExpenseManagement.getMonthlyExpenses(2,
				expenses.getMonthlyExpenses());
		System.out.println("Feb. Expenses: " + febMonthlyExpenses);
		
		//get total expense amount for feb
		double totalFebMonthlyExpenses = ExpenseManagement.getTotalMonthlyExpenses("feb",
				expenses.getMonthlyExpenses());
		System.out.println("Total: " + totalFebMonthlyExpenses);
		
		System.out.println();
		
		//get highest expense
		Expense mostExpensiveMonth = ExpenseManagement.getMostExpensiveMonth(expenses.getMonthlyExpenses());
		System.out.println("Most Expensive Month: " + mostExpensiveMonth);
	}
}
