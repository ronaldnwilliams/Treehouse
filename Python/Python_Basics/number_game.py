import random

def game():
        
    # generate a random number between 1 and 10
    secret_num = random.randint(1, 10)
    guesses = []
    
    while len(guesses) < 3:
    
        # get a number guess from the player
        try:
            guess = int(input("Guess a number between 1 and 10: "))
        except ValueError:
            print("That's not a number.")
        else:        
            # keep track of how many guesses
            # compare guess to number
            if guess == secret_num:
                # print hit or miss
                print("You got it! My number was {} and you guessed it in {} {try_plural}".format(secret_num, len(guesses) + 1 , try_plural="try" if len(guesses) == 0 else "tries"))
                break                    
            else:
                print("That's not it!")
                guesses.append(guess)
                # give a hint
                if len(guesses) != 3:
                    if guess > secret_num:
                        print("Hint: Guess lower.")
                    else:
                        print("Hint: Guess higher.")
    else:
        print("You lose. My number was {}.".format(secret_num))
    play_again = input("Do you want to play again? Y/n ")
    if play_again.lower() != 'n':
        game()
    else:
        print("Good bye. Thank you for playing.")

game()
