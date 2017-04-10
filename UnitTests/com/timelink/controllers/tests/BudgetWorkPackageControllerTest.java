package com.timelink.controllers.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.timelink.Session;
import com.timelink.controllers.BudgetWorkPackageController;
import com.timelink.ejbs.BudgetedWorkPackageWorkDays;
import com.timelink.ejbs.Employee;
import com.timelink.ejbs.LabourGrade;
import com.timelink.ejbs.Project;
import com.timelink.ejbs.WorkPackage;
import com.timelink.enums.WorkPackageStatusEnum;
import com.timelink.managers.BudgetedWorkPackageWorkDaysManager;
import com.timelink.managers.EmployeeManager;
import com.timelink.managers.LabourGradeManager;
import com.timelink.managers.ProjectManager;
import com.timelink.managers.WorkPackageManager;
import com.timelink.services.WorkPackageCodeService;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

public class BudgetWorkPackageControllerTest {
	private BudgetWorkPackageController testController;
	private ProjectManager pm;
	private Session ses;
	private LabourGradeManager lgm;
	private EmployeeManager em;
	private WorkPackageManager wpm;
	private BudgetedWorkPackageWorkDaysManager bhm;
	private WorkPackageCodeService workPackageCodeService;
	
	@Before
	public void setUp() throws Exception {
		pm = mock(ProjectManager.class);
		ses = mock(Session.class);
		lgm = mock(LabourGradeManager.class);
		em = mock(EmployeeManager.class);
		wpm = mock(WorkPackageManager.class);
		bhm = mock(BudgetedWorkPackageWorkDaysManager.class);
		workPackageCodeService = mock(WorkPackageCodeService.class);
		testController= new BudgetWorkPackageController(pm, ses, lgm, em , 
										wpm, bhm, workPackageCodeService);
	}

	@Test
	public void testApproveWorkPackage_Inside_IF() {
		WorkPackage wp = mock(WorkPackage.class);
		testController.setEditingWorkPackageId(wp);
		LabourGrade lb1 = mock(LabourGrade.class);
		LabourGrade lb2 = mock(LabourGrade.class);
		LabourGrade lb3 = mock(LabourGrade.class);
		List<LabourGrade> list = Arrays.asList(lb1, lb2, lb3);
		testController.setLabourGrades(list);
		when(lb1.getLabourGradeId()).thenReturn(1);
		when(lb2.getLabourGradeId()).thenReturn(2);
		when(lb3.getLabourGradeId()).thenReturn(3);
		BudgetedWorkPackageWorkDays days = mock(BudgetedWorkPackageWorkDays.class);
		when(wp.getPlannedHourFromLabourGrade(any())).thenReturn(days);
		when(days.getManDay()).thenReturn(4);
		testController.approveWorkPackage();
		verify(days, times(3)).setLabourGrade(any());
		verify(wp).setStatus(WorkPackageStatusEnum.APPROVED);
	}
	
	@Test
	public void testApproveWorkPackage_Inside_Else() {
		WorkPackage wp = mock(WorkPackage.class);
		testController.setEditingWorkPackageId(wp);
		LabourGrade lb1 = mock(LabourGrade.class);
		LabourGrade lb2 = mock(LabourGrade.class);
		LabourGrade lb3 = mock(LabourGrade.class);
		List<LabourGrade> list = Arrays.asList(lb1, lb2, lb3);
		testController.setLabourGrades(list);
		when(lb1.getLabourGradeId()).thenReturn(1);
		when(lb2.getLabourGradeId()).thenReturn(2);
		when(lb3.getLabourGradeId()).thenReturn(3);
		BudgetedWorkPackageWorkDays days = mock(BudgetedWorkPackageWorkDays.class);
		when(wp.getPlannedHourFromLabourGrade(any())).thenReturn(days);
		when(days.getManDay()).thenReturn(0);
		testController.approveWorkPackage();
		verify(days, never()).setLabourGrade(any());
		verify(wp).setStatus(WorkPackageStatusEnum.APPROVED);
		verify(wp, times(3)).removePlannedHourByLabourGrade(any());
	}

	@Test
	public void testSave_Inside_If() {
		WorkPackage wp1 = mock(WorkPackage.class);
		WorkPackage wp2 = mock(WorkPackage.class);
		WorkPackage wp3 = mock(WorkPackage.class);
		BudgetedWorkPackageWorkDays days = mock(BudgetedWorkPackageWorkDays.class);
		when(wp1.getPlannedHourFromLabourGrade(any())).thenReturn(days);
		when(wp2.getPlannedHourFromLabourGrade(any())).thenReturn(days);
		when(wp3.getPlannedHourFromLabourGrade(any())).thenReturn(days);
		when(days.getManDay()).thenReturn(4);
		
		List<WorkPackage> listWP = Arrays.asList(wp1, wp2, wp3);
		LabourGrade lb1 = mock(LabourGrade.class);
		LabourGrade lb2 = mock(LabourGrade.class);
		LabourGrade lb3 = mock(LabourGrade.class);
		List<LabourGrade> list = Arrays.asList(lb1, lb2, lb3);
		
		testController.setLabourGrades(list);
		Project p = mock(Project.class);
		testController.setCurrentProject(p);
		when(p.getWorkPackages()).thenReturn(listWP);
		
		assertNull(testController.save());
		verify(days, times(9)).setLabourGrade(any());
		verify(pm).merge(p);
	}
	
