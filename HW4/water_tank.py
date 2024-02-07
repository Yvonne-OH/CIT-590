# TODO: Students, fill out statement of work header
# Student Name in Canvas: Zhanqian Wu
# Penn ID: 52463471
# Did you do this homework on your own (yes / no): yes
# Resources used outside course materials:
    
# 1.https://www.w3schools.com/python/ref_random_randint.asp
# 2.https://www.w3schools.com/python/ref_random_choice.asp
# 3.https://www.w3schools.com/python/python_string_formatting.asp


# import statements
from random import shuffle

# TODO: Write the functions as described in the instructions
def get_user_input(question):
    
    """  
    ● Prompt the user with the given question and process the input.
    ● Return the post-processed user input.
    """
    while True:
        user_input = input(question).strip()  # Remove leading and trailing whitespaces.
        if len(user_input) == 0: # If the lengthafter removing whitespaces is0, reprompt.
            continue         
        
        if user_input.isdigit(): #if the input is a number return an integer
            return int(user_input)
        
        if user_input.upper()=="SOH" or user_input.upper()=="DMT" or user_input.upper()=="DOT":
            # power card ---> return an uppercase string
            return user_input.upper()
        
        # Return any other input as a lowercase string
        return user_input.lower()

def setup_water_cards():
    """
    Create a shuffled list of water cards 
    """
    water_cards = [1]*30+[5]*15+[10]*8
    shuffle(water_cards)
    return  water_cards

def setup_power_cards():
    """
    Create power cards 
    """
    power_cards = ["SOH"]*10 + ["DOT"]*2 + ["DMT"]*3
    shuffle(power_cards)
    return power_cards

def setup_cards():
    """
    Return a 2-tuple containing 
    the water cards pile and the power cards pile
    """
    return (setup_water_cards(),setup_power_cards())

def get_card_from_pile(pile, index):
    """
    ● Removes the entry at the specified index 
    of the given pile and modifies the pile by reference.
    
    ● Returns the entry at the specified index.
    """
    
    return pile.pop(index)

def arrange_cards(cards_list):
    """
    ● Arrange the players cards
    ● The first three indices are water cards, sorted in ascending order.
    ● The last two indices are power cards, sorted in alphabetical order.
    """
    # Separate water and power cards
    water_cards = sorted([card for card in cards_list if isinstance(card, int)])
    power_cards = sorted([card for card in cards_list if isinstance(card, str)])
    
    if len(water_cards) == 3 and len(power_cards) == 2:    
        cards_list[:3] = water_cards # Arrange the first threewater cards
        cards_list[3:5] = power_cards # Arrange the last two power cards
    else:
        print("Error: Please check the card list")

def deal_cards(water_cards_pile, power_cards_pile):
    # Initialize player cards
    player_1_cards = []
    player_2_cards = []
    
    # Offer 3 water cards to each player
    for i in range(3):
        player_1_cards.append(water_cards_pile.pop(0))
        player_2_cards.append(water_cards_pile.pop(0))
    
    # Offer 2 power cards to each player
    for _ in range(2):
        player_1_cards.append(power_cards_pile.pop(0))
        player_2_cards.append(power_cards_pile.pop(0))
    
    # Arrange cards
    arrange_cards(player_1_cards)
    arrange_cards(player_2_cards)
    
    return (player_1_cards, player_2_cards)

def apply_overflow(tank_level):
    """
    ● If necessary, apply the overflow rule 
    ● remaining water = maximum fill value - overflow

    ● Return the tank level. 
    ● If no overflow , return starting tank level.
    """
    
    maximum_fill_value=80
    if tank_level > maximum_fill_value:
        # Calculate the remaining water
        tank_level = maximum_fill_value - (tank_level - maximum_fill_value)
        
    return tank_level

def use_card(player_tank, card_to_use, player_cards, opponent_tank):
    """
    Get that card from the player’s hand, 
    update the tank level based on the card that was used. 
    after using the card, the player_cards size will only be 4 cards
    """
    # Remove the card from the player's hand
    if card_to_use in player_cards:
        player_cards.remove(card_to_use)
    else:
        return (player_tank, opponent_tank)  # Card not found
        print("Card not found")
    
    if isinstance(card_to_use, int):
        player_tank += card_to_use
        # Apply the effect of power card
    else:
        if card_to_use == "SOH":
            # Steal half of the opponent's tank 
            stolen_amount = opponent_tank // 2
            player_tank += stolen_amount
            opponent_tank -= stolen_amount
        elif card_to_use == "DOT":
            # Drain the opponent's tank
            opponent_tank = 0
        elif card_to_use == "DMT":
            # Double the player's tank value
            player_tank *= 2
    
    # Apply overflow to the player's tank
    player_tank = apply_overflow(player_tank)
    opponent_tank = apply_overflow(opponent_tank)
    
    # Return the updated tank levels
    return (player_tank, opponent_tank)

