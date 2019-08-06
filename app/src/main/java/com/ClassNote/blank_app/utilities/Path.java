package com.ClassNote.blank_app.utilities;

public enum Path {

    ACTIVE_USER("com.ClassNote.user.active"),
    ACTIVE_CLASS("com.ClassNote.class.active"),
    ACTIVE_THREAD("com.ClassNote.thread.active"),
    USERNAME("com.ClassNote.login.username"),
    GITHUB_ADDRESS("https://github.com/macklinhrw/ClassApp");

    public String str;

    Path(String str){
        this.str = str;
    }
}
