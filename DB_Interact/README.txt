ReadMe file for DB_Interact
This directory contains all the interaction with MySQL and python files for sample interactions.

==========INSTRUCTIONS TO SET UP MESSAGING==========
RUN IN ONE TERMINAL WINDOW: python DB_Interact/users_testing.py or python3 DB_Interact/users_testing.py
----hopefully mysql and pip installation is not necessary, if it is, this will need to be updated
Enter commands in this order: (all commands start with ! and params start with -
!newuser (follow prompts)
!login (login as the user you just created)
!addclass -C##### (use the id of the class you are joining. for now this is probably C00000)
!enterclass -C#####
!dAll (prints to make sure you are logged in correctly)
someone else will need to !addtothread you in (the first time)
!thread -group
when prompted, input group name (person who ran !addtothread will give it to you)
in ANOTHER TERMINAL WINDOW (keep the first open)
RUN: python DB_Interact/message_display.py
login as user you created when prompted, specify class C##### and thread (group name from before)
this window will show all messages in thread
IN FIRST WINDOW:
!m
type messages, enter to send
type !~ to leave messaging mode and return to command line
!quit to close MySQL session and end interaction
congratulations! you set up basic texting! read the rest of the docs for more info on functions, etc.
===========================================


========TABLES=========
USERS: id (U+10 numbers, capacity to expand to 20), name (full), nickname (for login), description (limit 200 chars), birth (date), email, password, onboard (date), type (admin, student, teacher)
CLASSES: id (C+5 numbers, capacity to expand to 20), members, type, description, teacher, section, school
CHAT_THREADS: id (T+20 nums), members (uid separated by ;), type (direct, group, all), description, group_name, in_class (cid)
MESSAGES: id: (M+50 nums), sender (user.id), thread (chat_threads.id), datetime, text, rank (not in use)
SCHOOLS: id: (S+5 nums), name (max 50), description (max 255), location (max 50)


=======ALL COMMANDS=======
RUN: python DB_Interact/users_testing.py to use these
***You can use several commands per line except:
    >update must have its own line
    >addclass and enterclass cannot be on the same line
    >!thread !m and .m must be their own lines
    >they will always run in this order:
!newuser: starts make new user flow, will need to log in after
!login: log in as user (asks for credentials). for testing, use billybob/pass (student) or rudy/pass (admin)
!easy: logs in as elig. do not use this if you are not Eli please (use billybob or rudy)
!update: update user info pass fields to be updated as -field. Currently works with:
    -description
    -name
!dUser: dumps active user info
!addclass: -C##### to specify class, adds active user to class
!enterclass: -C##### to specify class, selects class as active
!dClass: dumps active class info
!dAll: dumps class, thread and user info
!thread: -groupname (can have spaces) selects conversation thread
!m: several message inputs
.m: one message input
!addtothread: add another user to the active thread
!newdm -user: starts a dm track with -user
!dm -user: selects dm track with user - as active thread
!newthread: enters prompt sequence for new group chat
!quit: obvious


==========CLASSES==========
User:
    __init__
    __repr__
    update
    wrong_creds
Class:
    __init__
    __repr__
    add_student
Thread:
    __init__
    __repr__
    adduser
Message:
    __init__
    __repr__
    add


===========FUNCTIONS========
Users_Utils:
    dump_all_user_info
    new_user



=========TODO=========
Make new class function
Make new chat thread function (different for direct, group or all)
Improve select thread (if direct, auto select based on input of usr to msg. if group, ask group name. if all, auto enter all)
Periodically change message rank to old, then delete based on timestamp
