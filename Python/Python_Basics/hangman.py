def game():
    misses = 0
    the_word = set_up()
    
    print("Okay, player!")
    print("Unless you can guess this word... You will be hanged!")
    print("Good luck!")
    guesses = []
    dashes = "_ " * len(the_word)
    word_hidden = dashes.split()

    while True:
        print_hangman(misses)
        if misses == 6:
            print("Nice try, you've been hanged.")
            break;
        if '_' not in word_hidden:
            print("Congrats! You win. The word was '{}' and you got it in {} {guess_plural}.".format(the_word,len(guesses), guess_plural="guesses" if len(guesses) > 1 else "guess"))
            break;
        else:
            print("Here is your word: {}".format(word_hidden))
            
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
                            break;
                        else:
                            print("Sorry, no '{}' in this word.".format(guess))
                            misses += 1
                            break;
                    else:
                        print("You already guessed '{}'".format(guess))
                else:
                    print("'{}' is not a letter. Try again.".format(guess))
             
def set_up():
    print("Welcome to Hangman!")
    print("=" * 10)
    print("ADMIN SET UP")
    print("=" * 10)
    return input("What should the word be? ")

def print_hangman(misses):
    if misses == 0:
        print("""
            _______
            |    |
            |    
            |
            |
            |
           =========
        """)
    if misses == 1:
        print("""
            _______
            |    |
            |    O
            |
            |
            |
           =========
        """)
    if misses == 2:
        print("""
            _______
            |    |
            |    O
            |    |
            |
            |
           =========
        """)
    if misses == 3:
        print("""
            _______
            |    |
            |    O
            |    |\\
            |
            |
           =========
        """)
    if misses == 4:
        print("""
            _______
            |    |
            |    O
            |   /|\\
            |
            |
           =========
        """)
    if misses == 5:
        print("""
            _______
            |    |
            |    O
            |   /|\\
            |     \\
            |
           =========
        """)
    if misses == 6:
        print("""
            _______
            |    |
            |    O
            |   /|\\
            |   / \\
            |
           =========
        """)

game()
