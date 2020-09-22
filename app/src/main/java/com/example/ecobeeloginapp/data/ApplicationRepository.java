package com.example.ecobeeloginapp.data;

import com.example.ecobeeloginapp.data.api.ApiClient;
import com.example.ecobeeloginapp.data.api.ApiConstants;
import com.example.ecobeeloginapp.data.api.ApiServiceInterface;
import com.example.ecobeeloginapp.data.model.CatApiResponse;
import com.example.ecobeeloginapp.data.model.LoginAuthResult;
import com.example.ecobeeloginapp.data.model.User;

import java.util.List;

import io.reactivex.Single;

/***
 * The Repository  to cater the requirements for LoginViewModel and HomeViewModel
 */
public class ApplicationRepository {

    private static ApplicationRepository applicationRepository;
    private static LoginDataSource loginDataSource;
    private static ApiServiceInterface apiServiceInterface;

    private ApplicationRepository() {
    }

    public static ApplicationRepository getApplicationRepository() {
        if (applicationRepository == null) {
            applicationRepository = new ApplicationRepository();
        }
        return (applicationRepository);
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
