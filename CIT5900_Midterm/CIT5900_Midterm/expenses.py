"""
This Python exam will involve implementing a system for managing expenses.  You will
download the skeleton of the program, then implement the functions/methods.  The design of the
program has been set up for you.

In this system, users will be able to add and deduct expenses, update expenses, sort expenses,
and export filtered expenses to a file.  The program will initially load a collection of expenses
from 2 different .txt files (in the same format) and store them as Expense objects in a dictionary.

NOTE(S):
- It is important that you DO NOT edit the expenses.txt file or the expenses_2.txt file.  If you do,
you could fail the automated testing.
- DO NOT change the spacing or remove any blank lines.
- DO NOT copy/paste the text from the expenses.txt file or the expenses_2.txt file into another file.
"""

from ExpensesLoader import *
from ExpensesManager import *


def main():
    # to store dictionary of expenses
    expenses = {}

    # create instance of ExpensesLoader class
    expenses_loader = ExpensesLoader()

    # call import_expenses method in ExpensesLoader class
    # to import expense files and store in dictionary
    expenses_loader.import_expenses(expenses, 'expenses.txt')
    expenses_loader.import_expenses(expenses, 'expenses_2.txt')

    # for testing purposes, uncomment the following line
    print(expenses)

    # create instance of ExpensesManager class
    expenses_manager = ExpensesManager()

    while True:

        # print welcome and options
        print('\nWelcome to the expense management system!  What would you like to do?')
        print('1: Get expense info')
        print('2: Add an expense')
        print('3: Deduct an expense')
        print('4: Update an expense')
        print('5: Sort expenses')
        print('6: Export expenses')
        print('0: Exit the system')

        # get user input
        option_input = input('\n')

        # try and cast to int
        try:
            option = int(option_input)

        # catch ValueError
        except ValueError:
            print("Invalid option.")

        else:

            # check options
            if (option == 1):

                # get expense type and print expense info
                expense_type = input('Expense type? ')

                # call get_expense method in ExpensesManager class
                print(expenses_manager.get_expense(expenses, expense_type))

            elif (option == 2):

                # get expense type
                expense_type = input('Expense type? ')

                # try and get amount to add and cast to float
                try:
                    amount = float(input('Amount to add? '))

                # catch ValueError
                except ValueError:
                    print("Invalid amount.")

                else:
                    # add expense
                    # call add_expense method in ExpensesManager class
                    expenses_manager.add_expense(expenses, expense_type, amount)

            elif (option == 3):

                # get expense type
                expense_type = input('Expense type? ')

                # try and get amount to deduct and cast to float
                try:
                    amount = float(input('Amount to deduct? '))

                # catch ValueError
                except ValueError:
                    print("Invalid amount.")

                else:
                    # deduct expense
                    # call deduct_expense method in ExpensesManager class
                    expenses_manager.deduct_expense(expenses, expense_type, amount)

            elif (option == 4):

                # get expense type
                expense_type = input('Expense type? ')

                # try and get amount to update expense to
                try:
                    amount = float(input('Amount to update expense to? '))

                # catch ValueError
                except ValueError:
                    print("Invalid amount.")

                else:
                    # update expense
                    # call update_expense method in ExpensesManager class
                    expenses_manager.update_expense(expenses, expense_type, amount)

            elif (option == 5):

                # get sort type
                sort_type = input('What type of sort? (\'expense_type\' or \'amount\') ')

                # sort expenses
                # call sort_expenses method in ExpensesManager class
                print(expenses_manager.sort_expenses(expenses, sort_type))

            elif (option == 6):

                # get filename to export to
                file_name = input('Name of file to export to?')

                # get expense types to export
                expense_types = []

                while True:
                    expense_type = input("What expense type you want to export? Input N to quit: ")
                    if expense_type == "N":
                        break

                    expense_types.append(expense_type)

                # export expenses
                # call export_expenses method in ExpensesManager class
                expenses_manager.export_expenses(expenses, expense_types, file_name)

            elif (option == 0):

                # exit expense system
                print('Good bye!')
                break

            # corner case
            else:
                print("Invalid option.")


if __name__ == '__main__':
    main()
