import os
import random

CELLS = [(0, 0), (1, 0), (2, 0), (3, 0), (4, 0),
         (0, 1), (1, 1), (2, 1), (3, 1), (4, 1),
         (0, 2), (1, 2), (2, 2), (3, 2), (4, 2),
         (0, 3), (1, 3), (2, 3), (3, 3), (4, 3),
         (0, 4), (1, 4), (2, 4), (3, 4), (4, 4)]

def clear_screen():
    os.system('cls' if os.name == 'nt' else 'clear')

def get_locations():
    return random.sample(CELLS, 3)

def move_player(player, move):
    # get player location
    x, y = player
    # if move == Left, x-1
    if move == "LEFT" or move == "A":
        x -= 1
    # if move == RIGHT, x+1
    if move == "RIGHT" or move == "D":
        x += 1
    # if move == UP, y-1
    if move == "UP" or move == "W":
        y -= 1
    # if move == DOWN, y+1
    if move == "DOWN" or move == "S":
        y += 1
    return x, y

def get_moves(player):
    moves = ["LEFT","A", "RIGHT","D", "UP","W", "DOWN","S"]
    x, y = player
    # if player's y == 0, they can't move left
    if y == 0:
        moves.remove("UP")
        moves.remove("W")
    # if player's y == 4, they can't move down
    if y == 4:
        moves.remove("DOWN")
        moves.remove("S")
    # if player's x == 0, they can't move left
    if x == 0:
        moves.remove("LEFT")
        moves.remove("A")
    # if player's x == 4, they can't move right
    if x == 4:
        moves.remove("RIGHT")
        moves.remove("D")
    return moves

def draw_map(player, previous_player):
    print(" _"*5)
    tile = "|{}"

    for cell in CELLS:
        x, y = cell
        if x < 4:
            line_end = ""
            if cell == player:
                output = tile.format("X")
            elif cell in previous_player:
                output = tile.format(".")
            else:
                output = tile.format("_")
        else:
            line_end = "\n"
            if cell == player:
                output = tile.format("X|")
            elif cell in previous_player:
                output = tile.format(".|")
            else:
                output = tile.format("_|")
        print(output, end = line_end)

def hide_move_shortcuts(moves):
    shown_moves = []
    shortcut_moves = ['W','A','S','D']
    for move in moves:
        if move not in shortcut_moves:
            shown_moves.append(move)
    return shown_moves

def game_loop():
    monster, door, player = get_locations()
    previous_player = []
    playing = True
    while playing:
        clear_screen()
        draw_map(player, previous_player)
        previous_player.append(player)
        valid_moves = get_moves(player)

        print("You are currently in room {}.".format(player)) # fill with player location
        print("You can move {}".format(", ".join(hide_move_shortcuts(valid_moves)))) # fill with available moves
        print("Enter Q to quit.")
        move = input("> ").upper()
        # Quit
        if move == 'Q':
            print("\n ** See you next time! **\n")
            break
        # Good move? Change the player position
        if move in valid_moves:
            player = move_player(player, move)
            # On the monster? They lose!
            if player == monster:
                print("\n ** Oh no! The monster got you! Better luck next time. ** \n")
                playing = False
            # On the door? They win!
            if player == door:
                print("\n ** You escaped! Congratulations! ** \n")
                playing = False
        # Bad move? Don't change anything
        else:
            input("\n ** Walls are hard! Don't run into them! **\n")
    else:
        if input("Play again? [Y/n] ").lower() != "n":
            game_loop()


clear_screen()
print("Welcome to the dungeon.")
input("Press return to start")
clear_screen()
game_loop()

    # Otherwise, loop back around
