
"""
o Name: Zhanqian Wu
o Penn ID: 52463471
o Statement of work:
    
▪ 1. "Python's raise: Effectively Raising Exceptions in Your Code" https://realpython.com/python-raise-exception/
▪ 2. "Python String lower() Method" https://www.w3schools.com/python/ref_string_lower.asp
▪ 3. ED Discussion

▪ 4. I worked alone 
"""


# import the random module
# use "winnings = random.randint(2, 10)" to generate a random int from 2 - 10 and store in a variable "winnings"
import random

# unit price of a lottery ticket
constant_lottery_unit_price = 2

# unit price of an apple
constant_apple_unit_price = .99

# unit price of a can of beans
constant_canned_beans_unit_price = 1.58

# unit price of a soda
constant_soda_unit_price = 1.23

# the user has initial $5 for shopping
money = 5

# the user has spent $0 initially
money_spent = 0

# the amounts of lottery tickets, apples, cans of beans, and sodas the user has purchased
lottery_amount, apple_amount, canned_beans_amount, soda_amount = 0, 0, 0, 0

# Print welcome message
print("Welcome to the supermarket!  Here's what we have in stock:")
print(f"- Lottery ticket cost ${constant_lottery_unit_price} each")
print(f"- Apple cost ${constant_apple_unit_price} each")
print(f"- Can of beans cost ${constant_canned_beans_unit_price} each")
print(f"- Sodas cost ${constant_soda_unit_price} each")

# Print available money
print()
print(f"You have ${money} available")

# Ask if they want to purchase a lottery ticket
Need_lottery = input("First, do you want to buy a $2 lottery ticket for a chance at winning $2-$10? (y/n)")

# Process the response for the lottery ticket
# If Customer answer 'y' or 'Y', randomly generate a lottery 0-2
# If lottery number==1, win, else, lose
if Need_lottery.lower() == 'y':
    if money >= constant_lottery_unit_price:
        # Process item and money 
        lottery_amount += 1
        money -= constant_lottery_unit_price
        money_spent += constant_lottery_unit_price
        win_or_lose = random.randint(0, 2)
        if win_or_lose ==1:
            winnings = random.randint(2, 10)
            print(f"Congratulations! You won ${winnings} !")
            money += winnings
        else:
            winnings = 0 
            print("Sorry! You did not win the lottery.")
    else:
        print("Sorry, you don't have enough money to buy a lottery ticket.")
elif  Need_lottery.lower() == 'n':
    print("No lottery tickets were purchased.")
else:
    print("Sorry, Please Enter Y/N, No lottery tickets were purchased.")

#__________________________________________________________________
# Print Current Available Money 
print()
print(f"You have ${money} available.")

Need_apples = input("Do you want to buy apple(s)? (y/n)")
if Need_apples.lower() == 'y':
    try:
        # Ask how many apples the user wants to buy
        apple_num = int(input("How many apples would you like to buy? "))
        if apple_num < 0:
            print("Quantity cannot be negative! No apples purchased.")
        else:
            total_apple_cost = apple_num * constant_apple_unit_price
    
            # Check if the user has enough money
            if total_apple_cost <= money:
                print(f"The user wants to buy {apple_num} apple(s). This will cost ${total_apple_cost:.2f}.")
                apple_amount += apple_num
                money -= total_apple_cost
                money_spent += total_apple_cost
            else:
                print("Not enough money! No apples purchased.")
    except ValueError:
        # Handle non-integer input
        print("Please enter a number (int)")
        
elif  Need_apples.lower() == 'n':
    print("No apples were purchased.")
else:
    print("Sorry, Please Enter Y/N, No apples were purchased.")

#__________________________________________________________________
# Print Current Available Money 
print()
print(f"You have ${money} available.")

Need_beans = input("Do you want to buy can of bean(s)? (y/n)")
if Need_beans.lower() == 'y':
    try:
        # Ask how many can of beans the user wants to buy
        beans_num = int(input("How many can of beans would you like to buy? "))
        if beans_num < 0:
            print("Quantity cannot be negative! No can(s) of beans purchased.")
        else:
            total_beans_cost = beans_num * constant_canned_beans_unit_price 
            # Check if the user has enough money
            if total_beans_cost <= money:
                print(f"The user wants to buy {beans_num} can of beans. This will cost ${total_beans_cost:.2f}.")
                apple_amount += apple_num
                money -= total_beans_cost
                money_spent += total_beans_cost
            else:
                print("Not enough money! No can of beans purchased.")
                
    except ValueError:
        # Handle non-integer input
        print("Please enter a number (int)")
        
elif   Need_beans.lower() == 'n':
    print(" No can of beans purchased.")
else:
    print("Sorry, Please Enter Y/N,  No can of beans purchased.")

#__________________________________________________________________
# Print Current Available Money 
print()
print(f"You have ${money} available.")

Need_sodas = input("Do you want to buy soda(s)? (y/n)")
if Need_sodas.lower() == 'y':
    try:
        # Ask how many can of beans the user wants to buy
        sodas_num = int(input("How many soda(s) would you like to buy? "))
        if sodas_num < 0:
            print("Quantity cannot be negative! No can(s) of sodas purchased.")
        else:
            total_sodas_cost =sodas_num * constant_soda_unit_price 
            # Check if the user has enough money
            if total_sodas_cost <= money:
                print(f"The user wants to buy {sodas_num} soda(s). This will cost ${total_sodas_cost:.2f}.")
                soda_amount += sodas_num
                money -= total_sodas_cost
                money_spent += total_sodas_cost
            else:
                print("Not enough money! No soda(s) purchased.")
    except ValueError:
        # Handle non-integer input
        print("Please enter a number (int)")
        
elif   Need_sodas.lower() == 'n':
    print(" No soda(s) purchased.")
else:
    print("Sorry, Please Enter Y/N,  No soda(s) purchased.")


#__________________________________________________________________
print()
print("_"*20,"CIT 590","_"*20)
print(f"Money left: ${money:.2f}")
print(f"Lottery ticket(s) purchased: {lottery_amount}")
print(f"Lottery winnings: ${winnings if lottery_amount > 0 else 0}")
print(f"Apple(s) purchased: {apple_amount}")
print(f"Can(s) of beans purchased: {canned_beans_amount}")
print(f"Soda(s) purchased: {soda_amount}")
print("Good bye!")