def discard_card(card_to_discard, player_cards, water_cards_pile, power_cards_pile):
    """
    ● Discard the given card from the player’s hand 
    and return it to the bottom of the appropriate pile. 
    ● after calling this function, the player_cards size will only be 4 cards.
    ● This function does not return anything
    """
    # Check if the card is in the player's hand and remove it
    if card_to_discard in player_cards:
        player_cards.remove(card_to_discard)
        # Determine the type of the card and add it to the bottom of the appropriate pile
        if isinstance(card_to_discard, int):
            water_cards_pile.append(card_to_discard)
        else:  
            power_cards_pile.append(card_to_discard)
    else:
        print("Card not found")
        
def filled_tank(tank_level):
    """
    Determine if the tank level is between 
    the maximum and minimum fill values (inclusive).
    """
    return 75 <= tank_level <= 80
        
def check_pile(pile, pile_type):
    """
    ● Checks if the given pile is empty.
      If so, call the pile’s setup function to replenish the pile.
    ● pile_type is a string (“water” or “power”)
    """
    # Check if the pile is empty
    if not pile:
        # Determine the pile type and call the appropriate setup function
        if pile_type == "water":
            pile.extend(setup_water_cards())
        elif pile_type == "power":
            pile.extend(setup_power_cards())
        else:
            print("Invalid type")

def human_play(human_tank, human_cards, water_cards_pile, power_cards_pile, opponent_tank):
    print("=== Human Player's turn ===")
    print(f"Your water level is at: {human_tank}")
    print(f"Computer's water level is at: {opponent_tank}")
    print(f"Your cards are: {human_cards}")
    
    
    while True:
        action = get_user_input("Do you want to use a card or discard a card? (u / d): ")
        if action=="u" or action=="d" :
            break
        
    if action == 'u':
        # using a card
        while True:
            card_to_use = get_user_input("Which card do you want to use?: ")
            print(f"Using card: {card_to_use}")
            if card_to_use in human_cards:
                human_tank,opponent_tank=use_card(human_tank, card_to_use, human_cards, opponent_tank)
                
                # draw a new card
                card_type = 'water' if isinstance(card_to_use, int) else 'power'
                print(card_type)
                if card_type == 'water':
                    new_card=water_cards_pile.pop(0)
                else:  # 'power'
                    new_card=power_cards_pile.pop(0)
                human_cards.append(new_card)
                
                arrange_cards(human_cards)
                
                break
            else: 
                print ("Invaild Card")
    elif action == 'd':
        # discarding a card
        while True:
            card_to_discard = get_user_input("Which card do you want to discard?: ")
            print(f"Discarding card: {card_to_discard}")
            if card_to_discard in human_cards:
                discard_card(card_to_discard, human_cards, water_cards_pile, power_cards_pile)
                
                # draw a new card
                card_type = 'water' if isinstance(card_to_discard, int) else 'power'
                if card_type == 'water':
                    new_card=water_cards_pile.pop(0)
                else:  # 'power'
                    new_card=power_cards_pile.pop(0)
                    
                human_cards.append(new_card)
            
                arrange_cards(human_cards)
                
                break
            else: 
                print ("Invaild Card")

    
    print(f"Your water level is now at: {human_tank}")
    print(f"Computer's water level is now at: {opponent_tank}")
    print(f"Your cards are now: {human_cards}")
    
    return (human_tank, opponent_tank)

