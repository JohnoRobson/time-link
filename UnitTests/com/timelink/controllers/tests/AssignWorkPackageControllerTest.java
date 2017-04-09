package com.timelink.controllers.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.primefaces.model.DualListModel;

import com.timelink.controllers.AssignWorkPackageController;
import com.timelink.ejbs.Employee;
import com.timelink.ejbs.Project;
import com.timelink.ejbs.WorkPackage;
import com.timelink.managers.EmployeeManager;
import com.timelink.managers.ProjectManager;
import com.timelink.managers.WorkPackageManager;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
public class AssignWorkPackageControllerTest {

	private AssignWorkPackageController testController;
	private ProjectManager pm;
	private WorkPackageManager wpm;
	private EmployeeManager em;
	@Before
	public void setUp() throws Exception {
		this.pm = mock(ProjectManager.class);
		this.wpm = mock(WorkPackageManager.class);
		this.em = mock(EmployeeManager.class);
		testController = new AssignWorkPackageController(pm, wpm, em);
	}

	@Test
	public void testGetProjects() {
		List<Project> mockProjList = Arrays.asList(mock(Project.class)
				, mock(Project.class), mock(Project.class));
		when(pm.findAll()).thenReturn(mockProjList);
		assertEquals(mockProjList, testController.getProjects());
	}

	@Test
	public void testGetEmployees() {
		Project mockProj = mock(Project.class);
		WorkPackage mockWP = mock(WorkPackage.class);
		Set<Employee> mockEmpSet = new HashSet<Employee>();
		mockEmpSet.add(mock(Employee.class));
		mockEmpSet.add(mock(Employee.class));
		mockEmpSet.add(mock(Employee.class));
		testController.setSelectedProject(mockProj);
		testController.setSelectedWorkPackage(mockWP);
		when(mockWP.getAssignedEmployees()).thenReturn(mockEmpSet);
		testController.getEmployees();
		verify(em).findByNotInWorkPackage(mockProj, mockWP);
		
		
		Project mockProj2 = mock(Project.class);
		WorkPackage mockWP2 = mock(WorkPackage.class);
		testController.setSelectedProject(null);
		testController.setSelectedWorkPackage(null);
		testController.getEmployees();
		verify(em, never()).findByNotInWorkPackage(mockProj2, mockWP2);
	}

	@Test
	public void testSetEmployees() {
		testController.setSelectedProject(null);
		testController.setSelectedWorkPackage(null);
		Employee e1 = mock(Employee.class);
		Employee e2 = mock(Employee.class);
		Employee e3 = mock(Employee.class);
		Employee e4 = mock(Employee.class);
		List<Employee> mockEmpSource = Arrays.asList(e1, e2);
		List<Employee> mockEmpTarget = Arrays.asList(e3, e4);
		DualListModel<Employee> mockDL = 
				new DualListModel<Employee>(mockEmpSource, mockEmpTarget);
		
		testController.setEmployees(mockDL);
		verify(wpm, never()).merge(any());
		
		Project mockProj = mock(Project.class);
		WorkPackage mockWP = mock(WorkPackage.class);
		testController.setSelectedProject(mockProj);
		testController.setSelectedWorkPackage(mockWP);
		testController.setEmployees(mockDL);
		verify(mockWP).setAssignedEmployees(any());
		verify(wpm).merge(mockWP);
	}

	@Test
	public void testResetWp() {
		WorkPackage wp = new WorkPackage();
		testController.setSelectedWorkPackage(wp);
		testController.resetWp();
		assertNull(testController.getSelectedWorkPackage());
	}

}
