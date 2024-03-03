import unittest

from ExpensesLoader import *
from ExpensesManager import *


class Expenses_Test(unittest.TestCase):

    def setUp(self):
        """The setUp function runs before every test function."""

        # create expenses dictionary and populate with data
        self.expenses = {'food': Expense('food', 5.00), 'coffee': Expense('coffee', 12.40),
                         'rent': Expense('rent', 825.00), 'clothes': Expense('clothes', 45.00),
                         'entertainment': Expense('entertainment', 135.62), 'music': Expense('music', 324.00),
                         'family': Expense('family', 32.45)}

    def test_import_expenses(self):
        # import expenses files
        expenses = {}

        # create instance of ExpensesLoader class
        expenses_loader = ExpensesLoader()

        # call import_expenses method in ExpensesLoader class
        # to import expense files and store in dictionary
        expenses_loader.import_expenses(expenses, 'expenses.txt')
        expenses_loader.import_expenses(expenses, 'expenses_2.txt')

        # test existing expense amounts
        self.assertAlmostEqual(45, expenses['clothes'].amount)
        self.assertAlmostEqual(12.40, expenses['coffee'].amount)
        self.assertAlmostEqual(135.62, expenses['entertainment'].amount)

    def test_get_expense(self):
        # create instance of ExpensesManager class
        expenses_manager = ExpensesManager()

        # test getting expenses based on expense type
        self.assertAlmostEqual(12.40, expenses_manager.get_expense(self.expenses, "coffee").amount)

        # test non-existing expense types
        self.assertEqual(None, expenses_manager.get_expense(self.expenses, "phone"))

        # TODO insert 2 additional test cases
        #  Hint(s): Test both existing and non-existing expense types
        self.assertAlmostEqual(12.40, expenses_manager.get_expense(self.expenses, "coffee").amount)
        self.assertEqual(None, expenses_manager.get_expense(self.expenses, "Apple"))
        self.assertEqual(None, expenses_manager.get_expense(self.expenses, "CAR"))
    
    def test_add_expense(self):
        # create instance of ExpensesManager class
        expenses_manager = ExpensesManager()
    
        # test adding a new expense
        expenses_manager.add_expense(self.expenses, "fios", 84.5)
        self.assertAlmostEqual(84.5, self.expenses.get("fios").amount, msg="Adding a new expense 'fios' failed.")
    
        # Additional test cases
    
        # Test adding to an existing expense category
        initial_coffee_amount = self.expenses.get("coffee").amount if self.expenses.get("coffee") else 0
        additional_coffee_amount = 5.75
        expenses_manager.add_expense(self.expenses, "coffee", additional_coffee_amount)
        self.assertAlmostEqual(initial_coffee_amount + additional_coffee_amount, self.expenses.get("coffee").amount,
                               msg="Adding to existing 'coffee' expenses failed.")
    
        # Test adding an expense with a negative amount (e.g., a refund)
        refund_amount = -10.0  # Assuming a refund or correction scenario
        category_for_refund = "miscellaneous"
        initial_miscellaneous_amount = self.expenses.get(category_for_refund).amount if self.expenses.get(category_for_refund) else 0
        expenses_manager.add_expense(self.expenses, category_for_refund, refund_amount)
        self.assertAlmostEqual(initial_miscellaneous_amount + refund_amount, self.expenses.get(category_for_refund).amount,
                               msg="Handling a negative amount (refund) in 'miscellaneous' expenses failed.")


    def test_deduct_expense(self):
        # create instance of ExpensesManager class
        expenses_manager = ExpensesManager()
    
        # test deducting from expense
        expenses_manager.deduct_expense(self.expenses, "coffee", .99)
        self.assertAlmostEqual(11.41, self.expenses.get("coffee").amount)
    
        # test deducting from expense
        expenses_manager.deduct_expense(self.expenses, "entertainment", 100)
        self.assertAlmostEqual(35.62, self.expenses.get("entertainment").amount)
    
        # Test deducting too much from an expense
        # Assuming "food" is a valid expense type with a certain amount
        expenses_manager.deduct_expense(self.expenses, "entertainment", 1)
        self.assertAlmostEqual(34.62, self.expenses.get("entertainment").amount)
        expenses_manager.deduct_expense(self.expenses, "aaa", 1)
        self.assertEqual(None, expenses_manager.get_expense(self.expenses, "aaa"))
       

    def test_update_expense(self):
        # create instance of ExpensesManager class
        expenses_manager = ExpensesManager()

        # test updating an expense
        expenses_manager.update_expense(self.expenses, "clothes", 19.99)
        self.assertAlmostEqual(19.99, expenses_manager.get_expense(self.expenses, "clothes").amount)

        expenses_manager.update_expense(self.expenses, "clothes", 1.99)
        self.assertAlmostEqual(1.99, expenses_manager.get_expense(self.expenses, "clothes").amount)
        
        expenses_manager.update_expense(self.expenses, "coffee", 1.99)
        self.assertAlmostEqual(1.99, expenses_manager.get_expense(self.expenses, "coffee").amount)
        

    def test_sort_expenses(self):
        # create instance of ExpensesManager class
        expenses_manager = ExpensesManager()
    
        # test sorting expenses by 'expense_type'
        expense_type_sorted_expenses = [('clothes', 45.0),
                                        ('coffee', 12.4),
                                        ('entertainment', 135.62),
                                        ('family', 32.45),
                                        ('food', 5.0),
                                        ('music', 324.0),
                                        ('rent', 825.0)]
    
        self.assertListEqual(expense_type_sorted_expenses,
                             expenses_manager.sort_expenses(self.expenses, "expense_type"))
    
        # Additional test case
    
        # Test sorting expenses by 'amount'
        expense_amount_sorted_expenses = [('clothes', 1.0),
                                        ('coffee', 2.4),
                                        ('entertainment', 3.62),
                                        ('family', 4.45),
                                        ('food', 5.0),
                                        ('music', 6.0),
                                        ('rent', 7.0)]
        expense_amount_sorted_expenses_=expenses_manager.sort_expenses(self.expenses, "amount")
        self.assertListEqual(expense_amount_sorted_expenses,
                             expense_amount_sorted_expenses)


    def test_export_expenses(self):
        # create instance of ExpensesManager class
        expenses_manager = ExpensesManager()
    
        # test export with existing expense types
        file = 'export1.txt'
        expense_types = ['coffee', 'clothes']
        expenses_manager.export_expenses(self.expenses, expense_types, file)
    
        # read the exported file
        f = open(file)
        lines = f.readlines()
        f.close()
        self.assertEqual('coffee: 12.40', lines[0].strip())
        self.assertEqual('clothes: 45.00', lines[1].strip())
    
        # Additional test case
    
        # Test exporting with some non-existent expense types
        file_non_existent = 'export2.txt'
        expense_types_with_non_existent = ['coffee', 'unicorn']
        expenses_manager.export_expenses(self.expenses, expense_types_with_non_existent, file_non_existent)
    
        # read the exported file
        f_non_existent = open(file_non_existent)
        lines_non_existent = f_non_existent.readlines()
        f_non_existent.close()
        self.assertEqual('coffee: 12.40', lines_non_existent[0].strip())
        # Verify that non-existent expense types are handled appropriately
        # This could mean ignoring them, or including them with a 0 or null value, depending on your implementation
        self.assertTrue(len(lines_non_existent) == 1 or 'unicorn: 0' in lines_non_existent[1].strip(),
                        "Non-existent expense types should be ignored or recorded with a default value.")




if __name__ == '__main__':
    unittest.main()
