package com.example.ecobeeloginapp.ui.login.viewmodel;

import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ecobeeloginapp.ApplicationController;
import com.example.ecobeeloginapp.data.LoginRepository;
import com.example.ecobeeloginapp.data.model.LoginAuthResult;
import com.example.ecobeeloginapp.data.model.User;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class LoginViewModel extends ViewModel {
    public MutableLiveData<Boolean> progress = new MutableLiveData<>();
    public MutableLiveData<LoginAuthResult> loginAuthResultMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> validCredentials = new MutableLiveData<>();
    private User user;
    private CompositeDisposable compositeDisposable;
    public String username;
    public String password;

    public LoginViewModel() {
        this.user = new User();
        this.compositeDisposable = new CompositeDisposable();
    }

    public void onLoginClick(View v) {
        if (TextUtils.isEmpty(username)) {
            validCredentials.setValue("User name cannot be empty");
        } else if (TextUtils.isEmpty(password)) {
            validCredentials.setValue("Password cannot be empty");
        } else {
            progress.setValue(true);
            user.setUserName(username);
            user.setPassword(password);
            doLogin(user);
        }
    }


    public void doLogin(User user) {
        compositeDisposable.add(LoginRepository.getLoginRepository().login(user)
                .subscribeOn(ApplicationController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError));
    }


    private void onSuccess(LoginAuthResult loginAuthResult) {
        progress.setValue(false);
        loginAuthResultMutableLiveData.postValue(loginAuthResult);
    }

    private void onError(Throwable error) {
        loginAuthResultMutableLiveData.postValue(null);
    }

    public MutableLiveData<LoginAuthResult> getLoginAuthResultMutableLiveData() {
        return this.loginAuthResultMutableLiveData;
    }

    public MutableLiveData<String> getValidCredentials() {
        return this.validCredentials;
    }

    public MutableLiveData<Boolean> getProgress() {
        return progress;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
