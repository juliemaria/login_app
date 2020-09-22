package com.example.ecobeeloginapp.data.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class UserTest {
    @Mock
    User user;
    private final String username = "test username";
    private final String password = "test password";

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUserName() {
        Mockito.when(user.getUserName()).thenReturn(username);
        Assert.assertEquals("test username", user.getUserName());
    }

    @Test
    public void testPassword() {
        Mockito.when(user.getPassword()).thenReturn(password);
        Assert.assertEquals("test password", user.getPassword());
    }
}
