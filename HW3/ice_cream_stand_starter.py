"""
STARTER CODE
Homework 3: Ice Cream Stand
Topics Covered:
- Lists (append, pop)
- For and while loops
- Getting user inputs
- Validating user inputs
- Functions and helper functions
- Formatted Strings
"""

# TODO: Students, fill out statement of work header
# Student Name in Canvas: Zhanqian Wu
# Penn ID: 52463471
# Did you do this homework on your own (yes / no): yes
# Resources used outside course materials:
    
# 1.https://www.w3schools.com/python/ref_random_randint.asp
# 2.https://www.w3schools.com/python/ref_random_choice.asp
# 3.https://www.w3schools.com/python/python_string_formatting.asp

# import statements
from random import randint, choice


def print_welcome_and_menu(list_of_flavors, list_of_sizes, list_of_prices):
    """
    Prints the following:
    1. Welcome message (Must contain word 'welcome')
    2. Message on what flavors are available in the ice cream store.
        Hint: Loop through the list_of_flavors
    3. Message on how much each size cost.
        Hint: Loop through the list_of_sizes, list_of_prices
        Format should be: Our {size} ice cream is ${price}.
    """
    # TODO: Write your code here
    # Welcome message
    print("Welcome to Penn's Student Run Ice Cream Stand!")
    print("_"*50)
    
    # Printing available flavors
    print("\nOur current flavors for today are:")
    for flavor in list_of_flavors:
        print(flavor)
    
    print("_"*50)
    
    for size, price in zip(list_of_sizes, list_of_prices):
        print(f"Our {size} ice cream is ${price:.2f}")
    
    # TODO: Remove the pass statement once you have your code written


def get_order_qty(customer_name):
    """
    Ask the customer how many orders of ice cream they want.
    Valid order quantity should be an integer 1-5 inclusive. If outside the range or non-int, re-prompt.
    Hint: When asking for user input, cast it to an integer. If the input cannot be cast-ed to an integer, re-prompt.
    "2.55", "abc", "   ", are a few examples of what should all re-prompt the user.
    Returns: How many orders of ice cream the customer wants.
    """
    order_qty = 0
    # TODO: Write your code here
    while True:
        try:
            # Ask the customer for their order quantity
            order_qty = input(f"Welcome {customer_name}! How many ice creams will you be ordering (1 to 5)? ")
    
            # Convert the input to an integer
            order_qty = int(order_qty)
    
            # Check if the order quantity is within the valid range
            if 1 <= order_qty <= 5:
                return order_qty
            else:
                print("Please enter a number between 1 and 5.")
        except ValueError:
            # Re-prompt if input is not an integer
            print("Invalid input. Please enter a whole integer.")
            
    return order_qty


def get_ice_cream_flavor(ice_cream_flavors):
    """
    Ask the customer 'Which flavor would you like (v/c/s)? '
    Then, processes and cleans the input and returns the equivalent flavor from ice_cream_flavors list.
    Hint:   Use the indices set in the main function for the flavors.
            Call the get_first_letter_of_user_input function to get and process inputs.
            Note: Only the first letter of the input will be considered so an input of 'Cookies and Cream'
            will be considered as 'c' which corresponds to 'Chocolate'.
            Ask again if it is not a valid flavor.
    Returns: String of ice cream flavor picked (e.g "Vanilla")
    """
    flavor_picked = ""
    # TODO: Write your code here
    flavor_dict = {'v': 'Vanilla', 'c': 'Chocolate', 's': 'Strawberry'}
    
    while True:
        # Ask the customer for their flavor choice
        choice = get_first_letter_of_user_input("Which flavor would you like (v/c/s)? ").lower()

        # Check if the choice corresponds to a valid flavor
        if choice in flavor_dict:
            flavor_picked = flavor_dict[choice]
            break
        else:
            print("Invalid choice. Please choose Vanilla, Chocolate, or Strawberry.")

    return flavor_picked


def get_ice_cream_size(ice_cream_sizes):
    """
    Ask the customer 'Which size would you like (s/m/l)? '
    Then, processes and cleans the input and returns the equivalent size from ice_cream_sizes list.
    Hint:   Use the indices set in the main function for the sizes.
            Call the get_first_letter_of_user_input function to get and process inputs.
            Note: Only the first letter of the input will be considered so an input of 'Super Large'
            will be considered as 's' which corresponds to 'Small'.
            Ask again if it is not a valid size.
    Returns: String of Size picked (e.g "Small")
    """
    size_picked = ""
    # TODO: Write your code here
    size_dict = {'s': 'Small', 'm': 'Medium', 'l': 'Large'}
    
    while True:
        # Ask the customer for their size choice
        choice = get_first_letter_of_user_input("Which size would you like (s/m/l)? ").lower()

        # Check if the choice corresponds to a valid size
        if choice in size_dict:
            size_picked = size_dict[choice]
            break
        else:
            print("Invalid choice. Please choose Small, Medium, or Large.")
            
    return size_picked


