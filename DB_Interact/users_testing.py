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
    users_utils.new_user(connection, 'William Roberts', 'billybob', 'Test student user', '2019-07-13', 'eliasbgilbert@gmail.com', 'pass', 'student')


if __name__ == "__main__":
    main()
