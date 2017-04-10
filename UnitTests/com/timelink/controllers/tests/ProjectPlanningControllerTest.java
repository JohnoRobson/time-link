package com.timelink.controllers.tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import com.timelink.Session;
import com.timelink.controllers.ProjectPlanningController;
import com.timelink.ejbs.BudgetedProjectWorkDays;
import com.timelink.ejbs.Employee;
import com.timelink.ejbs.EstimatedWorkPackageWorkDays;
import com.timelink.ejbs.Hours;
import com.timelink.ejbs.LabourGrade;
import com.timelink.ejbs.Project;
import com.timelink.ejbs.WorkPackage;
import com.timelink.managers.BudgetedProjectWorkDaysManager;
import com.timelink.managers.EstimatedWorkPackageWorkDaysManager;
import com.timelink.managers.HoursManager;
import com.timelink.managers.LabourGradeManager;
import com.timelink.managers.ProjectManager;
import com.timelink.managers.WorkPackageManager;
import com.timelink.enums.RoleEnum;
import com.timelink.services.WeekNumberService;
import com.timelink.services.WorkPackageCodeService;

public class ProjectPlanningControllerTest {
	private ProjectPlanningController testController;
	private Session session;
	  private ProjectManager pm;
	  private LabourGradeManager lgm;
	  private BudgetedProjectWorkDaysManager bhm;
	  private EstimatedWorkPackageWorkDaysManager ewm;
	  private WorkPackageCodeService workPackageCodeService;
	  private WeekNumberService weekNumberService;
	  private WorkPackageManager wpm;
	  private HoursManager hm;
	
	
	@Before
    public void setUp() {
        this.session = mock(Session.class);
		this.pm = mock(ProjectManager.class);
        this.lgm = mock(LabourGradeManager.class);
        this.bhm = mock(BudgetedProjectWorkDaysManager.class);
        this.ewm = mock(EstimatedWorkPackageWorkDaysManager.class);
        this.workPackageCodeService = mock(WorkPackageCodeService.class);
        this.weekNumberService = mock(WeekNumberService.class);
        this.wpm = mock(WorkPackageManager.class);
        this.hm = mock(HoursManager.class);
		testController = new ProjectPlanningController(session,pm, lgm, bhm, ewm,
				workPackageCodeService, weekNumberService, wpm, hm);
    }
	

	@Test
	public void testSave() {
		Employee emp = mock(Employee.class);
		HashSet<BudgetedProjectWorkDays> mockBHours = new HashSet<BudgetedProjectWorkDays>();
		mockBHours.add(mock(BudgetedProjectWorkDays.class));
		mockBHours.add(mock(BudgetedProjectWorkDays.class));
		List<Project> mockProjList = Arrays.asList(mock(Project.class), mock(Project.class));
		testController.setBudgetedHours(mockBHours);
		when(this.pm.findByProjectManager(anyInt())).thenReturn(mockProjList);
		when(session.getCurrentEmployee()).thenReturn(emp);
		when(emp.getEmployeeId()).thenReturn(1);
		assertEquals(null, testController.saveChanges());
		verify(bhm, atLeast(2)).merge(any());
	}
	@Test
	public void testGetLastEstimate() {
	 Project mockProj = mock(Project.class);
	 EstimatedWorkPackageWorkDays ewp = mock(EstimatedWorkPackageWorkDays.class);
	 testController.setSelectedProject(mockProj);
	 List<EstimatedWorkPackageWorkDays> mockListEstWPWorkDays = Arrays.asList(mock(EstimatedWorkPackageWorkDays.class), 
			 mock(EstimatedWorkPackageWorkDays.class), mock(EstimatedWorkPackageWorkDays.class));			 
	 when(ewm.findLatest(mockProj, 1)).thenReturn(ewp);
	 assertEquals(new Integer(0) ,testController.getLastEstimate(1));
	 }