def computer_play(computer_tank, computer_cards, water_cards_pile, power_cards_pile, opponent_tank):
    
    """
    Use Power Cards First: Prioritize using power cards based on the opponent's tank level and the computer's tank level, as well as the potential for overflow.
    
    Use "SOH" if the opponent's tank level is greater than 20 and using it won't cause the computer's tank to overflow.
    Use "DOT" if the opponent's tank level is greater than 40.
    Use "DMT" if the computer's tank level is greater than 20 and using it won't cause overflow.
    
    Choose Water Cards: If no power card is used or conditions are not met, select a water card that brings the computer's tank level as close as possible to but not exceeding the maximum tank level.
    
    Discard if Necessary: If all choices lead to overflow, choose a water card to discard.    
    """
    print("=== Computer Player's turn ===")
    print(f"Computer's water level is at: {computer_tank}")
    print(f"Your water level is at: {opponent_tank}")
    
    action_taken = False
    card_used = None
    card_to_discard = None
    MAX_TANK_LEVEL=80
    

    # Strategy for using power cards
    if 'SOH' in computer_cards and opponent_tank > 20 and (computer_tank + opponent_tank // 2 <= MAX_TANK_LEVEL):
        card_used = 'SOH'
        computer_tank,opponent_tank=use_card(computer_tank, card_used, computer_cards, opponent_tank)
    elif 'DOT' in computer_cards and opponent_tank > 40:
        card_used = 'DOT'
        computer_tank,opponent_tank=use_card(computer_tank, card_used, computer_cards, opponent_tank)
    elif 'DMT' in computer_cards and computer_tank > 20 and (computer_tank * 2 <= MAX_TANK_LEVEL):
        card_used = 'DMT'
        computer_tank,opponent_tank=use_card(computer_tank, card_used, computer_cards, opponent_tank)

    # If no power card was used or conditions weren't met, try using a water card
    if not card_used:
        best_card = None
        best_diff = MAX_TANK_LEVEL
        for card in computer_cards:
            if isinstance(card, int):
                new_level = computer_tank + card
                if new_level <= MAX_TANK_LEVEL and (MAX_TANK_LEVEL - new_level) < best_diff:
                    best_diff = MAX_TANK_LEVEL - new_level
                    best_card = card
        if best_card:
            card_used = best_card
            computer_tank,opponent_tank=use_card(computer_tank, card_used, computer_cards, opponent_tank)
        else:  # If all water cards lead to overflow, choose to discard
            for card in computer_cards:
                if isinstance(card, int):
                    card_to_discard = card  # Choose a water card to discard
                    discard_card(card_to_discard, computer_cards, water_cards_pile, power_cards_pile)
                    action_taken = 'discard'
                    break

    if card_used:
        action_taken = 'use'
        card_type = 'water' if isinstance(card_used, int) else 'power'
        if card_type == 'water':
            new_card=water_cards_pile.pop(0)
        else:  # 'power'
            new_card=power_cards_pile.pop(0)
        computer_cards.append(new_card)   
        arrange_cards(computer_cards)
    
    elif card_to_discard:
        card_type = 'water' if isinstance(card_to_discard, int) else 'power'
        if card_type == 'water':
            new_card=water_cards_pile.pop(0)
        else:  # 'power'
            new_card=power_cards_pile.pop(0)
        computer_cards.append(new_card)   
        arrange_cards(computer_cards)



    print(f"Computer playing with card: {card_used}" if action_taken == 'use' else f"Computer discarded: {card_used}")
    print(f"Computer's water level is now at: {computer_tank}")
    print(f"Your water level is now at: {opponent_tank}")
    #print(f"Computer cards are now: {computer_cards}")
    
    return (computer_tank, opponent_tank)


def main():
    # TODO: Write your code as described in the instructions
    # Welcome message
    print("Welcome to the WATER TANK game! Play against the computer!")
    print("Fill your tank by using or discarding a card for each turn.")
    print("The first player to fill their tank wins the game.")
    print("Good luck!\n")
    print("The Human Player has been selected to go first.")
    
	# Initial setup
    water_cards_pile = setup_water_cards()  
    power_cards_pile = setup_power_cards()
    human_tank, computer_tank = 0, 0  # Starting tank levels
    human_cards, computer_cards = deal_cards(water_cards_pile, power_cards_pile)
    turn = 'human'
    
    # Main game loop
    while True:  #
        if turn == 'human':
            human_tank, computer_tank = human_play(human_tank, human_cards, water_cards_pile, power_cards_pile, computer_tank)
            turn = 'computer'  # Switch turn
            print()
        else:
            computer_tank, human_tank = computer_play(computer_tank, computer_cards, water_cards_pile, power_cards_pile, human_tank)
            turn = 'human'  # Switch turn
            print()
        

        if filled_tank(human_tank) or filled_tank(computer_tank):
            break
        
        check_pile(water_cards_pile, "water") 
        check_pile(water_cards_pile, "power")
    
    # Determine winner
    if human_tank > computer_tank:
        print("Human wins!")
    else:
        print("Computer wins!")

    
    
    

if __name__ == '__main__':
    main()
