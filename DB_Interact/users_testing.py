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
from message_utils import Thread
from message_utils import Message
import getpass


def main():
    connection = ConnectMySQL()
    active_user = None
    active_class = None
    active_thread = None
    while True:
        command = input(">>> ")
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
            if "-name" in command:
                to_update.append("name")
            update_test(to_update, connection, active_user)
        if "!dUser" in command:
            if active_user is not None:
                print(active_user)
            else:
                print("ERROR: No user is logged in. Use !login to log in")
        if "!addclass" in command:
            if active_user is not None:
                i = command.index("C")
                cid = command[i: i + 6]
                class_to_add = Class(connection, cid)
                class_to_add.add_student(connection, active_user)
            else:
                print("ERROR: Must log in first. Use !login to log in")
        if "!enterclass" in command:
            if active_user is not None:
                i = command.index("C")
                cid = command[i: i + 6]
                active_class = Class(connection, cid)
                print("INFO: Entered class " + active_class.id + " with user " + active_user.nickname)
            else:
                print("ERROR: Must log in first. Use !login to log in")
        if "!dClass" in command:
            if active_class is not None:
                print(active_class)
            else:
                print("ERROR: No user is logged in. Use !login to log in")
        if "!dAll" in command:
            print("="*3+"USER:"+"="*3)
            print(active_user)
            print("="*3+"CLASS:"+"="*3)
            print(active_class)
            print("="*3+"THREAD:"+"="*3)
            print(active_thread)
        if "!thread" in command:
            i = command.index("-")
            tid = command[i + 1:]
            print(tid)
            active_thread = Thread(connection, tid)
            if active_user.id in active_thread.members or active_thread.type == 'all':
                if active_thread.id is not '':
                    print("INFO: Thread " + active_thread.id + " aka " + str(active_thread.group_name) + " selected. Type !m or .m to send messages.")
                else:
                    active_thread = None
                    print("ERROR: Could not select thread")
            else:
                active_thread = None
                print("ERROR: You are not in this thread")
        if "!addtothread" in command:
            if active_thread is not None and active_user is not None:
                i = command.index("-")
                unick = command[i + 1:]
                active_thread.adduser(connection, unick)
                print("INFO: Added " + unick + " to thread " + active_thread.group_name)
            else:
                print("ERROR: Could not add user. Make sure you are logged in and have an active thread")
        if command == "!m":
            print("Enter as many messages as you want, !~ to end.")
            while True:
                text = input("::")
                if text == "!~":
                    break
                msg = Message(text=text, sender=active_user, thread=active_thread, type='new')
                msg.add(connection)
        if command == ".m":
            text = input("::")
            msg = Message(text=text, sender=active_user, thread=active_thread, type='new')
            msg.add(connection)
        if "!quit" in command:
            break
    connection.close()


def login(connection):
    nick_input = input("Enter your nickname: ")
    pass_input = getpass.getpass(prompt="Enter your password: ")
    active_user = User(connection, nickname=nick_input, password=pass_input)
    if active_user.nickname is not "":
        print("INFO: Logged in as: "+active_user.nickname)
    else:
        print("ERROR: Failed to log in.")
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
    while nick in existing_nicknames:
        nick = input("That nickname is taken. Try another nickname: ")
    password = input("Enter new user password: ")
    print("Please tell us more about yourself, if you are comfortable. \nYou can decline to answer the following questions by pressing enter with no input.")
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
    if user is None:
        active_user = login(connection)
    else:
        active_user = user
    if "description" in to_update:
        update_input = input("Enter your new description: ")
        active_user.update(connection, desc=update_input)
    if "name" in to_update:
        update_input = input("Enter your new name: ")
        active_user.update(connection, name=update_input)
    # TODO add other params for update


if __name__ == "__main__":
    main()
