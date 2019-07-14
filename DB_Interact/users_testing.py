import mysql.connector
from mysql.connector import Error
#You will need to pip install mysql-connector-python
import users_utils
from sql_connect import ConnectMySQL
from users_utils import User


def main():
    connection = ConnectMySQL()
    nick_input = input("Enter your nickname: ")
    pass_input = input("Enter your password: ")
    active_user = User(connection, nickname=nick_input, password=pass_input)
    print(active_user)
    connection.close()


def new_test():
    connection = ConnectMySQL()
    nick = input("Enter your nickname: ")
    password = input("Enter your password: ")
    name = input("Enter your name: ")
    desc = input("Enter your bio description (max 150 words): ")
    if len(desc) > 150:
        desc = ''
    birth = input("Enter your birth date (format YYYY-MM-DD): ")
    email = input("Enter your email: ")
    type = 'student'
    users_utils.new_user(connection, name, nick, desc, birth, email, password, type)


def update_test():
    connection = ConnectMySQL()
    nick_input = input("Enter your nickname: ")
    pass_input = input("Enter your password: ")
    active_user = User(connection, nickname=nick_input, password=pass_input)
    update_input = input("Enter your new description: ")
    active_user.update(connection, desc=update_input)


if __name__ == "__main__":
    main()
