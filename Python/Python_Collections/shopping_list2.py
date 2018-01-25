import os

def clear_screen():
    os.system("cls" if os.name == "nt" else "clear")

def remove_item(new_item):
    new_item = input("Item to be removed: ")
    if new_item in shopping_list:
        while new_item in shopping_list:
            shopping_list.remove(new_item)
        clear_screen()
        print_list()
        print("Removed '{}'".format(new_item))

# function to add item
def add_item(new_item):
    print_list()
    if len(shopping_list):
        position = input("Where should I add {}?\n"
                         "Press ENTER to add to the end of the list\n"
                         "> ".format(new_item))
    else:
        position = 0
    try:
        position = abs(int(position))
    except ValueError:
        position = None
    if position is not None:
        shopping_list.insert(position-1, new_item)
    else:
        shopping_list.append(new_item)
    print_list()

# function to print list
def print_list():
    clear_screen()
    print("Here is your list: ")
    index = 1
    for item in shopping_list:
        print("{}. {}".format(index, item))
        index += 1
    print("="*10)

# help function
def print_help():
    clear_screen()
    print("""
What should we pick up at the store?
Enter 'REMOVE' to remove an item.
Enter 'SHOW' to show list.
Enter 'HELP' for help.
Enter 'DONE' to stop adding items.
""")


# make a list that will hold items
shopping_list = []

# menu selections
menu_list = ["SHOW", "HELP", "REMOVE", "DONE", "QUIT"]

# print instructions
print_help()

while True:
    # ask for new items
    new_item = input("> ")

    # remove item from list
    if new_item.upper() == "REMOVE":
        if len(shopping_list):
            remove_item(new_item)
        else:
            print("No items to remove.")
            continue

    # be able to print list
    if new_item.upper() == "SHOW":
        print_list()
        continue
    # get help
    elif new_item.upper() == "HELP":
        print_help()
        continue
    # be able to quit app
    elif new_item.upper() == "DONE" or new_item.upper() == "QUIT":
        # print out the list
        print_list()
        # break while loop
        break;
    # add new items to list
    if new_item.upper() not in menu_list:
        add_item(new_item)