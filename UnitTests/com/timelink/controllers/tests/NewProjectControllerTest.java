package com.timelink.controllers.tests;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import com.timelink.controllers.NewProjectController;
import com.timelink.ejbs.Employee;
import com.timelink.ejbs.Role;
import com.timelink.managers.EmployeeManager;
import com.timelink.managers.ProjectManager;
import com.timelink.managers.RoleManager;
import com.timelink.enums.RoleEnum;

public class NewProjectControllerTest {
	private NewProjectController testController;
	  private ProjectManager pm;
	  private EmployeeManager em;
	  private RoleManager rm;
	
	@Before
	public void setUp() throws Exception {
		this.pm = mock(ProjectManager.class);
		this.em = mock(EmployeeManager.class);
		this.rm = mock(RoleManager.class);
		testController = new NewProjectController(pm, em, rm);
	}

	@Test
	public void testCreateProject() {
		testController.setProjectManager(1);
		testController.setProjectManagerAssistant(1);
		Employee mockEmp = mock(Employee.class);
		mockEmp.setRoles(Arrays.asList(new Role(RoleEnum.EMPLOYEE)));		
		when(em.find(1)).thenReturn(mockEmp);
		assertEquals("assignemp", testController.createProject());
		verify(rm, atLeast(2)).persist(any());
	}

	@Test
	public void testGetEmployees() {
		List<Employee> mockEmps = Arrays.asList(mock(Employee.class), mock(Employee.class));
		when(em.getAll()).thenReturn(mockEmps);
		assertEquals(mockEmps, testController.getEmployees());
		verify(em).getAll();
	}

}
