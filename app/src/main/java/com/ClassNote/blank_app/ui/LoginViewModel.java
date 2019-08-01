package com.ClassNote.blank_app.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.ClassNote.blank_app.data.ConnectUser;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<String> loginResult = new MutableLiveData<>();

    LiveData<String> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        ConnectUser cu = new ConnectUser();
        cu.login(username, password, this);
    }

    public void setLoginResult(String result){
        if (!result.equals("failed")) {
            loginResult.setValue(result);
        } else {
            // TODO : implement a loginResult class for access to details about the error
            loginResult.setValue(null);
        }
    }
}
