package com.example.ecobeeloginapp.data.model;

public class LoginAuthResult {

    private UserInfo loggedInUserInfo;
    private boolean isAuthenticated;

    public UserInfo getLoggedInUserInfo() {
        return loggedInUserInfo;
    }

    public void setLoggedInUserInfo(UserInfo loggedInUserInfo) {
        this.loggedInUserInfo = loggedInUserInfo;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }
}