def get_ice_cream_order_price(ice_cream_size, ice_cream_prices, ice_cream_sizes):
    """
    Hint:   Use the indices set in the main function for the prices of Small, Medium and Large.
    Returns: The equivalent price of an ice cream size. Example: Returns 4.99 if ice_cream_size is 'Small'
    """
    # TODO: Write your code here
    size_index = ice_cream_sizes.index(ice_cream_size)
    return ice_cream_prices[size_index]


def take_customer_order(customer_name, ice_cream_flavors, ice_cream_sizes, ice_cream_prices):
    """
    This function runs when a customer reaches the front of the queue. It should print
    the current customer's name being served, and take their order(s).
    If the customer can pay for their order, returns the amount of revenue from the sale.
    If the customer cancels their order, returns 0.
    Hint: Use other helper functions we required you to write whenever needed here.
    Returns: Amount of Revenue from the sale with customer
    """

    total_bill = 0

    # TODO: Print a message "Now serving customer: X" where X is the current customer's name
    print("_"*50)
    print(f"Now serving customer: {customer_name}")

    # TODO: Call the get_order_qty and save the value to order_qty
    order_qty = get_order_qty(customer_name)

    # TODO: For Each order you need to get a flavor, and size
    for order in range(order_qty):
        print("Order No.:", order + 1)
        # TODO: Write code to get the ice cream flavor for this order
        flavor=get_ice_cream_flavor(ice_cream_flavors)
        # TODO: Write code to get the ice cream size for this order
        ice_cream_size=get_ice_cream_size(ice_cream_sizes)
        # TODO: Write code to get the price for this order
        price=get_ice_cream_order_price(ice_cream_size, ice_cream_prices, ice_cream_sizes)
        # TODO: Update the total_bill
        total_bill += price
        # TODO: Print the details for this order
        #   Hint: See https://www.w3schools.com/python/python_string_formatting.asp for string formatting examples on rounding to 2 decimal places
        print(f"You ordered a {ice_cream_size} {flavor} for ${price:.2f}")
        # TODO: Remove the pass statement once you have your code written

    # TODO: Print the customer's total_bill
    print(f"Your total bill is: ${total_bill:.2f}")
    print("_"*50)

    # TODO: Once orders are all taken, the customer should be asked if they still want to Pay or Cancel
    #  "Would you like to pay or cancel the order (p/c)? "
    #   Hint: Use the get_first_letter_of_user_input() Re-prompt if answer does not start with 'p' or 'c'
    while True:
        # Prompt the user for their decision
        decision = get_first_letter_of_user_input("Would you like to pay or cancel the order (p/c)? ").lower()

        # Check the decision
        if decision == 'p':
            break
        elif decision == 'c':
            total_bill=0
            break
        else:
            print("Invalid input. Please enter 'p' to pay or 'c' to cancel.")

    return total_bill


def get_first_letter_of_user_input(question):
    """
    Takes in a string as its argument, to be used as the question you want the user to be asked.
    Gets input from the user, removes whitespace and makes all letters lowercase
    Hint: Use the strip() and lower() functions
    Returns: The first letter of the input the user provides. Ask again if the input is empty.
    """

    first_letter = ""
    # TODO: Write your code here
    while True:
        # Get user input
        user_input = input(question).strip().lower()

        # Check if the input is not empty
        if user_input:
            first_letter = user_input[0]  # Store the first letter
            break  # Break the loop when a valid input is received
        else:
            print("Input cannot be empty. Please try again.")
            
    return first_letter


def are_all_customers_served(customer_queue_length):
    """
    If there are no customers in the queue, returns True, and all customers have been served.
    Otherwise, returns False.
    Returns: True or False
    """
    # TODO: Write your code here
    return customer_queue_length == 0


def print_current_status(customers_served, tracking_revenue):
    """
    Prints a message of how many customers have been served and the total sales of the ice cream stand.
    Hint: See https://www.w3schools.com/python/python_string_formatting.asp for string formatting examples on rounding to 2 decimal places
    No Return, only print statements
    """
    print(f"Customers served: {customers_served}")
    print(f"Total sales: ${tracking_revenue:.2f}")


