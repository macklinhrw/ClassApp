"""
@author: Elias Gilbert
@purpose: drivers for testing the framework for MySQL DB interaction
which was set up in users_utils, sql_connect, etc.
@dependency: pip install mysql-connector-python
"""
import users_utils
from sql_connect import ConnectMySQL
from users_utils import User
from users_utils import Class
import getpass


def main():
    connection = ConnectMySQL()
    active_user = None
    while True:
        command = input()
        if command == "!newuser":
            new_test(connection)
        if "!login" in command:
            active_user = login(connection)
        if "!easy" in command:
            active_user = User(connection, nickname='elig', password='couch')
            print(active_user)
        if "!update" in command:
            to_update = list()
            if "-description" in command:
                to_update.append("description")
            update_test(to_update, connection, active_user)
        if "!dUser" in command:
            if active_user is not None:
                print(active_user)
            else:
                print("No user is logged in. Use -login to log in")
        if "!addclass" in command:
            if active_user is not None:
                i = command.index("C")
                cid = command[i:i+6]
                current_class = Class(connection, cid)
                current_class.add_student(connection, active_user)
            else:
                print("Must log in first. Use -login to log in")
        if "!quit" in command:
            break
    connection.close()


def login(connection):
    nick_input = input("Enter your nickname: ")
    pass_input = getpass.getpass(prompt="Enter your password: ")
    active_user = User(connection, nickname=nick_input, password=pass_input)
    print(active_user)
    return active_user


def new_test(connection):
    # TODO build a case where user does not want to provide some or
    #  all information (only nickname and password are necessary)
    print("Creating NEW USER")
    nick = input("Enter new user nickname: ")
    connection.cursor.execute("select nickname from users")
    existing_nicknames_dicts = connection.cursor.fetchall()
    existing_nicknames = list()
    for n in existing_nicknames_dicts:
        existing_nicknames.append(n['nickname'])
    print(existing_nicknames)
    while nick in existing_nicknames:
        nick = input("That nickname is taken. Try another nickname: ")
    password = input("Enter new user password: ")
    name = input("Enter your name: ")
    desc = input("Enter your bio description (max 150 words): ")
    if len(desc) > 150:
        desc = ''
    birth = input("Enter your birth date (format YYYY-MM-DD): ")
    email = input("Enter your email: ")
    type = 'student'
    users_utils.new_user(connection, name, nick, desc, birth, email, password, type)


def update_test(to_update, connection, user):
    #param to_update: list of things to update options ["description"]
    # TODO improve mechanism for changing
    if user is None:
        active_user = login(connection)
    else:
        active_user = user
    if "description" in to_update:
        update_input = input("Enter your new description: ")
        active_user.update(connection, desc=update_input)


if __name__ == "__main__":
    main()
