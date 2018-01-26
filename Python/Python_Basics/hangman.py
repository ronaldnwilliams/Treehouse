import os
import sys

def game():
    while True:
        the_word = set_up()
        clear()
        greet()
        misses = []
        guesses = []
        dashes = "_ " * len(the_word)
        word_hidden = dashes.split()

        while True:
            print_guesses(guesses)
            print_hangman(misses)
            play = print_status(misses,word_hidden,the_word,guesses)
            if play == False:
                break;
            prompt_guess(guesses, the_word, word_hidden, misses)

        play_again()

def set_up():
    print("Welcome to Hangman!")
    print("=" * 10)
    print("ADMIN SET UP")
    print("=" * 10)
    return input("What should the word be? ")

def greet():
    print("""Okay, player!
Unless you can guess this word... You will be hanged!
Good luck!""")

def print_hangman(misses):
    if len(misses) == 0:
        print("""
            _______
            |    |
            |    
            |
            |
            |
           =========
        """)
    if len(misses) == 1:
        print("""
            _______
            |    |
            |    O
            |
            |
            |
           =========
        """)
    if len(misses) == 2:
        print("""
            _______
            |    |
            |    O
            |    |
            |
            |
           =========
        """)
    if len(misses) == 3:
        print("""
            _______
            |    |
            |    O
            |    |\\
            |
            |
           =========
        """)
    if len(misses) == 4:
        print("""
            _______
            |    |
            |    O
            |   /|\\
            |
            |
           =========
        """)
    if len(misses) == 5:
        print("""
            _______
            |    |
            |    O
            |   /|\\
            |     \\
            |
           =========
        """)
    if len(misses) == 6:
        print("""
            _______
            |    |
            |    O
            |   /|\\
            |   / \\
            |
           =========
        """)

def print_status(misses, word_hidden, the_word, guesses):
    if len(misses) == 6:
        print("Nice try, you've been hanged. The word was '{}'".format(the_word))
        return False
    if '_' not in word_hidden:
        print("Congrats! You win. The word was '{}' and you got it in {} {guess_plural} "
              "with {} {miss_plural}.".format(the_word,len(guesses),len(misses),
                                              guess_plural="guesses" if len(guesses) > 1 else "guess",
                                              miss_plural="miss" if len(misses) == 1 else "misses"))
        return False
    else:
        print("Here is your word: {}".format(word_hidden))

def prompt_guess(guesses, the_word, word_hidden, misses):
    while True:
        guess = input("Guess a letter: ")
        if guess.isalpha() and len(guess) == 1:
            if guess not in guesses:
                guesses.append(guess)
                if guess.lower() in the_word.lower():
                    print("Great guess!")
                    letter_loc = []
                    count = 0
                    for char in the_word:
                        if guess.lower() == char.lower():
                            letter_loc.append(count)
                        count +=1
                    for loc in letter_loc:
                        word_hidden.pop(loc)
                        word_hidden.insert(loc, guess.lower())
                    clear()
                    break;
                else:
                    print("Sorry, no '{}' in this word.".format(guess))
                    misses.append(guess)
                    clear()
                    return misses;
            else:
                print("You already guessed '{}'".format(guess))
        else:
            print("'{}' is not a letter. Try again.".format(guess))

def print_guesses(guesses):
    all_guesses = "Guesses: "
    for letter in guesses:
        all_guesses += "{}, ".format(letter)
    print(all_guesses)

def play_again():
    ask = input("yes/no Would you like to play again? ")
    if ask.lower() == 'yes' or ask == '':
        return True
    else:
        sys.exit()

def clear():
    if os.name == 'nt':
        os.system('cls')
    else:
        os.system('clear')

game()
