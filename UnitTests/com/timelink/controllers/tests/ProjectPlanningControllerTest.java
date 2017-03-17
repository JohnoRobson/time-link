package com.timelink.controllers.tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.timelink.Session;
import com.timelink.controllers.ProjectPlanningController;
import com.timelink.ejbs.Employee;
import com.timelink.ejbs.Project;
import com.timelink.ejbs.WorkPackage;
import com.timelink.managers.BudgetedHoursManager;
import com.timelink.managers.EmployeeManager;
import com.timelink.managers.LabourGradeManager;
import com.timelink.managers.ProjectManager;
import com.timelink.managers.WorkPackageManager;
import com.timelink.roles.RoleEnum;
import com.timelink.services.WorkPackageCodeService;

public class ProjectPlanningControllerTest {
	private ProjectPlanningController testController;
	private ProjectManager pm;
	private Session ses;
	private LabourGradeManager lgm;
	private EmployeeManager em;
	private WorkPackageManager wpm;
	private BudgetedHoursManager bhm;
	private WorkPackageCodeService workPackageCodeService;
	
	
	@Before
    public void setUp() {
        this.pm = mock(ProjectManager.class);
        this.ses = mock(Session.class);
        this.lgm = mock(LabourGradeManager.class);
        this.em = mock(EmployeeManager.class);
        this.wpm = mock(WorkPackageManager.class);
        this.bhm = mock(BudgetedHoursManager.class);
        this.workPackageCodeService = mock(WorkPackageCodeService.class); 
		testController = new ProjectPlanningController(pm, ses, lgm, em, wpm, 
				bhm, workPackageCodeService);
    }
	
	@Test
	public void testSave() {
		testController.setCurrentProject(mock(Project.class));
		assertEquals(null, testController.save());
		verify(pm).merge(any());
	}

	@Test
	public void testCreateWorkPackage() {
		testController.setResponsibleEngineer(1);
		testController.setCurrentProject(mock(Project.class));
		when(em.find(anyInt())).thenReturn(mock(Employee.class));
		assertEquals(null, testController.createWorkPackage());
	}

	@Test
	public void testEditWorkPackage() {
		WorkPackage mockPackage = mock(WorkPackage.class);
		testController.setCurrentProject(mock(Project.class));
		testController.setEditingWorkPackageId(mockPackage);
		assertEquals(null, testController.editWorkPackage());
		verify(wpm).merge(mockPackage);
	}

	@Test
	public void testGetResponsibleEngineers() {
		List<Employee> mockEmpList = Arrays.asList(mock(Employee.class), mock(Employee.class));
		when(testController.getResponsibleEngineers()).thenReturn(mockEmpList);
		assertEquals(mockEmpList, testController.getResponsibleEngineers());
		verify(em).getAllEmployeesWithRole(RoleEnum.RESPONSIBLE_ENGINEER);
	}

	@Test
	public void testGetAllProjects() {
		testController.getAllProjects();
		verify(pm).findAll();
	}

}
