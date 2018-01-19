# function to add item
def add_item(new_item):
    shopping_list.append(new_item)
    print("Added {}. List now has {} items.".format(new_item, len(shopping_list)))

# function to print list
def print_list():
    print("Here is your list: ")
    for item in shopping_list:
        print(item)

# help function
def print_help():
    print("""
What should we pick up at the store?
Enter 'SHOW' to show list.
Enter 'HELP' for help.
Enter 'DONE' to stop adding items.
""")
	

# make a list that will hold items
shopping_list = []

# menu selections
menu_list = ["SHOW", "HELP"]

# print instructions
print_help()

while True:
    # ask for new items
    new_item = input("> ")

    # be able to print list
    if new_item == "SHOW":
        print_list()

    # get help
    if new_item == "HELP":
        print_help()
 
    # be able to quit app
    if new_item == "DONE":
        # print out the list
        print_list()
        # break while loop
        break;

    # add new items to list
    if new_item not in menu_list:    
        add_item(new_item)

