package com.ClassNote.blank_app.data;

public interface AsyncResponse<T> {
    void processFinish(T result);
}
