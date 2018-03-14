#!/usr/bin/env python3

from collections import OrderedDict
import datetime
import os
import sys

from peewee import *

db = SqliteDatabase('diary.db')


class Entry(Model):
    content = TextField()
    timestamp = DateTimeField(default=datetime.datetime.now)

    class Meta:
        database = db


def initialize():
    """Create the database and the table if they don't exist."""
    db.connect()
    db.create_tables([Entry], safe=True)


def clear_screen():
    os.system('cls' if os.name == 'nt' else 'clear')


def menu_loop():
    """Show the menu."""
    clear_screen()
    choice = None

    while choice != 'q':
        print("Enter 'q' to quit.")
        for key, value in menu.items():
            print('{}) {}'.format(key, value.__doc__))
        choice = input('Action: ').lower().strip()

        if choice in menu:
            clear_screen()
            menu[choice]()


def add_entry():
    """Add an entry."""
    print("Enter your entry. Press ctrl+d when finsihed.")
    data = sys.stdin.read().strip()

    if data:
        if input('Save entry? [Y/n] ').lower() != 'n':
            Entry.create(content=data)
            print('Saved successfully!')
            clear_screen()


def view_entries(search_query=None):
    """View previous entries"""
    entries = Entry.select().order_by(Entry.timestamp.desc())

    if search_query:
        entries = entries.where(Entry.content.contains(search_query))

    for entry in entries:
        timestamp = entry.timestamp.strftime('%A %B %d, %Y %I:%M%p')
        clear_screen()
        print(timestamp)
        print('='*len(timestamp))
        print(entry.content)
        print('\n\n' + '='*len(timestamp))
        print('n) next entry')
        print('d) delete_entry')
        print('q) return to main menu')

        next_action = input('Action: [N/d/q]').lower().strip()
        if next_action == 'q':
            break
        elif next_action == 'd':
            delete_entry(entry)
    clear_screen()


def search_entries():
    """Search entries for a string"""
    view_entries(input('Search query: '))


def delete_entry(entry):
    """Delete a given entry."""
    if input('Are you sure you want to delete this entry? [y/N]').lower() == 'y':
        entry.delete_instance()
        print('Entry deleted.')


menu = OrderedDict([
    ('a', add_entry),
    ('v', view_entries),
    ('s', search_entries)
])

if __name__ == '__main__':
    initialize()
    menu_loop()