	@Test
	public void testSave_Inside_Else() {
		WorkPackage wp1 = mock(WorkPackage.class);
		WorkPackage wp2 = mock(WorkPackage.class);
		WorkPackage wp3 = mock(WorkPackage.class);
		BudgetedWorkPackageWorkDays days = mock(BudgetedWorkPackageWorkDays.class);
		when(wp1.getPlannedHourFromLabourGrade(any())).thenReturn(days);
		when(wp2.getPlannedHourFromLabourGrade(any())).thenReturn(days);
		when(wp3.getPlannedHourFromLabourGrade(any())).thenReturn(days);
		when(days.getManDay()).thenReturn(0);
		
		List<WorkPackage> listWP = Arrays.asList(wp1, wp2, wp3);
		LabourGrade lb1 = mock(LabourGrade.class);
		LabourGrade lb2 = mock(LabourGrade.class);
		LabourGrade lb3 = mock(LabourGrade.class);
		List<LabourGrade> list = Arrays.asList(lb1, lb2, lb3);
		
		testController.setLabourGrades(list);
		Project p = mock(Project.class);
		testController.setCurrentProject(p);
		when(p.getWorkPackages()).thenReturn(listWP);
		
		assertNull(testController.save());
		verify(days, never()).setLabourGrade(any());
		verify(wp1, times(3)).removePlannedHourByLabourGrade(any());
		verify(wp2, times(3)).removePlannedHourByLabourGrade(any());
		verify(wp3, times(3)).removePlannedHourByLabourGrade(any());
		verify(pm).merge(p);
	}

	@Test
	public void testCreateWorkPackage() {
		WorkPackage wp1 = mock(WorkPackage.class);
		WorkPackage wp2 = mock(WorkPackage.class);
		WorkPackage wp3 = mock(WorkPackage.class);
		BudgetedWorkPackageWorkDays days = mock(BudgetedWorkPackageWorkDays.class);
		when(wp1.getPlannedHourFromLabourGrade(any())).thenReturn(days);
		when(wp2.getPlannedHourFromLabourGrade(any())).thenReturn(days);
		when(wp3.getPlannedHourFromLabourGrade(any())).thenReturn(days);
		when(days.getManDay()).thenReturn(4);
		
		List<WorkPackage> listWP = Arrays.asList(wp1, wp2, wp3);
		LabourGrade lb1 = mock(LabourGrade.class);
		LabourGrade lb2 = mock(LabourGrade.class);
		LabourGrade lb3 = mock(LabourGrade.class);
		List<LabourGrade> list = Arrays.asList(lb1, lb2, lb3);
		Employee engg = mock(Employee.class);
		
		testController.setLabourGrades(list);
		Project p = mock(Project.class);
		testController.setCurrentProject(p);		
		when(p.getWorkPackages()).thenReturn(listWP);
		when(p.getProjectNumber()).thenReturn(1);
		when(em.find(anyInt())).thenReturn(engg);
		testController.setResponsibleEngineer(1);
		
		assertNull(testController.createWorkPackage());
		verify(wpm).persist(any());
	}

	@Test
	public void testEditWorkPackage() {
		WorkPackage wp = mock(WorkPackage.class);
		Project p = mock(Project.class);
		testController.setCurrentProject(p);		
		testController.setEditingWorkPackageId(wp);
		assertNull(testController.editWorkPackage());
		verify(wpm).merge(wp);
	}

	@Test
	public void testGetNonChargedWorkPackages_CurrentProject_Null() {
		testController.setCurrentProject(null);
		assertNull(testController.getNonChargedWorkPackages());
	}
	
	@Test
	public void testGetNonChargedWorkPackages_CurrentProject_NotNull_InsideIF_1() {
		Project p = mock(Project.class);
		testController.setCurrentProject(p);		
		WorkPackage wp1 = mock(WorkPackage.class);
		WorkPackage wp2 = mock(WorkPackage.class);
		WorkPackage wp3 = mock(WorkPackage.class);
		List<WorkPackage> listWP = Arrays.asList(wp1, wp2, wp3);
		when(p.getWorkPackages()).thenReturn(listWP);
		when(wp1.isCharged()).thenReturn(true);
		when(wp2.isCharged()).thenReturn(true);
		when(wp3.isCharged()).thenReturn(true);
		
		assertEquals(0, testController.getNonChargedWorkPackages().size());
	}
	
	@Test
	public void testGetNonChargedWorkPackages_CurrentProject_NotNull_InsideIF_2() {
		Project p = mock(Project.class);
		testController.setCurrentProject(p);		
		WorkPackage wp1 = mock(WorkPackage.class);
		WorkPackage wp2 = mock(WorkPackage.class);
		WorkPackage wp3 = mock(WorkPackage.class);
		List<WorkPackage> listWP = Arrays.asList(wp1, wp2, wp3);
		when(p.getWorkPackages()).thenReturn(listWP);
		when(wp1.isCharged()).thenReturn(true);
		when(wp2.isCharged()).thenReturn(false);
		when(wp3.isCharged()).thenReturn(false);
		
		assertEquals(2, testController.getNonChargedWorkPackages().size());
	}
	
	@Test
	public void testGetNonChargedWorkPackages_CurrentProject_NotNull_OutsideIF() {
		Project p = mock(Project.class);
		testController.setCurrentProject(p);		
		WorkPackage wp1 = mock(WorkPackage.class);
		WorkPackage wp2 = mock(WorkPackage.class);
		WorkPackage wp3 = mock(WorkPackage.class);
		List<WorkPackage> listWP = Arrays.asList(wp1, wp2, wp3);
		when(p.getWorkPackages()).thenReturn(listWP);
		when(wp1.isCharged()).thenReturn(false);
		when(wp2.isCharged()).thenReturn(false);
		when(wp3.isCharged()).thenReturn(false);
		
		assertEquals(3, testController.getNonChargedWorkPackages().size());
	}
}
