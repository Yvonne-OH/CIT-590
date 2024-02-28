class Expense(object):
    """A class for representing an expense with an expense type and amount.
    """

    def __init__(self, expense_type, amount):
        """Initialize expense_type with given expense_type and amount
        """
        self.expense_type = expense_type

        # initialize amount with given amount
        self.amount = amount

    def add_amount(self, amount):
        """Adds the given amount to the total amount of the expense.
        This method doesn’t return anything.
        """

        # TODO insert your code
        self.amount += amount

    def deduct_amount(self, amount):
        """Deducts the given amount from the total amount of the expense.
        This method doesn’t return anything.
        """

        # TODO insert your code
        self.amount -= amount

    def __str__(self):
        """Returns string representation of expense, aimed at the user.
        Called by str(object) and the built-in functions format() and print()
        to compute an “informal” or nicely printable string representation.
        Returns expense type and amount, rounded to 2 decimal places.
        """

        return self.expense_type + ": " + "{:.2f}".format(self.amount)

    def __repr__(self):
        """Returns string representation of expense, aimed at the programmer.
        Typically used for debugging, to provide an information-rich
        and unambiguous string representation.
        Returns expense type and amount, rounded to 2 decimal places.
        """

        return self.expense_type + ": " + "{:.2f}".format(self.amount)