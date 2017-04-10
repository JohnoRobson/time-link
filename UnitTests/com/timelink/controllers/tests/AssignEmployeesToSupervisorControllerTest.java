package com.timelink.controllers.tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.primefaces.model.DualListModel;

import com.timelink.controllers.AssignEmployeesToSupervisorController;
import com.timelink.ejbs.Employee;
import com.timelink.managers.EmployeeManager;
import org.primefaces.model.DualListModel;

public class AssignEmployeesToSupervisorControllerTest {
	
	private AssignEmployeesToSupervisorController testController;
	private EmployeeManager em;
	
	@Before
	public void setUp() throws Exception {
		this.em = mock(EmployeeManager.class);
		testController = new AssignEmployeesToSupervisorController(em);
	}

	@Test
	public void testGetSupervisors() {
		List<Employee> mockEmps = Arrays.asList(mock(Employee.class), mock(Employee.class));
		when(em.getAllEmployeesWithRole(any())).thenReturn(mockEmps);
		assertEquals(mockEmps, testController.getSupervisors());
	}
	
	@Test
	public void testGetEmployees() {
		List<Employee> mockEmpSource = Arrays.asList(mock(Employee.class), 
				mock(Employee.class));
		List<Employee> mockEmpTarget = Arrays.asList(mock(Employee.class), 
				mock(Employee.class));
		testController.setSelectedSupervisor(null);
		testController.getEmployees();
		verify(em, never()).findByNotSupervisor(any());
		verify(em, never()).findBySupervisor(any());
		
		Employee mockSup = mock(Employee.class);
		testController.setSelectedSupervisor(mockSup);
		testController.getEmployees();
		verify(em).findByNotSupervisor(mockSup);
		verify(em).findBySupervisor(mockSup);
				
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
		
		testController.setSelectedSupervisor(null);
		verify(em , never()).merge(any());
		
		Employee mockSup = mock(Employee.class);
		testController.setSelectedSupervisor(mockSup);
		when(mockSup.getEmployeeId()).thenReturn(2);
		Employee mockSup2 = mock(Employee.class);
		when(e1.getSupervisor()).thenReturn(mockSup2);
		when(e2.getSupervisor()).thenReturn(mockSup2);
		when(mockSup2.getEmployeeId()).thenReturn(2);
		testController.setEmployees(mockDL);
		verify(e3).setSupervisor(mockSup);
		verify(e4).setSupervisor(mockSup);
		verify(em).merge(e3);
		verify(em).merge(e4);
		verify(em).merge(e1);
		verify(em).merge(e2);
		verify(e1).setSupervisor(null);
		verify(e2).setSupervisor(null);
	}
}
