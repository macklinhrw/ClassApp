package com.ClassNote.blank_app.ui;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ClassNote.blank_app.data.ConnectMessages;
import com.ClassNote.blank_app.data.MessageClass;

import java.util.List;

public class MessagesViewModel extends ViewModel {
    private MutableLiveData<List<MessageClass>> messages = new MutableLiveData<>();
    private ConnectMessages messagesConnection;
    private boolean isOpen;

    public MutableLiveData<List<MessageClass>> getMessages(){
        return messages;
    }

    public void updateMessages(List<MessageClass> mes){
        messages.setValue(mes);
    }

    public MessagesViewModel(){
        messagesConnection = new ConnectMessages(this);
        isOpen = false;
    }

    public void addMessages(List<MessageClass> mes){
        List<MessageClass> tempList = messages.getValue();
        tempList.addAll(mes);
        messages.setValue(tempList);
    }

    public void connectNewMessages(String thread, String time){
        isOpen = true;
        messagesConnection.startNewMessagesTask(thread, time, false);
    }

    public void fetchMessages(String thread){
        messagesConnection.loadMessages(thread);
    }

    public void closMessagesTask(){
        messagesConnection.setFinishTask(true);
        isOpen = false;
    }

//    public boolean isChanged(List<MessageClass> mes){
//        if(mes == null || messages.getValue() == null){
//            return false;
//        }
//        MessageClass last = mes.get(mes.size() - 1);
//        List<MessageClass> tempList = messages.getValue();
//        return last.getId() == tempList.get(tempList.size() - 1).getId();
//    }

    public boolean isOpen(){
        return isOpen;
    }
}
