package com.timelink.controllers.tests;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.primefaces.model.DualListModel;

import com.timelink.controllers.AssignEmployeesToTimesheetApprover;
import com.timelink.ejbs.Employee;
import com.timelink.managers.EmployeeManager;

public class AssignEmployeesToTimesheetApproverTest {
	private AssignEmployeesToTimesheetApprover testController;
	private EmployeeManager em;
	
	@Before
	public void setUp() throws Exception {
		this.em = mock(EmployeeManager.class);
		testController = new AssignEmployeesToTimesheetApprover(em);
	}

	@Test
	public void testGetTimesheetApprovers() {
		List<Employee> mockEmpList = Arrays.asList(mock(Employee.class), 
				mock(Employee.class), mock(Employee.class));
		when(em.getAll()).thenReturn(mockEmpList);
		assertEquals(mockEmpList, testController.getTimesheetApprovers());
	}

	@Test
	public void testGetEmployees() {
		Employee mockTSApprover = mock(Employee.class);
		Employee e1 = mock(Employee.class);
		Employee e2 = mock(Employee.class);
		Employee e3 = mock(Employee.class);
		Employee e4 = mock(Employee.class);
		List<Employee> mockEmpSource = Arrays.asList(e1, e2);
		List<Employee> mockEmpTarget = Arrays.asList(e3, e4);
		DualListModel<Employee> mockDL = 
				new DualListModel<Employee>(mockEmpSource, mockEmpTarget);
		
		testController.setSelectedTimesheetApprover(mockTSApprover);
		testController.getEmployees();
		verify(em).findByNotTimesheetApprover(mockTSApprover);
		verify(em).findByTimesheetApprover(mockTSApprover);
		
		testController.setSelectedTimesheetApprover(null);
		testController.getEmployees();
		verify(em, never()).findByTimesheetApprover(e1);
		verify(em, never()).findByNotTimesheetApprover(e2);
	}

	@Test
	public void testSetEmployees() {
		Employee e1 = mock(Employee.class);
		Employee e2 = mock(Employee.class);
		Employee e3 = mock(Employee.class);
		Employee e4 = mock(Employee.class);
		List<Employee> mockEmpSource = Arrays.asList(e1, e2);
		List<Employee> mockEmpTarget = Arrays.asList(e3, e4);
		DualListModel<Employee> mockDL = 
				new DualListModel<Employee>(mockEmpSource, mockEmpTarget);
		
		testController.setSelectedTimesheetApprover(null);
		verify(em , never()).merge(any());
		
		Employee mockSup = mock(Employee.class);
		testController.setSelectedTimesheetApprover(mockSup);
		when(mockSup.getEmployeeId()).thenReturn(2);
		Employee mockSup2 = mock(Employee.class);
		when(e1.getTimesheetApprover()).thenReturn(mockSup2);
		when(e2.getTimesheetApprover()).thenReturn(mockSup2);
		when(mockSup2.getEmployeeId()).thenReturn(2);
		testController.setEmployees(mockDL);
		verify(e3).setTimesheetApprover(mockSup);
		verify(e4).setTimesheetApprover(mockSup);
		verify(em).merge(e3);
		verify(em).merge(e4);
		verify(em).merge(e1);
		verify(em).merge(e2);
		verify(e1).setSupervisor(null);
		verify(e2).setSupervisor(null);
	}

}
