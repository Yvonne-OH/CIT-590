import unittest
from random import randint

import water_tank

class Test(unittest.TestCase):

    ######################################
    ###Test Cases for setup_water_cards###
    ######################################

    def test_setup_water_cards_length_of_array(self):
        """
        Test the number of water cards is 53 (30 + 15 + 8)
        """
        self.assertEqual(53, len(water_tank.setup_water_cards()), "There should be 53 total cards")

    def test_setup_water_cards_successful_shuffle(self):
        """
        Test that water cards get shuffled by checking if the first 8 items are all the same
        """
        water_shuffle = water_tank.setup_water_cards()
        unshuffled = []
        unshuffled.append([1 for i in range(30)] + [5 for i in range(15)] + [10 for i in range(8)])
        unshuffled.append([1 for i in range(30)] + [10 for i in range(8)] + [5 for i in range(15)])
        unshuffled.append([5 for i in range(15)] + [10 for i in range(8)] + [1 for i in range(30)])
        unshuffled.append([5 for i in range(15)] + [1 for i in range(30)] + [10 for i in range(8)])
        unshuffled.append([10 for i in range(8)] + [1 for i in range(30)] + [5 for i in range(15)])
        unshuffled.append([10 for i in range(8)] + [5 for i in range(15)] + [1 for i in range(30)])

        self.assertNotIn(water_shuffle, unshuffled, "The water cards do not appear to have shuffled")

    def test_setup_water_cards_card_count(self):
        """
        Counts each card type
        """
        water_cards = water_tank.setup_water_cards()
        self.assertEqual(30, water_cards.count(1),
                         "There should be 30 x water cards with value 1 after setting up the cards")
        self.assertEqual(15, water_cards.count(5),
                         "There should be 15 x water cards with value 5 after setting up the cards")
        self.assertEqual(8, water_cards.count(10),
                         "There should be 8 x water cards with value 10 after setting up the cards")

    ############################################
    ###   Test Cases for setup_power_cards   ###
    ############################################

    def test_setup_power_cards_length_of_array(self):
        self.assertEqual(15, len(water_tank.setup_power_cards()),
                         "There should be 15 power cards total after setting up the cards")

    def test_setup_power_cards_successful_shuffle(self):
        """
        Test that power cards get shuffled by checking if the first items are all the same
        """
        power_shuffle = water_tank.setup_power_cards()
        unshuffled = []
        unshuffled.append(["SOH" for i in range(10)] + ["DOT" for i in range(2)] + ["DMT" for i in range(3)])
        unshuffled.append(["SOH" for i in range(10)] + ["DMT" for i in range(3)] + ["DOT" for i in range(2)])
        unshuffled.append(["DMT" for i in range(3)] + ["DOT" for i in range(2)] + ["SOH" for i in range(10)])
        unshuffled.append(["DMT" for i in range(3)] + ["SOH" for i in range(10)] + ["DOT" for i in range(2)])
        unshuffled.append(["DOT" for i in range(2)] + ["DMT" for i in range(3)] + ["SOH" for i in range(10)])
        unshuffled.append(["DOT" for i in range(2)] + ["SOH" for i in range(10)] + ["DMT" for i in range(3)])

        self.assertNotIn(power_shuffle, unshuffled, "It doesn't look like the power cards shuffled")

    def test_setup_power_cards_card_count(self):

        power_shuffle = water_tank.setup_power_cards()
        self.assertEqual(10, power_shuffle.count("SOH"), "There should be 10 SOH cards after shuffling")
        self.assertEqual(2, power_shuffle.count("DOT"), "There should be 2 DOT cards after shuffling")
        self.assertEqual(3, power_shuffle.count("DMT"), "There should be 3 DMT cards after shuffling")

    #############################################
    ###   Test Cases for setup_cards          ###
    #############################################

    def test_setup_cards(self):
        water_cards_pile, power_cards_pile = water_tank.setup_cards()
        self.assertIsInstance(water_cards_pile[0], int, "Water cards pile should be a list of integers")
        self.assertIsInstance(power_cards_pile[0], str, "Power cards pile should be a list of strings")

    #############################################
    ###   Test Cases for get_card_from_pile   ###
    #############################################

    def test_get_card_from_pile(self):
        """
        Make sure the return values match and the selected cards pop off
        """
        mock_pile = [1, 5, 5, 10, 1, 1, 1, 5]
        self.assertEqual(1, water_tank.get_card_from_pile(mock_pile, 0),
                         "Check the return value for the get_card_from_pile function")
        self.assertEqual(5, mock_pile[0], "Check if the selected value is popped from the list")
        self.assertEqual(10, water_tank.get_card_from_pile(mock_pile, 2),
                         "Check the return value for the get_card_from_pile function")
        self.assertEqual(0, mock_pile.count(10), "Check if the selected value is popped from the list")
        self.assertEqual(3, mock_pile.count(5), "Check if popping from the list manipulates more than one list item")
        self.assertEqual(3, mock_pile.count(1), "Check if popping from the list manipulates more than one list item")
        self.assertEqual(6, len(mock_pile),
                         "The number of items in the list is not as expected after several operations")

    #####################################
    ###   Test Cases for deal_cards   ###
    #####################################

    def test_deal_cards_5_cards_each_deck(self):
        mock_power_cards = ['SOH', 'SOH', 'DOT', 'DMT', 'DOT', 'SOH', 'SOH', 'DMT',
                            'SOH', 'SOH', 'SOH', 'SOH', 'DMT', 'SOH', 'SOH']
        mock_water_cards = [5, 1, 1, 1, 1, 5, 1, 10, 1, 10, 5, 1, 1, 5, 1, 1, 5, 10, 1, 1, 5, 10, 5, 1, 1, 1, 10, 1, 5,
                            1, 5, 1, 5, 1, 5, 1, 5, 1, 10, 1, 1, 10, 5, 1, 1, 1, 5, 1, 1, 1, 5, 1, 10]
        # count to make sure there are 5 cards in each deck
        player_1_cards, player_2_cards = water_tank.deal_cards(mock_water_cards, mock_power_cards)
        self.assertEqual(5, len(player_1_cards),
                         "After dealing cards, Player 1 does not have 5 cards")
        self.assertEqual(5, len(player_2_cards),
                         "After dealing cards, Player 2 does not have 5 cards")
        water_1 = player_1_cards[:3]
        power_1 = player_1_cards[3:]
        water_2 = player_2_cards[:3]
        power_2 = player_2_cards[3:]
        self.assertListEqual(sorted(water_1), water_1, "Player 1 cards were not arranged.")
        self.assertListEqual(sorted(water_2), water_2, "Player 2 cards were not arranged.")
        self.assertListEqual(sorted(power_1), power_1, "Player 1 cards were not arranged.")
        self.assertListEqual(sorted(power_2), power_2, "Player 2 cards were not arranged.")

    def test_deal_cards_2_str_3_int_ea_deck(self):
        # reset card piles
        mock_power_cards = ['SOH', 'SOH', 'DOT', 'DMT', 'DOT', 'SOH', 'SOH', 'DMT',
                            'SOH', 'SOH', 'SOH', 'SOH', 'DMT', 'SOH', 'SOH']
        mock_water_cards = [5, 1, 1, 1, 1, 5, 1, 10, 1, 10, 5, 1, 1, 5, 1, 1, 5, 10, 1, 1, 5, 10, 5, 1, 1, 1, 10, 1, 5,
                            1, 5, 1, 5, 1, 5, 1, 5, 1, 10, 1, 1, 10, 5, 1, 1, 1, 5, 1, 1, 1, 5, 1, 10]
        # three ints and two str in each deck

        player_1_cards, player_2_cards = water_tank.deal_cards(mock_water_cards, mock_power_cards)
        self.assertEqual(3,
                         sum(isinstance(x, int) for x in player_1_cards),
                         "After dealing cards, Player 1 does not have 3 water cards")
        self.assertEqual(2,
                         sum(isinstance(x, str) for x in player_1_cards),
                         "After dealing cards, Player 1 does not have 2 power cards")
        self.assertEqual(3,
                         sum(isinstance(x, int) for x in player_2_cards),
                         "After dealing cards, Player 2 does not have 3 water cards")
        self.assertEqual(2,
                         sum(isinstance(x, str) for x in player_2_cards),
                         "After dealing cards, Player 2 does not have 2 power cards")

        self.assertTrue(len(mock_power_cards) == 11, "Cards were not taken off from power cards pile.")
        self.assertTrue(len(mock_water_cards) == 47, "Cards were not taken off from water cards pile.")

        self.assertListEqual([1, 1, 5, 'DOT', 'SOH'], player_1_cards, "Doesn't deal cards alternately to each player.")
        self.assertListEqual([1, 1, 5, 'DMT', 'SOH'], player_2_cards, "Doesn't deal cards alternately to each player.")

        self.assertListEqual(['DOT', 'SOH', 'SOH', 'DMT', 'SOH', 'SOH', 'SOH', 'SOH', 'DMT', 'SOH', 'SOH'],
                             mock_power_cards, "Doesn't deal cards alternately to each player.")
        self.assertListEqual(
            [1, 10, 1, 10, 5, 1, 1, 5, 1, 1, 5, 10, 1, 1, 5, 10, 5, 1, 1, 1, 10, 1, 5, 1, 5, 1, 5, 1, 5, 1, 5, 1, 10, 1,
             1, 10, 5, 1, 1, 1, 5, 1, 1, 1, 5, 1, 10], mock_water_cards,
            "Doesn't deal cards alternately to each player.")

    #########################################
    ###   Test Cases for apply_overflow   ###
    #########################################

    def test_apply_overflow(self):
        random_tank_level = randint(81, 99)
        overflow = random_tank_level - 80
        expected_tank_level = 80 - overflow
        error_message = "If the tank value is {}, the remaining water should be {} after applying overflow rules.".format(
            random_tank_level, expected_tank_level)
        self.assertEqual(expected_tank_level, water_tank.apply_overflow(random_tank_level), error_message)

        random_tank_level = randint(81, 99)
        overflow = random_tank_level - 80
        expected_tank_level = 80 - overflow
        error_message = "If the tank value is {}, the remaining water should be {} after applying overflow rules.".format(
            random_tank_level, expected_tank_level)
        self.assertEqual(expected_tank_level, water_tank.apply_overflow(random_tank_level), error_message)

    ########################################
    ###   Test Cases for test_use_card   ###
    ########################################

    def test_use_water_card(self):

        # player_tank, card_used, player_cards, opponent_tank
        player_cards = [1, 5, 10, "DOT", "DMT"]
        player_tank, opponent_tank = water_tank.use_card(25, 1, player_cards, 25)
        self.assertEqual(26, player_tank,
                         "Using a water card does not update the tank level correctly.")
        self.assertEqual(25, opponent_tank,
                         "Using a water card does not update the opponent tank level correctly.")
        self.assertTrue(len(player_cards) == 4, "Card should be removed from player's hand when it is used.")

    def test_use_soh_card(self):

        # player_tank, card_used, player_cards, opponent_tank
        player_cards = [1, 5, 10, "DOT", "SOH"]
        player_tank, opponent_tank = water_tank.use_card(25, "SOH", player_cards, 25)
        self.assertEqual(37, player_tank,
                         "Using SOH does not update the tank level correctly.")
        self.assertEqual(13, opponent_tank,
                         "Using SOH does not update the tank level correctly.")
        self.assertTrue(len(player_cards) == 4, "Card should be removed from player's hand when it is used.")

        # Even Steal
        player_cards = [1, 5, 10, "DOT", "SOH"]
        player_tank, opponent_tank = water_tank.use_card(25, "SOH", player_cards, 26)
        self.assertEqual(38, player_tank,
                         "Using SOH does not update the tank level correctly.")
        self.assertEqual(13, opponent_tank,
                         "Using SOH does not update the tank level correctly.")
        self.assertTrue(len(player_cards) == 4, "Card should be removed from player's hand when it is used.")

    def test_use_dmt_card(self):

        # player_tank, card_used, player_cards, opponent_tank
        player_cards = [1, 5, 10, "DOT", "DMT"]
        player_tank, opponent_tank = water_tank.use_card(25, "DMT", player_cards, 25)
        self.assertEqual(50, player_tank,
                         "Using DMT does not update the tank level correctly.")
        self.assertEqual(25, opponent_tank,
                         "Using DMT does not update the tank level correctly.")
        self.assertTrue(len(player_cards) == 4, "Card should be removed from player's hand when it is used.")

    def test_use_dot_card(self):

        # player_tank, card_used, player_cards, opponent_tank
        player_cards = [1, 5, 10, "DOT", "DMT"]
        player_tank, opponent_tank = water_tank.use_card(25, "DOT", player_cards, 25)
        self.assertEqual(25, player_tank,
                         "Using DOT does not update the tank level correctly.")
        self.assertEqual(0, opponent_tank,
                         "Using DOT does not update the tank level correctly.")
        self.assertTrue(len(player_cards) == 4, "Card should be removed from player's hand when it is used.")

    #######################################
    ###   Test Cases for discard_card   ###
    #######################################

    def test_discard_card_water(self):

        # player_tank, card_used, player_cards, opponent_tank
        water_cards_pile = [1, 5, 10, 5, 10, 1]
        power_cards_pile = ['SOH', 'DMT', 'DMT', 'DOT']
        player_cards = [1, 5, 10, 'SOH', 'DMT']
        water_tank.discard_card(1, player_cards, water_cards_pile, power_cards_pile)
        self.assertListEqual([1, 5, 10, 5, 10, 1, 1], water_cards_pile,
                             "Using the discard card does not function correctly.")
        self.assertListEqual([5, 10, 'SOH', 'DMT'], player_cards,
                             "Using the discard card does not function correctly.")

    def test_discard_card_power(self):

        # player_tank, card_used, player_cards, opponent_tank
        water_cards_pile = [1, 5, 10, 5, 10, 1]
        power_cards_pile = ['SOH', 'DMT', 'DMT', 'DOT']
        player_cards = [1, 5, 10, 'SOH', 'DMT']
        water_tank.discard_card('DMT', player_cards, water_cards_pile, power_cards_pile)
        self.assertListEqual(['SOH', 'DMT', 'DMT', 'DOT', 'DMT'], power_cards_pile,
                             "Using the discard card does not function correctly.")
        self.assertListEqual([1, 5, 10, 'SOH'], player_cards, "Using the discard card does not function correctly.")

    #######################################
    ###   Test Cases for filled_tank    ###
    #######################################

    def test_filled_tank(self):
        self.assertTrue(water_tank.filled_tank(80), "A tank filled to 80 is full")
        self.assertTrue(water_tank.filled_tank(75), "A tank filled to 75 is full")
        self.assertFalse(water_tank.filled_tank(74), "A tank filled to 74 is underfull")
        self.assertFalse(water_tank.filled_tank(81), "A tank filled to 81 is overfull")
        self.assertFalse(water_tank.filled_tank(-1), "A tank filled to -1 should not return as full")

    #########################################
    ###   Test Cases for arrange_cards    ###
    #########################################

    def test_arrange_cards(self):
        mock_deck = ["SOH", 1, "DMT", 10, 1]
        water_tank.arrange_cards(mock_deck)

        self.assertIsInstance(mock_deck[0], int,
                              "The card at index 0 (first item) should be an water card which has a type of integer")
        self.assertIsInstance(mock_deck[1], int,
                              "The card at index 1 (second item) should be an water card which has a type of integer")
        self.assertIsInstance(mock_deck[2], int,
                              "The card at index 2 (third item) should be an water card which has a type of integer")
        self.assertIsInstance(mock_deck[3], str,
                              "The card at index 3 (fourth item) should be a power card which has a type of string")
        self.assertIsInstance(mock_deck[4], str,
                              "The card at index 4 (fifth item) should be a power card which has a type of string")
        self.assertTrue(mock_deck[0] == 1, "Check the water cards sorting")
        self.assertTrue(mock_deck[1] == 1, "Check the water cards sorting")
        self.assertTrue(mock_deck[2] == 10, "Check the water cards sorting")
        self.assertTrue(mock_deck[3] == "DMT", "Check the power cards sorting")
        self.assertTrue(mock_deck[4] == "SOH", "Check the power cards sorting")

    #####################################
    ###   Test Cases for check_pile   ###
    #####################################

    def test_check_water_pile_with_card(self):
        # the way the sample code is written we have to get them to at least call
        # the pile "water" or something else, keep in mind for instructions
        """
        Checks that the check pile function adds a whole new deck when presented an empty
        list, and doesn't when the list still has at least one element
        """
        pile = [1]
        water_tank.check_pile(pile, "water")
        self.assertEqual(1, len(pile), "The water pile should not change while there is still a card left")

    def test_check_power_pile_with_card(self):
        pile = ["SOH"]
        water_tank.check_pile(pile, "power")
        self.assertEqual(1, len(pile), "The power pile should not change while there is still a card left")

    def test_check_empty_water_pile(self):
        pile = []
        water_tank.check_pile(pile, "water")
        self.assertEqual(53, len(pile),
                         "When the water pile is empty, check_piles should start a new 53 water card deck. "
                         "Check if you are modifying the pile by reference.")

    def test_check_empty_power_pile(self):
        pile = []
        water_tank.check_pile(pile, "power")
        self.assertEqual(15, len(pile),
                         "When the power pile is empty, check_piles should start a new 25 power card deck. "
                         "Check if you are modifying the pile by reference.")


if __name__ == '__main__':
    unittest.main()
