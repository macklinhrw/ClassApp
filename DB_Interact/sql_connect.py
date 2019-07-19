try:
    import mysql.connector
    from mysql.connector import Error
except ModuleNotFoundError:
    try:
        import subprocess
        subprocess.call(['pip', 'install', 'mysql-connector-python'])
        import mysql.connector
        from mysql.connector import Error
    except Exception:
        print("ERROR: Could not connect to MySQL")
        print("BECAUSE: Could not pip install mysql-connector-python")
        print("This might be because pip is not installed on this computer.")

class ConnectMySQL:
    def __init__(self, mute=False):
        try:
            ###LOCALHOST (delete eventually, only works on Eli's computer)
            # self.connection = mysql.connector.connect(host='localhost',
            #             #                                           database='classnote',
            #             #                                           user='root',
            #             #                                           password='rootpass')

            ###AWS RDS: may want to change the creds when scaled
            self.connection = mysql.connector.connect(host='classnote.cctd6tsztsfn.us-west-1.rds.amazonaws.com',
                                                      database='ClassNoteDB',
                                                      user='classnote',
                                                      password='macklineli')
            if self.connection.is_connected():
                self.cursor = self.connection.cursor(buffered=True, dictionary=True)
                self.cursor.execute("select database();")
                if mute == False:
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