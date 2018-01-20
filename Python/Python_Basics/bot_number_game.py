import random

# This is a guessing game where python tries to guess your number
def game():

    # Get player's number
    while True:
        print("Pick a number 1 - 1,000")
        try:
            players_number = int(input("What is the number the computer should try and guess? "))
        except ValueError:
            print("That's not a number.")
        else:
            if players_number >= 1 and players_number <= 1000:                
                break;
            else:
                print("{} is not a number between 1 and 1,000.".format(players_number))
        
    # Find out if player wants to give hint
    print("Yes or No")
    give_hint = input("Should we give the bot hints as in higher or lower? ")
    hint = False
    if give_hint.lower() == 'yes':
        hint = True
    pythons_guesses = []
    min_number = 1
    max_number = 1000

    while True:

        pythons_guess = random.randint(min_number, max_number)
        if pythons_guess not in pythons_guesses:
            pythons_guesses.append(pythons_guess)
            if pythons_guess == players_number:
                break;        
            if hint == True:
                if players_number > pythons_guess:
                    min_number = pythons_guess + 1
                else:
                    max_number = pythons_guess - 1

    print("Python guessed the number {} in {} {try_plural}".format(players_number, len(pythons_guesses), try_plural="one try! Impressssssive." if len(pythons_guesses) == 1 else "tries."))
        
game()
