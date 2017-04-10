package com.timelink.controllers.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.timelink.controllers.AdminMessageController;
import com.timelink.managers.AdminMessageManager;
import static org.mockito.Mockito.*;

import javax.persistence.PersistenceException;

public class AdminMessageControllerTest {
	private AdminMessageController testController;
	private AdminMessageManager am;
	
	@Before
	public void setUp() throws Exception {
		am = mock(AdminMessageManager.class);
		testController = new AdminMessageController(am);
	}

	@Test
	public void testSaveUserIssue() {
		testController.saveUserIssue();
		verify(am).persist(any());
	}
	
	@Test
	public void testSaveUserIssue_Exception() {
		doThrow(new PersistenceException()).when(this.am).persist(any());
		testController.saveUserIssue();
		verify(am).persist(any());
		
	}
	

}
