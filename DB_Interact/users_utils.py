import random
import datetime


class User:
    def wrong_creds(self):
        print("Incorrect username or password.")
        self.id = ''
        self.name = ''
        self.nickname = ''
        self.birth = ''
        self.desc = ''
        self.type = ''
        self.pw = ''
        self.email = ''
        self.onboard = ''


    def __init__(self, connection, nickname=None, uid=None, password=None):
        """
        :param connection: ConnectMySQL type class object
        :param nickname or uid: specify one to select the user
        """
        if nickname is not None:
            connection.cursor.execute("select * from users where nickname='%s'" % nickname)
        elif uid is not None:
            connection.cursor.execute("select * from users where id='%s'" % uid)
        sql_resp = connection.cursor.fetchall()
        if len(sql_resp) > 0:
            usersql = sql_resp[0]
            if usersql['password'] == password:
                self.id = usersql['id']
                self.name = usersql['name']
                self.nickname = usersql['nickname']
                self.birth = usersql['birth']
                self.desc = usersql['description']
                self.type = usersql['type']
                self.pw = usersql['password']
                self.email = usersql['email']
                self.onboard = usersql['onboard']
            else:
                self.wrong_creds()
        else:
            self.wrong_creds()


    def __repr__(self):
        s = "id: "+self.id + "\nName: "+self.name + "\nNickname: "+self.nickname + "\nDescription: "+self.desc + "\nType: "+self.type
        return s

    def update(self, connection, name=None, nickname=None, birth=None, desc=None,
               type=None, pw=None, email=None, onboard=None):
        if desc is not None:
            change_query = "update users set description='{0}' where id='{1}'".format(desc, self.id)
            self.desc = desc
            connection.cursor.execute(change_query)
            print("INFO: Updated description for user: " + self.name + " to " + self.desc)
        connection.connection.commit()



def dump_all_user_info(connection):
    sql_select_Query = "select * from users"
    connection.cursor.execute(sql_select_Query)
    records = connection.cursor.fetchall()
    for row in records:
        u = User(connection, row['id'])
        print(u)


def new_user(connection, name, nickname, description, birth, email, password, kind):
    uid = "U" + str(random.randint(1000000000, 9999999999))
    now = datetime.datetime.now()
    onboard = now.strftime("%Y-%m-%d")
    add_query = "INSERT INTO users(id,name,nickname,description,birth,email,password,onboard,type) VALUES " \
                "('{0}', '{1}', '{2}', '{3}', '{4}', '{5}', '{6}', '{7}', '{8}')"\
                .format(uid, name, nickname, description, str(birth), email, password, str(onboard), kind)
    connection.cursor.execute(add_query)
    connection.connection.commit()
    print("ADDED USER:")
    print(User(connection, nickname=nickname, password=password))
