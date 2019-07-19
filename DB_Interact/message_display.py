import users_utils
from sql_connect import ConnectMySQL
from users_utils import User
from users_utils import Class
from message_utils import Thread
from message_utils import Message
import getpass
import subprocess
import time


def main():
    connection = ConnectMySQL()
    active_user = login(connection)
    class_inp = input("Class ID: ")
    active_class = Class(connection, class_inp)
    thread_inp = input("Thread Title: ")
    active_thread = Thread(connection, thread_inp)
    prev = list()
    subprocess.call('clear')
    i = 0
    while i < 1000:
        connection = ConnectMySQL(mute=True)
        connection.cursor.execute("SELECT * FROM messages WHERE thread='{0}'".format(active_thread.id))
        thread_messages = connection.cursor.fetchall()
        sorted_m = sorted(thread_messages, key=lambda i: i['datetime'])
        for message in sorted_m:
            if message not in prev:
                m = Message(type='disp', sql_query=message)
                print(m)
        prev = sorted_m
        time.sleep(1)
        i += 1
    print("INFO: Ending chat display...")
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


if __name__ == '__main__':
    main()