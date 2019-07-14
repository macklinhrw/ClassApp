package com.example.classapp.data;

import com.example.classapp.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {
        String tempUser = "user";
        String tempPass = "pass";

        if(username.equals(tempUser) && password.equals(tempPass))
        {
            try {
                // TODO: handle loggedInUser authentication
                // TODO: Access database for user / pass
                LoggedInUser fakeUser =
                        new LoggedInUser(
                                java.util.UUID.randomUUID().toString(),
                                "Macklin");
                return new Result.Success<>(fakeUser);
            } catch (Exception e) {
                return new Result.Error(new IOException("Error logging in", e));
            }
        }
        return null;
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
