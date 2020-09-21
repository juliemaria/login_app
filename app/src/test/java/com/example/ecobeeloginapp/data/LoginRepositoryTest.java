package com.example.ecobeeloginapp.data;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.ecobeeloginapp.data.api.ApiClient;
import com.example.ecobeeloginapp.data.api.ApiConstants;
import com.example.ecobeeloginapp.data.api.ApiServiceInterface;
import com.example.ecobeeloginapp.data.model.CatApiResponse;
import com.example.ecobeeloginapp.data.model.LoginAuthResult;
import com.example.ecobeeloginapp.data.model.User;

import org.easymock.EasyMock;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;


@RunWith(PowerMockRunner.class)
@PrepareForTest({LoginRepository.class, LoginDataSource.class, ApiServiceInterface.class, ApiClient.class})
@PowerMockIgnore("javax.net.ssl.*")
public class LoginRepositoryTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void testLoginRepository() {

        // Creating user class and setting dummy values
        User user = new User();
        user.setUserName("test1");
        user.setPassword("test");

        //Setting result and message to be expected
        LoginAuthResult loginAuthResult = new LoginAuthResult();
        loginAuthResult.setAuthenticated(true);

        Single<LoginAuthResult> authResult = Single.just(loginAuthResult);

        //Mocking the Datasource class and expect to return the loginAuthResult on call of login method
        LoginDataSource loginDataSource = PowerMock.createMock(LoginDataSource.class);
        EasyMock.expect(loginDataSource.login(user.getUserName(), user.getPassword())).andReturn(authResult).anyTimes();
        PowerMock.mockStatic(LoginDataSource.class);
        EasyMock.expect(LoginDataSource.getLoginDataSource()).andReturn(loginDataSource).anyTimes();
        PowerMock.replayAll(loginDataSource);

        // Calling actual method
        LoginRepository loginRepository = LoginRepository.getLoginRepository();
        Single<LoginAuthResult> loginRepoResponse = loginRepository.login(user);

        // Retrieving the value
        LoginAuthResult loginAuthResultResponse = loginRepoResponse.blockingGet();

        // Checking the values
        assertEquals(loginAuthResultResponse.isAuthenticated(), loginAuthResult.isAuthenticated());
    }

    @Test
    public void testLoginRepositoryWithNullApiService() {

        CatApiResponse cr = new CatApiResponse();
        cr.setId("1");

        List<CatApiResponse> mockCatApiResponses = new ArrayList<CatApiResponse>();
        mockCatApiResponses.add(cr);

        Single<List<CatApiResponse>> mockCatApiResponseList = Single.just(mockCatApiResponses);

        ApiServiceInterface apiServiceInterface = createMock(ApiServiceInterface.class);
        PowerMock.mockStatic(ApiClient.class);
        EasyMock.expect(ApiClient.getApiService()).andReturn(apiServiceInterface).anyTimes();
        EasyMock.expect(apiServiceInterface.fetchCatImage(ApiConstants.CAT_API_KEY)).andReturn(mockCatApiResponseList).anyTimes();
        PowerMock.replayAll(apiServiceInterface);

        LoginRepository loginRepository = LoginRepository.getLoginRepository();
        Single<List<CatApiResponse>> catApiResponse = loginRepository.getCatImageDetails();

        //extract value
        CatApiResponse catApiResponseFromApi = catApiResponse.blockingGet().get(0);

        assertEquals(catApiResponseFromApi.getId(),cr.getId());
    }

}
