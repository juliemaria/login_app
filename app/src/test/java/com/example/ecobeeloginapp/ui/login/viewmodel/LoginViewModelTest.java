package com.example.ecobeeloginapp.ui.login.viewmodel;

import android.view.View;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;

import com.example.ecobeeloginapp.ApplicationController;
import com.example.ecobeeloginapp.data.LoginRepository;
import com.example.ecobeeloginapp.data.model.LoginAuthResult;
import com.example.ecobeeloginapp.data.model.User;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class LoginViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    LoginViewModel loginViewModel;
    @Mock
    LoginRepository loginRepository;
    @Mock
    View view;

    @Mock
    Observer<LoginAuthResult> observer;

    @Mock
    LifecycleOwner lifecycleOwner;
    Lifecycle lifecycle;
    User user = new User();

    @Mock
    ApplicationController applicationController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        loginViewModel = new LoginViewModel();
        user.setUserName("test");
        user.setPassword("test");
        lifecycle = new LifecycleRegistry(lifecycleOwner);
        loginViewModel.loginAuthResultMutableLiveData.observeForever(observer);
    }

    @Test
    public void doInvalidLoginTest() {
        LoginAuthResult loginAuthResult = new LoginAuthResult();
        //loginAuthResult.setMessage("Invalid THARAAN");
        when(loginRepository.login(user)).thenReturn(Single.just(loginAuthResult));

        TestObserver<LoginAuthResult> subscriber = new TestObserver<>();
        TestScheduler scheduler = new TestScheduler();
        Single.just(loginAuthResult)
                .observeOn(scheduler)
                .subscribeOn(scheduler)
                .subscribeWith(subscriber);
        loginViewModel.doLogin(user);
    }

}
