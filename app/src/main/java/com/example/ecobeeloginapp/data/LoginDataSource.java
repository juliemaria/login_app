package com.example.ecobeeloginapp.data;

import com.example.ecobeeloginapp.data.model.LoginAuthResult;
import com.example.ecobeeloginapp.data.model.UserInfo;

import io.reactivex.Single;

/***
 * Data source to check the user name and password
 * Using hardcoded user name and values to validate (No API calls)
 */
public class LoginDataSource {
    private static LoginDataSource loginDataSource;

    private LoginDataSource() {
    }

    public static LoginDataSource getLoginDataSource() {
        if (loginDataSource == null) {
            loginDataSource = new LoginDataSource();
        }
        return (loginDataSource);
    }

    public Single<LoginAuthResult> login(String username, String password) {
        LoginAuthResult loginAuthResult = new LoginAuthResult();
        /***
         * Writing the logic to check the username and password
         * Ideally this should happen in the backend API
         * For now, we are mocking it's operation here with hardcoded values.
         */
        if (username.equalsIgnoreCase(DataUtils.getUserNameToValidate())
                && password.equals(DataUtils.getPasswordToValidate())) {
            loginAuthResult.setLoggedInUserInfo(new UserInfo());
            loginAuthResult.setAuthenticated(true);
        } else {
            loginAuthResult.setAuthenticated(false);
            loginAuthResult.setLoggedInUserInfo(null);
        }
        return Single.just(loginAuthResult);
    }
}