	@Test
	public void testGetBudgetedHourByLabourGrade_InCondition() {
		Project mockProj = mock(Project.class);
		LabourGrade lg = mock(LabourGrade.class);
		testController.setSelectedProject(mockProj);
		HashSet<BudgetedProjectWorkDays> mockBHours = new HashSet<BudgetedProjectWorkDays>();
		BudgetedProjectWorkDays bpwd1 = mock(BudgetedProjectWorkDays.class);
		BudgetedProjectWorkDays bpwd2 = mock(BudgetedProjectWorkDays.class);
		testController.setBudgetedHours(mockBHours);
		mockBHours.add(bpwd1);
		mockBHours.add(bpwd2);
		when(lg.getLabourGradeId()).thenReturn(1);
		when(bpwd1.getLabourGrade()).thenReturn(lg);
		when(bpwd2.getLabourGrade()).thenReturn(lg);
		
		assertEquals(bpwd1, testController.getBudgetedHourByLabourGrade(1));
		
	}
	
	@Test
	public void testGetBudgetedHourByLabourGrade_OutSideCondition() {
		testController.setSelectedProject(null);
		HashSet<BudgetedProjectWorkDays> mockBHours = new HashSet<BudgetedProjectWorkDays>();
		BudgetedProjectWorkDays bpwd1 = mock(BudgetedProjectWorkDays.class);
		BudgetedProjectWorkDays bpwd2 = mock(BudgetedProjectWorkDays.class);
		testController.setBudgetedHours(mockBHours);
		mockBHours.add(bpwd1);
		mockBHours.add(bpwd2);
		assertNotEquals(bpwd1, testController.getBudgetedHourByLabourGrade(1));
		assertNotEquals(bpwd2, testController.getBudgetedHourByLabourGrade(1));
		
	}

	@Test
	public void testGetTotalChargedHours_InCondition() {
		Project mockProj = mock(Project.class);
		LabourGrade lg = mock(LabourGrade.class);
		testController.setSelectedProject(mockProj);
		Hours h1 = mock(Hours.class);
		Hours h2 = mock(Hours.class);
		h1.setHour(1.0f);
		h2.setHour(2.0f);
		List<Hours> listHrs = Arrays.asList(h1, h2);
		when(mockProj.getProjectNumber()).thenReturn(2);
		when(hm.findApprovedByLabourGradeInProject(1, 2)).thenReturn(listHrs);
		assertEquals(new Float(3), testController.getTotalChargedHours(1));

	}
	
	@Test
	public void testGetTotalChargedHours_OutSideCondition() {
		testController.setSelectedProject(null);
		assertEquals(new Float(0), testController.getTotalChargedHours(1));
	}

	@Test
	public void testRetriveBudgetedHours_OutSideCondition() {
		BudgetedProjectWorkDays bpwd1 = mock(BudgetedProjectWorkDays.class);
		BudgetedProjectWorkDays bpwd2 = mock(BudgetedProjectWorkDays.class);
		List<BudgetedProjectWorkDays> mockBHours = Arrays.asList(bpwd1, bpwd2);
		when(bhm.find(any())).thenReturn(mockBHours);
		HashSet<BudgetedProjectWorkDays> set = mock(HashSet.class);
		testController.setBudgetedHours(set);
		testController.retriveBudgetedHours();
		verify(set, never()).add(any());
	
	}
	
	@Test
	public void testRetriveBudgetedHours_InSideCondition() {
		LabourGrade lb1 = mock(LabourGrade.class);
		LabourGrade lb2 = mock(LabourGrade.class);
		LabourGrade lb3 = mock(LabourGrade.class);
		List<BudgetedProjectWorkDays> mockBHours = new ArrayList<>();
		List<LabourGrade> listLB = Arrays.asList(lb1, lb2, lb3);
		Project mockProj = mock(Project.class);
		testController.setSelectedProject(mockProj);
		when(bhm.find(mockProj)).thenReturn(mockBHours);
		
		HashSet<BudgetedProjectWorkDays> set = mock(HashSet.class);
		when(set.size()).thenReturn(0);
		testController.setBudgetedHours(set);
		testController.setLabourGrades(listLB);
		
		testController.retriveBudgetedHours();
	}

}
