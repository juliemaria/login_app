package com.example.ecobeeloginapp.data;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.ecobeeloginapp.data.model.LoginAuthResult;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import io.reactivex.Single;

import static org.junit.Assert.assertEquals;


@RunWith(PowerMockRunner.class)
@PrepareForTest({DataUtils.class})
public class LoginDataSourceTest {

    @Before
    public void setUp() {
        //Mocking the Datautils class and expect to return the loginAuthResult on call of login method
        DataUtils dataUtils = PowerMock.createMock(DataUtils.class);
        PowerMock.mockStatic(DataUtils.class);
        EasyMock.expect(DataUtils.getUserNameToValidate()).andReturn("TestUser").anyTimes();
        EasyMock.expect(DataUtils.getPasswordToValidate()).andReturn("TestPassword").anyTimes();
        PowerMock.replayAll(dataUtils);
    }


    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void testForValidUserCredentials() {

        // Calling actual method
        LoginDataSource loginDataSource = LoginDataSource.getLoginDataSource();
        Single<LoginAuthResult> loginAuthResultResponse = loginDataSource.login("TestUser", "TestPassword");

        LoginAuthResult loginAuthResult = loginAuthResultResponse.blockingGet();
        // Checking the values
        assertEquals(loginAuthResult.isAuthenticated(), true);
    }

    @Test
    public void testForInValidUserCredentials() {

        // Calling actual method
        LoginDataSource loginDataSource = LoginDataSource.getLoginDataSource();
        Single<LoginAuthResult> loginAuthResultResponse = loginDataSource.login("TestUser", "TestPasswod");

        LoginAuthResult loginAuthResult = loginAuthResultResponse.blockingGet();
        // Checking the values
        assertEquals(loginAuthResult.isAuthenticated(), false);
    }

}

