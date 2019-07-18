ReadMe file for DB_Interact
This directory contains all the interaction with MySQL and python files for sample interactions.

========TABLES=========
USERS: id (U+10 numbers, capacity to expand to 20), name (full), nickname (for login), description (limit 200), birth (date), email, password, onboard (date), type (admin, student, teacher)
CLASSES: id (C+5 numbers, capacity to expand to 20), members, type, description, teacher, period, school
CHAT_THREADS: id (T+20 nums), members, type, description, group_name, in_class
MESSAGES: id: (M+50 nums), sender (user.id), thread (chat_threads.id), datetime, text
SCHOOLS: id: (S+5 nums), name (max 50), description (max 200), location (max 50)


=======COMMAND LINE COMMANDS=======
***You can use several commands per line except:
    >update must have its own line
    >addclass and enterclass cannot be on the same line
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
!dAll: dumps class and user info
!quit: obvious




==========CLASSES==========
Users:
    __init__
    __repr__
    update
    wrong_creds
Classes:
    __init__
    add_student


===========FUNCTIONS========
Users_Utils:
    dump_all_user_info
    new_user
