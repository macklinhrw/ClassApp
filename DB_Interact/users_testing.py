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
import message_utils


def main():
    connection = ConnectMySQL()
    # connection = ConnectMySQL(host='local')
    active_user = None
    active_class = None
    active_thread = None
    while True:
        command = input(">>> ")
        try:
            if command == "!newuser":
                new_user(connection)
            if "!login" in command:
                active_user = login(connection)
            if "!easy" in command:
                active_user = User(connection, nickname='elig', password='couch')
                active_class = Class(connection, 'C00000')
                print(active_user)
            if "!update" in command:
                to_update = list()
                if "-description" in command:
                    to_update.append("description")
                if "-name" in command:
                    to_update.append("name")
                if "-email" in command:
                    to_update.append("email")
                if "-password" in command:
                    to_update.append("password")
                update_user_info(to_update, connection, active_user)
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
                title = command[i + 1:]
                active_thread = Thread(connection, title)
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
                    if active_thread.type != 'direct':
                        active_thread.adduser(connection, unick)
                        print("INFO: Added " + unick + " to thread " + active_thread.group_name)
                    else:
                        print("ERROR: You can not add a user to a direct thread. ")
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
            if "!newdm" in command:
                i = command.index("-")
                to_add_nick = command[i + 1:]
                to_add = User(connection, nickname=to_add_nick, password='bypass')
                message_utils.newthread(connection, active_user, 'direct', active_class, dm_to=to_add)
            if "!dm" in command:
                i = command.index("-")
                to_dm = command[i + 1:]
                threadname1 = active_user.nickname + to_dm
                threadname2 = to_dm + active_user.nickname
                active_thread = Thread(connection, threadname1)
                if active_thread.id == '':
                    active_thread = Thread(connection, threadname2)
                if active_thread.id == '':
                    print("ERROR: There is no active dm with this user. !newdm to start one.")
                if active_thread.id != '':
                    print("INFO: Connected to direct message with " + to_dm + ". !m or .m to send messages.")
            if "!newthread" in command:
                name = input("Enter a name for the new group: ")
                desc = input("Enter a group description (optional): ")
                message_utils.newthread(connection, active_user, 'group', active_class, group_name=name, description=desc)
                print("INFO: Created new thread " + name + ". There is nobody in it, so you will need to !thread to select"
                                                           " and then !addtothread people in.")
            if "!help" in command:
                print("INFO about common commands (coming soon).")
            if "!quit" in command:
                break
        except:
            print("ERROR: There was an error in your syntax. !help for common commands. ")
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


def new_user(connection):
    print("Creating NEW USER")
    nick = input("Enter new user nickname: ")
    connection.cursor.execute("select nickname from users")
    existing_nicknames_dicts = connection.cursor.fetchall()
    existing_nicknames = list()
    for n in existing_nicknames_dicts:
        existing_nicknames.append(n['nickname'])
    while nick in existing_nicknames:
        nick = input("That nickname is taken. Try another nickname: ")
    password = getpass.getpass(prompt="Enter new user password: ")
    print("Please tell us more about yourself, if you are comfortable. \nYou can decline to answer the following questions by pressing enter with no input.")
    name = input("Enter your name: ")
    desc = input("Enter your bio description (max 150 words): ")
    if len(desc) > 150:
        desc = ''
    birth = input("Enter your birth date (format YYYY-MM-DD): ")
    email = input("Enter your email: ")
    type = 'student'
    users_utils.new_user(connection, name, nick, desc, birth, email, password, type)


def update_user_info(to_update, connection, user):
    """

    :param to_update: list of things to update options i.e. ["description","name"]
    :param connection: ConnectMySQL object
    :param user: active_user object
    :return:
    """
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
    if "email" in to_update:
        update_input = input("Enter your new email: ")
        active_user.update(connection, email=update_input)
    if "password" in to_update:
        update_input = getpass.getpass(prompt="Enter your new password: ")
        active_user.update(connection, password=update_input)
    # TODO add other params for update


if __name__ == "__main__":
    main()
