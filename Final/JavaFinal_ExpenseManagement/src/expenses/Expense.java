/**
 * Zhanqian Wu 52463471
 * zhanqian@seas.upenn.edu
 */


package expenses;

/**
 * Represents a single expense for a particular month.
 * @author lbrandon
 */
public class Expense {

	/**
	 * Number of month for expense.
	 */
	private int month;
	
	/**
	 * Amount of expense.
	 */
	private double amount;
	
	/**
	 * Creates Expense with given month number and amount.
	 * @param month for expense
	 * @param amount for expense
	 */
	public Expense(int month, double amount) {
		this.month = month;
		this.amount = amount;
	}
	
	/**
	 * Get month of expense.
	 * @return month
	 */
	public int getMonth() {
		return this.month;
	}
	
	/**
	 * Get amount of expense.
	 * @return amount
	 */
	public double getAmount() {
		return this.amount;
	}
	
	/**
	 * Returns the month number and amount for expense.
	 */
	@Override 
	public String toString() {
		return this.month + " : " + this.amount;
	}
	
	/**
	 * Compares two Expense objects for equality, based on the months and amounts.
	 * If the month and amount of one Expense object is equal to 
	 * the month and amount of the other Expense object, 
	 * the two Expense objects are equal.
	 */
	@Override 
	public boolean equals(Object o) {
		
		// TODO Implement method
		// Hint: Cast given Object o to Expense object
		// Compare month and amount of this Expense to other casted Expense
		
		if (this == o) return true; // Check for reference equality
        if (o == null || getClass() != o.getClass()) return false; // Check for null and ensure exact class match

        Expense expense = (Expense) o; // Cast the Object to Expense

        return month == expense.month && Double.compare(expense.amount, amount) == 0; // Check for equality of month and amount
	}
}