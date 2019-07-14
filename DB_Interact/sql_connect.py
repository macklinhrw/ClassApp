import mysql.connector
from mysql.connector import Error

class ConnectMySQL:
    def __init__(self):
        try:
            self.connection = mysql.connector.connect(host='localhost',
                                                      database='classnote',
                                                      user='root',
                                                      password='rootpass')
            if self.connection.is_connected():
                self.cursor = self.connection.cursor(buffered=True, dictionary=True)
                self.cursor.execute("select database();")
                print("INFO: MySQL database connection SUCCESSFUL")
        except Error as e:
            print("ERROR: FAILED to connect to MySQL: ", e)

    def __repr__(self):
        if self.connection.is_connected():
            print("INFO: Connected to MySQL database")
        else:
            print("WARNING: Not connected to MySQL")

    def close(self):
        if self.connection.is_connected():
            self.cursor.close()
            self.connection.close()
            print("INFO: MySQL connection has been CLOSED")
        else:
            print("WARNING: MySQL connection was already closed")