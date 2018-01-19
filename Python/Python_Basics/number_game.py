import random

# generate a random number between 1 and 10
secret_num = random.randint(1, 10)

def main():
    while True:
        # get a number guess from the player
        guess = int(input("Guess a number between 1 and 10: "))

        # compare guess to number
        if guess == secret_num:
            # print hit or miss
            print("You got it! My number was {}".format(secret_num))
            break
        else:
            print("That's not it!")

main()
