import random

# generate a random number between 1 and 10
secret_num = random.randint(1, 10)

def main():
    count = 0
    while True:
        # get a number guess from the player
        guess = int(input("Guess a number between 1 and 10: "))
        # keep track of how many guesses
        count += 1
        # compare guess to number
        if guess == secret_num:
            # print hit or miss
            print("You got it! My number was {} and you guessed it in {} {try_plural}".format(secret_num, count, try_plural="try" if count == 1 else "tries"))
            break
        else:
            print("That's not it!")
            # give a hint
            if guess > secret_num:
                print("Hint: Guess lower.")
            else:
                print("Hint: Guess higher.")

main()
