package com.example.ecobeeloginapp.data;

import com.example.ecobeeloginapp.data.api.ApiClient;
import com.example.ecobeeloginapp.data.api.ApiConstants;
import com.example.ecobeeloginapp.data.api.ApiServiceInterface;
import com.example.ecobeeloginapp.data.model.CatApiResponse;
import com.example.ecobeeloginapp.data.model.LoginAuthResult;
import com.example.ecobeeloginapp.data.model.User;

import java.util.List;

import io.reactivex.Single;

public class LoginRepository {

    private static LoginRepository loginRepository;
    private static LoginDataSource loginDataSource;
    private static ApiServiceInterface apiServiceInterface;

    private LoginRepository() {}

    public static LoginRepository getLoginRepository() {
        if (loginRepository == null) {
            loginRepository = new LoginRepository();
        }
        return(loginRepository);
    }

    public Single<LoginAuthResult> login(User user) {
        loginDataSource = LoginDataSource.getLoginDataSource();
        return loginDataSource.login(user.getUserName(), user.getPassword());
    }

    public Single<List<CatApiResponse>> getCatImageDetails() {
        if (apiServiceInterface == null) {
            apiServiceInterface = ApiClient.getApiService();
        }
        return apiServiceInterface.fetchCatImage(ApiConstants.CAT_API_KEY);
    }
}
