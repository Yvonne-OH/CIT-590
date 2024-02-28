from Expense import *


class ExpensesManager(object):
    """A class for managing expenses in a dictionary.
    """

    # We do not have an __init__ function and will call the default constructor

    def get_expense(self, expenses, expense_type):
        """Returns the Expense object for the given expense type in the given expenses dictionary.

        Prints a friendly message and returns None if the expense type doesn't exist.

        (Note: Printing a friendly message means that the program should not raise an error or otherwise terminate.
        Simply tell the user that the requested expense type does not exist and continue the program.

        Also note that None is a specific keyword in Python of NoneType. You should not return a string “None” from
        this method.)
        """

        # TODO insert your code
        if expense_type in expenses: # Check if the expense_type exists in the expenses dictionary
            return expenses[expense_type]
        else:
            # Print a friendly message 
            print(f"The expense type '{expense_type}' does not exist.")
            # Return None
            return None        
        
        #raise NotImplementedError  # remove this line and replace with your code

    def add_expense(self, expenses, expense_type, value):
        """If the expense_type already exists in the given expenses dictionary, add the value to the associated
        Expense object amount.

        Otherwise, create a new entry in the expenses dictionary with the expense_type as the
        key and the value as an Expense object with the given expense_type and value parameters.

        Prints the updated Expense object.

        This method doesn’t return anything.
        """

        # TODO insert your code
        if expense_type in expenses:                        # Check if the expense_type already exists in the dictionary
            expenses[expense_type].amount += value   # Add the value to the existing amount
        else:
            expenses[expense_type] = Expense(expense_type, value) # Create a new Expense object
        
        print(f"Updated Expense: {expense_type} : ${expenses[expense_type].amount:.2f}")
        #raise NotImplementedError  # remove this line and replace with your code

    def deduct_expense(self, expenses, expense_type, value):
        """From the given expenses dictionary, get the Expense object associated with the given expense_type and
        deduct the given value from the amount.

        Raises a RuntimeError if the given value is greater than the existing amount of the Expense object. Note: You
        are not supposed to use try/except to catch the RuntimeError you raised. We expect the method to raise a
        RuntimeError if the value is greater than the existing total of the expense type.

        Prints a friendly message if the expense type doesn't exist from the given expenses dictionary. (Note:
        Printing a friendly message means that the program should not raise an error or otherwise terminate. Simply
        tell the user that the requested expense type does not exist and continue the program.)

        Print the updated Expense object if RuntimeError is not raised.

        This method doesn’t return anything.
        """

        # TODO insert your code
        # Check if the expense_type exists in the dictionary
        if expense_type not in expenses:
            print(f"The expense type '{expense_type}' does not exist.")
            return
        
        expense = expenses[expense_type] # Retrieve the Expense object
      
        if value > expense.amount: # Check if the given value is greater than the existing amount
            raise RuntimeError(f"The given value {value} is greater than the existing amount {expense.amount} for {expense_type}.")
    
        expense.amount -= value # Deduct the value from the Expense object's amount
    
        print(f"Updated Expense: {expense_type} : ${expense.amount:.2f}")
        #raise NotImplementedError  # remove this line and replace with your code

    def update_expense(self, expenses, expense_type, value):
        """From the given expenses dictionary, update the Expense object associated with the given expense_type and
        use the given value.

        Prints a friendly message if the expense type doesn't exist.  Note: Printing a friendly message means that
        the program should not raise an error or otherwise terminate. Simply tell the user that the requested expense
        type does not exist and continue the program.

        Prints the updated Expense object if it exists.

        This method doesn’t return anything.
        """

        # TODO insert your code
        # Check if the expense_type exists in the dictionary
        if expense_type not in expenses:
            print(f"The expense type '{expense_type}' does not exist.")
            return
            
        expenses[expense_type].amount = value # Update the Expense

        print(f"Updated Expense: {expense_type} : ${expenses[expense_type].amount:.2f}")
        #raise NotImplementedError  # remove this line and replace with your code

    def sort_expenses(self, expenses, sorting):
        """Converts the key:value pairs in the given expenses dictionary to a list of tuples containing the expense
        type and amount of the Expense object (Expense.expense_type, Expense.amount) and sorts based on the given
        sorting argument.

        If the sorting argument is the string ‘expense_type’, sorts the list of tuples based on the expense type
        (e.g. ‘rent’) in ascending alphabetical order of the expense_type, e.g. sorted results: ("coffee", 5.0),
        ("food", 5000.0), ("rent", 1000.0)

        Otherwise, if the sorting argument is ‘amount’, sorts the list of tuples based on the total expense amount
        (e.g. 825.0) in descending order of the expense amount, e.g. sorted results: ("food", 5000.0), ("rent",
        1000.0), ("coffee", 5.0)

        Returns the list of sorted tuples. (Note: If the given sorting argument is not an acceptable value
        (e.g. ‘expense_type’ or 'amount'), this method does nothing except print a friendly message and return None.)
        """

        # TODO insert your code
        # Convert dictionary to list of tuples (expense_type, amount)
        expenses_list = [(expense_type, expense.amount) for expense_type, expense in expenses.items()]
    
       
        if sorting == 'expense_type':   # Sort by expense_type in ascending alphabetical order
            sorted_expenses = sorted(expenses_list, key=lambda x: x[0]) 
        elif sorting == 'amount':
            sorted_expenses = sorted(expenses_list, key=lambda x: x[1], reverse=True)  # Sort by amount in descending order
        else:
            print("Sorting argument must be 'expense_type' or 'amount'.")
            return None
    
        return sorted_expenses
        #raise NotImplementedError  # remove this line and replace with your code

    def export_expenses(self, expenses, expense_types, file):
        """Exports the Expense objects associated with the given expense_types from the given expenses dictionary to
        the given file.

        Do not append to the file. If the function is called again and the given file already exists, make sure it
        overwrites what was previously in the file instead of appending to it.

        Iterates over the given expenses dictionary, filters based on the given expense types (a list of strings),
        and exports to a file.  Skips any expense type in the given list of expense types that doesn't exist.

        If the expenses argument is the dictionary {"food": Expense("food", 5000.00), "rent": Expense("rent",
        1000.00), "coffee": Expense("coffee", 5.00), "clothes": Expense("clothes", 58.92)} and the expense_types
        argument is the list of strings [‘coffee', 'clothes', 'rent’], exports a file containing: coffee: 5.00
        clothes: 58.92 rent: 1000.00

        If the expenses argument is the dictionary {"food": Expense("food", 5000.00), "rent": Expense("rent",
        1000.00), "coffee": Expense("coffee", 5.00), "clothes": Expense("clothes", 58.92)} and the expense_types
        argument is the list of strings [‘coffee', 'clothes', 'sports’], exports a file containing: coffee: 5.00
        clothes: 58.92

        Note, the specified expense type 'sports' does not exist in the expenses dictionary, so it is ignored.

        If an item is duplicated in the given expense types, don’t worry about it, just export the data as is. You
        should not deduplicate the expense types.

        Note: Each purchase should be written on its own line in the output file for example if it were the later example
        that we gave you above, the output file would look like the below:
        coffee: 12.40
        clothes: 45.00

        This method doesn’t return anything.
        """

        # TODO insert your code
        with open(file, 'w') as f:                   # Open the file 
            for expense_type in expense_types:       # Iterate over the list of specified expense_types
                if expense_type in expenses:         # Check if the expense_type exists in the expenses dictionary
                    expense = expenses[expense_type] # Retrieve the Expense object for the current expense_type
                    f.write(f"{expense_type}: {expense.amount:.2f}\n")
        #raise NotImplementedError  # remove this line and replace with your code
