package com.timelink.controllers.tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.any;
import com.timelink.controllers.HumanResourceController;
import com.timelink.ejbs.Credentials;
import com.timelink.ejbs.Employee;
import com.timelink.ejbs.LabourGrade;
import com.timelink.managers.EmployeeManager;
import com.timelink.managers.LabourGradeManager;
import com.timelink.managers.RoleManager;

import static org.mockito.Mockito.*;

import java.util.Date;

public class HumanResourceControllerTest {

	private HumanResourceController testController;
	private EmployeeManager em;
	private RoleManager rm;
	private LabourGradeManager lgm;
	
	@Before
    public void setUp() {
        em = mock(EmployeeManager.class);
        rm = mock(RoleManager.class);
        lgm = mock(LabourGradeManager.class);
		testController = new HumanResourceController(em, rm, lgm);
    }
	
	@Test
	public void testEdit() {
		Employee mockEmp = mock(Employee.class);
		LabourGrade mockLG = mock(LabourGrade.class);
		when(mockEmp.getLabourGrade()).thenReturn(mockLG);
		when(mockLG.getLabourGradeId()).thenReturn(11111);
		assertEquals("editemployee", testController.edit(mockEmp));
		verify(mockEmp).getEmail();
		verify(mockEmp).getLastName();
		verify(mockEmp).getFirstName();
	}

	@Test
	public void testCancel() {
		assertEquals("humanresources" , testController.cancel());
	}

	@Test
	public void testCreateNewEmployee_InCondition() {
		LabourGrade mockLG = mock(LabourGrade.class);
		testController.setPassword("nasim");
		testController.setConfirmPassword("nasim");
		testController.setVacationAccrual((float) 4);
		testController.setEffectiveFrom(new Date());
		testController.setLabourGrade(1);
		when(lgm.find(1)).thenReturn(mockLG);
		assertEquals(testController.createNewEmployee(), "humanresources");
		testController.setPassword("nasim");
		testController.setConfirmPassword("nasim2");
		testController.setLabourGrade(1);
		when(lgm.find(1)).thenReturn(mockLG);
		assertEquals(testController.createNewEmployee(), null);
		verify(em).persist(any());
		verify(rm).persist(any());
	}
	
	@Test
	public void testCreateNewEmployee_OutsieCondition_Null() {
		testController.setPassword("nasim");
		testController.setConfirmPassword("nasim2");
		assertEquals(testController.createNewEmployee(), null);
		verify(em, never()).persist(any());
		verify(rm, never()).persist(any());
	}

	@Test
	public void testSave() {
		LabourGrade mockLG = mock(LabourGrade.class);
		Employee emp = mock(Employee.class);
		testController.setEditingEmployee(emp);
		testController.setVacationAccrual((float) 4);
		testController.setLabourGrade(1);
		when(lgm.find(1)).thenReturn(mockLG);
		assertEquals("humanresources" , testController.save());
		verify(em).merge(emp);
		verify(lgm).find(1);
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
