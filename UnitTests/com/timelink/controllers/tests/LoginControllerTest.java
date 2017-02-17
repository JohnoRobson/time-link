package com.timelink.controllers.tests;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.timelink.Session;
import com.timelink.controllers.LoginController;
import com.timelink.ejbs.Credentials;
import com.timelink.ejbs.Employee;
import com.timelink.managers.CredentialManager;
import com.timelink.managers.EmployeeManager;

public class LoginControllerTest {


    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testLogin() {
        CredentialManager credMgrMock = mock(CredentialManager.class);
        Credentials credMock = mock(Credentials.class);
        EmployeeManager empMgrMock = mock(EmployeeManager.class);
        Session sessionMock = mock(Session.class);

        when(credMgrMock.find(any(), any())).thenReturn(credMock);
        when(credMock.getEmployeeId()).thenReturn(1);
        when(empMgrMock.find(anyInt())).thenReturn(new Employee());
        
        LoginController loginCtrl = new LoginController(credMgrMock, empMgrMock, sessionMock);
        
        loginCtrl.login();
        verify(sessionMock).setCurrentEmployee(any());
    }

}