def print_sales_summary(customers_served, tracking_revenue):
    """
    Takes in the arguments customers_served and tracking_revenue. Prints both
    arguments as strings to let the user know what those values are.
    Output should look something like:
        Total customers served: 3
        Total sales           : $xx.xx
    Hint: See https://www.w3schools.com/python/python_string_formatting.asp for string formatting examples on rounding to 2 decimal places
    No Return, only print statements
    """
    # TODO: Write your code here
    print(f"We have now served {customers_served} customer(s), and received ${tracking_revenue:.2f} in revenue")
    # TODO: Remove the pass statement once you have your code written


def random_queue_length():
    """
    Takes no arguments.
    Uses the imported randint function to generate a random integer between 2 and 5 inclusive.
    Hint: See https://www.w3schools.com/python/ref_random_randint.asp
    Returns: The resulting random integer.
    """
    return randint(2, 5)


def main():
    """
    Lists of available flavors, sizes and prices. DO NOT CHANGE.
    For sizes and prices, we will use the following convention:
    Index 0 for Small
    Index 1 for Medium
    Index 2 for Large
    """
    ice_cream_flavors = ['Vanilla', 'Chocolate', 'Strawberry']
    ice_cream_sizes = ['Small', 'Medium', 'Large']
    ice_cream_prices = [4.99, 7.49, 8.49]

    #List of names of possible customers
    customer_names = ["Alice", "Bob", "Charlie", "Dan", "Eve", "Frank", "Grace", "Heidi", "Ivan", "Judy"]

    program_running = True
    while program_running:
        # set shop to open
        input('Press any key to open the shop! ')
        queue_is_open = True

        # TODO: Call the print_welcome_and_menu function with the parameters in the following order -
        #  ice_cream_flavors, ice_cream_sizes, ice_cream_prices
        print_welcome_and_menu(ice_cream_flavors, ice_cream_sizes, ice_cream_prices)


        # set initial values
        tracking_revenue = 0

        # will hold the list of names of the customers in the queue
        customers_in_queue = []
        customers_served = 0

        # TODO: Call the random_queue_length function and save the result to num_of_customers_in_queue
        num_of_customers_in_queue = random_queue_length()

        # TODO: Print how many customers are in the queue
        print()
        print(f"Num of customers in queue: {num_of_customers_in_queue}"  )


        # TODO: Call the imported choice function to generate a random name from customer_names.
        #   Then, append each name to the end of the customers_in_queue list.
        #   The total number of customer names added should be equal to num_of_customers_in_queue
        #   Hint: See https://www.w3schools.com/python/ref_random_choice.asp
        #   Note: It is OK to have duplicate names in the queue.
        for i in range(num_of_customers_in_queue):
            random_name = choice(customer_names) # Select a random customer name
            customers_in_queue.append(random_name) # Append the name to the queue
        

        while queue_is_open:
            # TODO: Extract the first customer (index 0) from the customers_in_queue and save it to
            #  the current_customer_name variable.
            #  After extraction, the customer should now be removed from the customers_in_queue list.
            #  Hint: Use the pop function with an index argument
            current_customer_name = customers_in_queue.pop(0)
            
            print("Current customer being served:", current_customer_name)

            # TODO: Take a customer at the window and update the revenue by calling the take_customer_order function
            tracking_revenue += take_customer_order(current_customer_name, ice_cream_flavors, ice_cream_sizes, ice_cream_prices)

            # TODO: Update the customers_served variable
            customers_served += 1
            # TODO: Call the print_current_status
            print_current_status(customers_served, tracking_revenue)


            # TODO: Call the are_all_customers_served(customer_queue_length) function to check if there are any more
            #  customers in the queue.
            #  If False, continue the loop.
            #  If True, call the print_sales_summary(customers_served, tracking_revenue) and close the queue
            # TODO: Remove the pass statement once you have your code written
            if (are_all_customers_served(len(customers_in_queue))):
                print_sales_summary(customers_served, tracking_revenue)
                break
            else:
                pass


        # TODO: Ask if you want to open the ice cream stand again "Do you want to open again (y/n)? "
        #  Hint: Use the get_first_letter_of_user_input function
        #  Update the program_running variable if you get a valid answer either 'y' or 'n'
        #  Otherwise, re-prompt until a valid answer is given
        while True:
           answer = get_first_letter_of_user_input("Do you want to open again (y/n)? ")
           if answer in ['y', 'n']:
               break
           else:
               print("Invalid answer. Please respond with 'y' for yes or 'n' for no.")
               
        if answer == "n":
            break
        else:
            pass

if __name__ == '__main__':
    main()