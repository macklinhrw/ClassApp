import random
import datetime
import time

class User:
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
        s = "id: "+self.id + \
            "\nName: "+str(self.name) + \
            "\nNickname: "+self.nickname + \
            "\nDescription: "+str(self.desc) + \
            "\nType: "+self.type
        return s

    def update(self, connection, name=None, nickname=None, birth=None, desc=None,
               type=None, pw=None, email=None, onboard=None):
        if desc is not None:
            change_query = "update users set description='{0}' where id='{1}'".format(desc, self.id)
            self.desc = desc
            connection.cursor.execute(change_query)
            print("INFO: Updated description for user: " + self.name + " to " + self.desc)
        if name is not None:
            change_query = "update users set name='{0}' where id='{1}'".format(name, self.id)
            print("INFO: Updated name for user: " + self.id + " from " + self.name + " to " + name)
            self.name = name
            connection.cursor.execute(change_query)

        # TODO add copy of lines 53-57 for all params to update
        connection.connection.commit()

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


class Class:
    def __init__(self, connection, cid):
        connection.cursor.execute("select * from classes where id='%s'" % cid)
        sql_resp_a = connection.cursor.fetchall()
        sql_resp = sql_resp_a[0]
        self.id = sql_resp['id']
        self.members = sql_resp['members']
        self.type = sql_resp['type']
        self.description = sql_resp['description']
        self.teacher = sql_resp['teacher']
        self.period = sql_resp['period']
        self.school = sql_resp['school']

    def __repr__(self):
        s = "id: "+self.id + \
            "\nDescription: "+self.description + \
            "\nTeacher: "+self.teacher + \
            "\nPeriod: "+self.period + \
            "\nSchool: "+self.school + \
            "\nType: "+self.type
        return s

    def add_student(self, connection, user):
        current_members = self.members
        if user.id not in current_members:
            print(self.id)
            new_mem = current_members + user.id + ";"
            print(new_mem)
            add_query = "UPDATE classes SET members='{0}' WHERE id='{1}'".format(new_mem, self.id)
            connection.cursor.execute(add_query)
            connection.connection.commit()
            self.members = new_mem
            print("Added " + user.nickname + " to class " + self.id)
        else:
            print(user.nickname + " was already in class " + self.id)


def dump_all_user_info(connection):
    sql_select_Query = "select * from users"
    connection.cursor.execute(sql_select_Query)
    records = connection.cursor.fetchall()
    for row in records:
        u = User(connection, row['id'])
        print(u)


def new_user(connection, name, nickname, description, birth, email, password, kind):
    random.seed(time.time())
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
