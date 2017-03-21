package com.timelink.controllers.tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.any;
import com.timelink.controllers.HumanResourceController;
import com.timelink.ejbs.Credentials;
import com.timelink.ejbs.Employee;
import com.timelink.managers.EmployeeManager;
import com.timelink.managers.RoleManager;

import static org.mockito.Mockito.*;

import java.util.Date;

public class HumanResourceControllerTest {

	private HumanResourceController testController;
	private EmployeeManager em;
	private RoleManager rm;
	
	@Before
    public void setUp() {
        em = mock(EmployeeManager.class);
        rm = mock(RoleManager.class);
		testController = new HumanResourceController(em, rm);
    }
	
	@Test
	public void testEdit() {
		Employee mockEmp = mock(Employee.class);
		assertEquals("editemployee", testController.edit(mockEmp));
	}

	@Test
	public void testCancel() {
		assertEquals("humanresources" , testController.cancel());
	}

	@Test
	public void testCreateNewEmployee() {
		testController.setPassword("nasim");
		testController.setConfirmPassword("nasim");
		testController.setVacationAccrual(4);
		testController.setEffectiveFrom(new Date());
		assertEquals(testController.createNewEmployee(), "humanresources");
		testController.setPassword("nasim");
		testController.setConfirmPassword("nasim2");
		assertEquals(testController.createNewEmployee(), null);
		verify(em).persist(any());
		verify(rm).persist(any());
	}

	@Test
	public void testSave() {
		testController.setEditingEmployee(mock(Employee.class));
		testController.setVacationAccrual(4);
		assertEquals("humanresources" , testController.save());
		verify(em).merge(any());
	}

	@Test
	public void testNewPassword() {
		Employee mockEmp = mock(Employee.class);
		Credentials mockCr = mock(Credentials.class);
		testController.setPassword("nasim");
		testController.setConfirmPassword("nasim");
		testController.setEditingEmployee(mockEmp);
		when(mockEmp.getCredentials()).thenReturn(mockCr);
		when(mockCr.getCredentialsId()).thenReturn(1);
		assertEquals(null, testController.newPassword());
		verify(em).merge(any());
		
		
	}

}
