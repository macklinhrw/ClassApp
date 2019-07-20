import random
import time

class Thread:
    def __init__(self, connection, title, mute=False):
        #TODO eventually merge this over to select by id, currently uses group_name for simplicity
        connection.cursor.execute("select * from chat_threads where group_name='{0}'".format(title))
        sql_fa = connection.cursor.fetchall()
        if len(sql_fa) > 0:
            sql = sql_fa[0]
            self.id = sql['id']
            self.members = sql['members']
            self.type = sql['type']
            self.description = sql['description']
            self.group_name = sql['group_name']
            self.in_class = sql['in_class']
        else:
            if not mute:
                print("ERROR: Thread did not exist.")
            self.id = ''
            self.members = ''
            self.type = ''
            self.description = ''
            self.group_name = ''
            self.in_class = ''

    def __repr__(self):
        s = "id: "+self.id + \
            "\nType: "+self.type + \
            "\nDescription: "+str(self.description) + \
            "\nName: "+str(self.group_name) + \
            "\nClass: "+self.in_class
        return s

    def adduser(self, connection, nickname):
        try:
            connection.cursor.execute("select * from users where nickname='%s'" % nickname)
            user_to_add = connection.cursor.fetchall()[0]
            new_mem = self.members + user_to_add['id'] + ';'
            connection.cursor.execute("update chat_threads set members='{0}' where id='{1}'".format(new_mem, self.id))
            connection.connection.commit()
            self.members = new_mem
        except:
            print("ERROR: Could not find that user to add. ")



class Message:
    def __init__(self, text=None, sender=None, thread=None, type='new', sql_query=None, include_timestamp=False):
        """
        :param type: string 'new' or 'disp'
        if new specify text (string), sender (active_user, User object), thread (active_thread, Thread object)
        if disp only give sql_query (mysql query object from query of messages with params)
        """
        self.include_timestamp = include_timestamp
        if type == 'new':
            random.seed(time.time())
            self.id = "M"
            i = 0
            while i < 30:
                self.id += str(random.randint(0, 9))
                i += 1
            # TODO check if id exists
            self.sender = sender.id
            self.thread = thread.id
            self.datetime = time.strftime('%Y-%m-%d %H:%M:%S')
            self.text = text
            self.author = sender.nickname
            self.rank = 'new'
        elif type == 'disp':
            self.id = sql_query['id']
            self.sender = sql_query['sender']
            self.thread = sql_query['thread']
            self.datetime = sql_query['datetime']
            self.text = sql_query['text']
            self.author = sql_query['author']
            self.rank = sql_query['rank']

    def __repr__(self):
        s = ">> " + self.author + ": " + self.text
        if self.include_timestamp:
            s += " (" + str(self.datetime) + ")"
        return s

    def add(self, connection):
        query = "INSERT INTO messages(id,sender,thread,datetime,text,author) VALUES" \
                "('{0}','{1}','{2}','{3}','{4}','{5}')"\
                .format(self.id, self.sender, self.thread, self.datetime, self.text, self.author)
        connection.cursor.execute(query)
        connection.connection.commit()


def newthread(connection, active_user, type, active_class, group_name=None, dm_to=None, description=''):
    """
    :param connection:
    :param active_user: User object
    :param type: 'direct', 'group', 'all'
    :param group_name: only pass for group or all
    :param active_class: Class object
    :param dm_to: User object, only if type=direct
    :param description: optional, only for group
    """
    members = active_user.id + ';'
    if type == 'direct':
        members += dm_to.id + ';'
        group_name = active_user.nickname + dm_to.nickname
    tid = "T"
    i = 0
    while i < 20:
        tid += str(random.randint(0, 9))
        i += 1
    in_class = active_class.id
    query = "INSERT INTO chat_threads(id, members, type, description, group_name, in_class) VALUES" \
            "('{0}','{1}','{2}','{3}','{4}','{5}')"\
            .format(tid, members, type, description, group_name, in_class)
    connection.cursor.execute(query)
    connection.connection.commit()
