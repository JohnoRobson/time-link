package com.timelink.controllers.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.timelink.Session;
import com.timelink.controllers.LabourReportController;
import com.timelink.controllers.MonthlyReportController;
import com.timelink.managers.LabourGradeManager;
import com.timelink.managers.ProjectManager;
import com.timelink.ejbs.BudgetedWorkPackageWorkDays;
import com.timelink.ejbs.Employee;
import com.timelink.ejbs.EstimatedWorkPackageWorkDays;
import com.timelink.ejbs.LabourGrade;
import com.timelink.ejbs.Project;
import com.timelink.ejbs.WorkPackage;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

public class MonthlyReportControllerTest {
	private MonthlyReportController testController;
	private LabourGradeManager lgm;
	private LabourReportController lrc;
	private ProjectManager pm;
	private Session ses;
	
	@Before
	public void setUp() throws Exception {
		lgm = mock(LabourGradeManager.class);
		lrc = mock(LabourReportController.class);
		pm = mock(ProjectManager.class);
		ses = mock(Session.class);
		testController = new MonthlyReportController(lgm, lrc, pm, ses);
	}

	@Test
	public void testGetProjects() {
		List<Project> listP = Arrays.asList(mock(Project.class), 
									mock(Project.class), mock(Project.class));
		Employee emp = mock(Employee.class);
		when(pm.findByProjectManager(anyInt())).thenReturn(listP);
		when(ses.getCurrentEmployee()).thenReturn(emp);
		when(emp.getEmployeeId()).thenReturn(3);
		
		assertEquals(listP, testController.getProjects());
		
	}

	@Test
	public void testGetWorkPackages_InIFCondition() {
		Project p = mock(Project.class);
		testController.setSelectedProject(p);
		List<WorkPackage> listWP = Arrays.asList(mock(WorkPackage.class), mock(WorkPackage.class), 
				mock(WorkPackage.class));
		when(p.getWorkPackages()).thenReturn(listWP);
		assertEquals(listWP, testController.getWorkPackages());
	}
	
	@Test
	public void testGetWorkPackages_OutsideIFCondition() {
		testController.setSelectedProject(null);
		assertNull(testController.getWorkPackages());
	}

	@Test
	public void testGetBudgeted() {
		Project p = mock(Project.class);
		WorkPackage wp = mock(WorkPackage.class);
		testController.setSelectedProject(p);
		
		LabourGrade l1 = mock(LabourGrade.class);
		LabourGrade l2 = mock(LabourGrade.class);
		LabourGrade l3 = mock(LabourGrade.class);
		List<LabourGrade> list = Arrays.asList(l1, l2, l3);
		when(lgm.getAllLabourGrades()).thenReturn(list);
		when(l1.getLabourGradeId()).thenReturn(1);
		when(l2.getLabourGradeId()).thenReturn(2);
		when(l3.getLabourGradeId()).thenReturn(3);

		BudgetedWorkPackageWorkDays d = mock(BudgetedWorkPackageWorkDays.class);
		when(wp.getPlannedHourFromLabourGrade(anyInt())).thenReturn(d);
		when(d.getManDay()).thenReturn(5);
		
		assertEquals(new Integer(15), testController.getBudgeted(wp));
	}

	@Test
	public void testGetEstimated() {
		Project p = mock(Project.class);
		WorkPackage wp = mock(WorkPackage.class);
		testController.setSelectedProject(p);
		
		LabourGrade l1 = mock(LabourGrade.class);
		LabourGrade l2 = mock(LabourGrade.class);
		LabourGrade l3 = mock(LabourGrade.class);
		List<LabourGrade> list = Arrays.asList(l1, l2, l3);
		when(lgm.getAllLabourGrades()).thenReturn(list);
		when(l1.getLabourGradeId()).thenReturn(1);
		when(l2.getLabourGradeId()).thenReturn(2);
		when(l3.getLabourGradeId()).thenReturn(3);

		EstimatedWorkPackageWorkDays d = mock(EstimatedWorkPackageWorkDays.class);
		when(wp.getEstimatedHourFromLabourGrade(anyInt())).thenReturn(d);
		when(d.getManDay()).thenReturn(5);
		
		assertEquals(new Integer(15), testController.getEstimated(wp));
	}

	@Test
	public void testGetBudgetToComplete() {
		Project p = mock(Project.class);
		WorkPackage wp = mock(WorkPackage.class);
		testController.setSelectedProject(p);
		
		LabourGrade l1 = mock(LabourGrade.class);
		LabourGrade l2 = mock(LabourGrade.class);
		LabourGrade l3 = mock(LabourGrade.class);
		List<LabourGrade> list = Arrays.asList(l1, l2, l3);
		when(lgm.getAllLabourGrades()).thenReturn(list);
		when(l1.getLabourGradeId()).thenReturn(1);
		when(l2.getLabourGradeId()).thenReturn(2);
		when(l3.getLabourGradeId()).thenReturn(3);
		
		when(lrc.getBudgetToComplete(anyInt())).thenReturn(4f);
		
		assertEquals(new Float(12), testController.getBudgetToComplete(wp));
	}

	@Test
	public void testGetTotalBudgeted() {
		when(lrc.getTotalBudgeted()).thenReturn(3f);
		Project p = mock(Project.class);
		testController.setSelectedProject(p);
		WorkPackage wp = mock(WorkPackage.class);
		assertEquals(new Float(3), testController.getTotalBudgeted(wp));
	}

	@Test
	public void testGetTotalEstimated() {
		when(lrc.getTotalEstimated()).thenReturn(3f);
		Project p = mock(Project.class);
		testController.setSelectedProject(p);
		WorkPackage wp = mock(WorkPackage.class);
		assertEquals(new Float(3), testController.getTotalEstimated(wp));
	}

	@Test
	public void testGetTotalBudgetToComplete() {
		Project p = mock(Project.class);
		WorkPackage wp = mock(WorkPackage.class);
		testController.setSelectedProject(p);
		when(lrc.getTotalBudgetedToComplete()).thenReturn(3f);
		assertEquals(new Float(3), testController.getTotalBudgetToComplete(wp));
	}

	@Test
	public void testGetVariancePercent() {
		Project p = mock(Project.class);
		WorkPackage wp = mock(WorkPackage.class);
		testController.setSelectedProject(p);
		when(lrc.getTotalVariancePercent()).thenReturn(3f);
		assertEquals(new Float(3), testController.getVariancePercent(wp));
	}

}
