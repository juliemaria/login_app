package com.example.ecobeeloginapp.data;

import com.example.ecobeeloginapp.data.model.LoginAuthResult;
import com.example.ecobeeloginapp.data.model.UserInfo;

import io.reactivex.Single;

public class LoginDataSource {
    private static LoginDataSource loginDataSource;

    private LoginDataSource() {}

    public Single<LoginAuthResult> login(String username, String password) {
        LoginAuthResult loginAuthResult = new LoginAuthResult();
        if (username.equalsIgnoreCase(DataUtils.getUserNameToValidate())
                && password.equals(DataUtils.getPasswordToValidate())) {
            loginAuthResult.setLoggedInUserInfo(new UserInfo());
            loginAuthResult.setAuthenticated(true);
            }
        else{
            loginAuthResult.setAuthenticated(false);
            loginAuthResult.setLoggedInUserInfo(null);
        }
        return Single.just(loginAuthResult);
    }


    public static LoginDataSource getLoginDataSource() {
        if (loginDataSource == null) {
            loginDataSource = new LoginDataSource();
        }
        return(loginDataSource);
    }
}
