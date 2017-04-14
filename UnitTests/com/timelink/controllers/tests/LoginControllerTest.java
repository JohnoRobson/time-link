package com.timelink.controllers.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.timelink.Session;
import com.timelink.controllers.LoginController;
import com.timelink.ejbs.Credentials;
import com.timelink.ejbs.Employee;
import com.timelink.managers.CredentialManager;
import com.timelink.managers.EmployeeManager;
import static org.mockito.Mockito.*;
public class LoginControllerTest {

	private LoginController testController;
	private CredentialManager cm;
	private EmployeeManager em;
	private Session ss;
	
	@Before
	public void setUp() throws Exception {
		cm = mock(CredentialManager.class);
		em = mock(EmployeeManager.class);
		ss = mock(Session.class);
		testController = new LoginController(cm, em, ss);
	}

	@Test
	public void testValidateUser() {
		Credentials mockCred = mock(Credentials.class);
		
		when(cm.find(any(), any())).thenReturn(mockCred);
		assertTrue(testController.validateUser());
		
		when(cm.find(any(), any())).thenReturn(null);
		assertFalse(testController.validateUser());
	}
/*
	@Test
	public void testLogin() {
		Credentials credMock = mock(Credentials.class);
        
        when(cm.find(any(), any())).thenReturn(credMock);
        when(credMock.getEmployeeId()).thenReturn(1);
        when(em.find(anyInt())).thenReturn(new Employee());  
        
        //test returning 'home'
        assertEquals(testController.login(), "home");
        verify(ss).setCurrentEmployee(any());
        
        //test returning 'null'
        when(cm.find(any(), any())).thenReturn(null);
        assertEquals(testController.login(), "null");
        
	}
*/
}
