package com.timelink.controllers.tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import com.timelink.Session;
import com.timelink.controllers.LabourReportController;
import com.timelink.ejbs.BudgetedWorkPackageWorkDays;
import com.timelink.ejbs.EstimatedWorkPackageWorkDays;
import com.timelink.ejbs.Hours;
import com.timelink.ejbs.LabourGrade;
import com.timelink.ejbs.Project;
import com.timelink.ejbs.WorkPackage;
import com.timelink.managers.BudgetedWorkPackageWorkDaysManager;
import com.timelink.managers.EstimatedWorkPackageWorkDaysManager;
import com.timelink.managers.HoursManager;
import com.timelink.managers.LabourGradeManager;
import com.timelink.managers.WorkPackageManager;

public class LabourReportControllerTest {
	private LabourReportController testController;
	private Session ses;
	private WorkPackageManager wpm;
	private BudgetedWorkPackageWorkDaysManager bwm;
	private EstimatedWorkPackageWorkDaysManager ewm;
    private LabourGradeManager lgm;
	private HoursManager hm;
	
	@Before
	public void setUp() throws Exception {
		ses = mock(Session.class);
		wpm = mock(WorkPackageManager.class);
		bwm = mock(BudgetedWorkPackageWorkDaysManager.class);
		ewm = mock(EstimatedWorkPackageWorkDaysManager.class);
		lgm = mock(LabourGradeManager.class);
		hm = mock(HoursManager.class);
		testController = new LabourReportController(ses, wpm, bwm, ewm, lgm, hm);		
	}

//	@Test
//	public void testGetBudgetedHourByLabourGrade() {
//		fail("Not yet implemented");
//	}
	
	@Test
	public void testGetDate_InsideIF() {
		WorkPackage wp = mock(WorkPackage.class);
		testController.setSelectedWorkPackage(wp);
		EstimatedWorkPackageWorkDays d1 = mock(EstimatedWorkPackageWorkDays.class);
		EstimatedWorkPackageWorkDays d2 = mock(EstimatedWorkPackageWorkDays.class);
		List<EstimatedWorkPackageWorkDays> list = Arrays.asList(d1, d2);
		when(ewm.getAllWithWorkPackageUniqueDate(any())).thenReturn(list);
		Date date1 = mock(Date.class);
		Date date2 = mock(Date.class);
		when(d1.getDateCreated()).thenReturn((date1));
		when(d2.getDateCreated()).thenReturn((date2));
		when(date1.after(any())).thenReturn(true);
		assertEquals(date1, testController.getDate());
	}
	
	@Test
	public void testGetDate_OutsideIF_Null() {
		testController.setSelectedWorkPackage(null);
		assertNull(testController.getDate());
	}

	@Test
	public void testGetBudgetToComplete_InsideIF() {
		WorkPackage wp = mock(WorkPackage.class);
		testController.setSelectedWorkPackage(wp);
		Hours h1 = new Hours();
		Hours h2 = new Hours();
		Hours h3 = new Hours();
		h1.setHour(5f);
		h2.setHour(5f);
		h3.setHour(6f);
		List<Hours> list = Arrays.asList(h1, h2, h3);
		Project proj = mock(Project.class);
		when(wp.getProject()).thenReturn(proj);
		when(proj.getProjectNumber()).thenReturn(111);
		when(wp.getWorkPackageId()).thenReturn(111);
		when(hm.find(anyInt(), anyInt(), anyInt())).thenReturn(list);
		EstimatedWorkPackageWorkDays est = mock(EstimatedWorkPackageWorkDays.class);
		when(wp.getEstimatedHourFromLabourGrade(anyInt())).thenReturn(est);
		when(est.getManDay()).thenReturn(4);
		assertEquals(new Float(6), testController.getBudgetToComplete(1));
	}
	
	@Test
	public void testGetBudgetToComplete_OutsideIF_Null() {
		testController.setSelectedWorkPackage(null);
		assertNull(testController.getBudgetToComplete(1));
	}

	@Test
	public void testGetManDaysForLabourGrade_InsideIF() {
		WorkPackage wp = mock(WorkPackage.class);
		testController.setSelectedWorkPackage(wp);
		BudgetedWorkPackageWorkDays d = mock(BudgetedWorkPackageWorkDays.class);
		when(wp.getPlannedHourFromLabourGrade(1)).thenReturn(d);
		when(d.getManDay()).thenReturn(5);
		assertEquals(new Integer(5), testController.getManDaysForLabourGrade(1));
	}
	
	@Test
	public void testGetManDaysForLabourGrade_OutsideIF_Null() {
		testController.setSelectedWorkPackage(null);
		assertNull(testController.getManDaysForLabourGrade(1));
	}

	@Test
	public void testGetVariance_InsideIf() {
		WorkPackage wp = mock(WorkPackage.class);
		testController.setSelectedWorkPackage(wp);
		BudgetedWorkPackageWorkDays d = mock(BudgetedWorkPackageWorkDays.class);
		when(wp.getPlannedHourFromLabourGrade(1)).thenReturn(d);
		when(d.getManDay()).thenReturn(5);
		
		Hours h1 = new Hours();
		Hours h2 = new Hours();
		Hours h3 = new Hours();
		h1.setHour(5f);
		h2.setHour(5f);
		h3.setHour(6f);
		List<Hours> list = Arrays.asList(h1, h2, h3);
		Project proj = mock(Project.class);
		when(wp.getProject()).thenReturn(proj);
		when(proj.getProjectNumber()).thenReturn(111);
		when(wp.getWorkPackageId()).thenReturn(111);
		when(hm.find(anyInt(), anyInt(), anyInt())).thenReturn(list);
		EstimatedWorkPackageWorkDays est = mock(EstimatedWorkPackageWorkDays.class);
		when(wp.getEstimatedHourFromLabourGrade(anyInt())).thenReturn(est);
		when(est.getManDay()).thenReturn(4);
		
		assertEquals(new Float(-1), testController.getVariance(1));
	}
	
	@Test
	public void testGetVariance_OutsideIf_Null() {
		testController.setSelectedWorkPackage(null);
		assertNull(testController.getVariance(1));
	}

	@Test
	public void testGetVariancePercent_InsideIf() {
		WorkPackage wp = mock(WorkPackage.class);
		testController.setSelectedWorkPackage(wp);
		BudgetedWorkPackageWorkDays d = mock(BudgetedWorkPackageWorkDays.class);
		when(wp.getPlannedHourFromLabourGrade(anyInt())).thenReturn(d);
		when(d.getManDay()).thenReturn(5);
		
		Hours h1 = new Hours();
		Hours h2 = new Hours();
		Hours h3 = new Hours();
		h1.setHour(5f);
		h2.setHour(5f);
		h3.setHour(6f);
		List<Hours> list = Arrays.asList(h1, h2, h3);
		Project proj = mock(Project.class);
		when(wp.getProject()).thenReturn(proj);
		when(proj.getProjectNumber()).thenReturn(111);
		when(wp.getWorkPackageId()).thenReturn(111);
		when(hm.find(anyInt(), anyInt(), anyInt())).thenReturn(list);
		EstimatedWorkPackageWorkDays est = mock(EstimatedWorkPackageWorkDays.class);
		when(wp.getEstimatedHourFromLabourGrade(anyInt())).thenReturn(est);
		when(est.getManDay()).thenReturn(4);
		
		assertEquals(new Float(-0.2), testController.getVariancePercent(1));
	}
	
	@Test
	public void testGetVariancePercent_OutsideIf() {
		testController.setSelectedWorkPackage(null);
		assertNull(testController.getVariancePercent(1));
	}


	@Test
	public void testGetTotalBudgeted_InsideIf() {
		WorkPackage wp = mock(WorkPackage.class);
		testController.setSelectedWorkPackage(wp);
		
		LabourGrade l1 = mock(LabourGrade.class);
		LabourGrade l2 = mock(LabourGrade.class);
		LabourGrade l3 = mock(LabourGrade.class);
		List<LabourGrade> list = Arrays.asList(l1, l2, l3);
		when(lgm.getAllLabourGrades()).thenReturn(list);
		
		BudgetedWorkPackageWorkDays d = mock(BudgetedWorkPackageWorkDays.class);
		when(wp.getPlannedHourFromLabourGrade(anyInt())).thenReturn(d);
		when(d.getManDay()).thenReturn(5);
		
		when(l1.getLabourGradeId()).thenReturn(1);
		when(l2.getLabourGradeId()).thenReturn(2);
		when(l3.getLabourGradeId()).thenReturn(3);
		when(l1.getCostRate()).thenReturn(1f);
		when(l2.getCostRate()).thenReturn(2f);
		when(l3.getCostRate()).thenReturn(3f);
		
		assertEquals(new Float(30), testController.getTotalBudgeted());
	}
	
	@Test
	public void testGetTotalBudgeted_OutsideIf() {
		testController.setSelectedWorkPackage(null);
		assertNull(testController.getTotalBudgeted());
	}

	@Test
	public void testGetTotalEstimated() {
		WorkPackage wp = mock(WorkPackage.class);
		testController.setSelectedWorkPackage(wp);
		
		LabourGrade l1 = mock(LabourGrade.class);
		LabourGrade l2 = mock(LabourGrade.class);
		LabourGrade l3 = mock(LabourGrade.class);
		List<LabourGrade> list = Arrays.asList(l1, l2, l3);
		when(lgm.getAllLabourGrades()).thenReturn(list);
		EstimatedWorkPackageWorkDays d = mock(EstimatedWorkPackageWorkDays.class);
		when(wp.getEstimatedHourFromLabourGrade(anyInt())).thenReturn(d);
		when(d.getManDay()).thenReturn(5);
		
		when(l1.getLabourGradeId()).thenReturn(1);
		when(l2.getLabourGradeId()).thenReturn(2);
		when(l3.getLabourGradeId()).thenReturn(3);
		when(l1.getCostRate()).thenReturn(1f);
		when(l2.getCostRate()).thenReturn(2f);
		when(l3.getCostRate()).thenReturn(3f);
		
		assertEquals(new Float(30), testController.getTotalEstimated());
	}
	
	@Test
	public void testGetTotalEstimated_OutsideIF_Null() {
		testController.setSelectedWorkPackage(null);
		assertNull(testController.getTotalEstimated());
	}

	@Test
	public void testGetTotalBudgetedToComplete_InsideIF() {
		WorkPackage wp = mock(WorkPackage.class);
		testController.setSelectedWorkPackage(wp);
		
		LabourGrade l1 = mock(LabourGrade.class);
		LabourGrade l2 = mock(LabourGrade.class);
		LabourGrade l3 = mock(LabourGrade.class);
		List<LabourGrade> list = Arrays.asList(l1, l2, l3);
		when(lgm.getAllLabourGrades()).thenReturn(list);
		
		when(l1.getLabourGradeId()).thenReturn(1);
		when(l2.getLabourGradeId()).thenReturn(2);
		when(l3.getLabourGradeId()).thenReturn(3);
		when(l1.getCostRate()).thenReturn(1f);
		when(l2.getCostRate()).thenReturn(2f);
		when(l3.getCostRate()).thenReturn(3f);
		
		Hours h1 = new Hours();
		Hours h2 = new Hours();
		Hours h3 = new Hours();
		h1.setHour(5f);
		h2.setHour(5f);
		h3.setHour(6f);
		List<Hours> listh = Arrays.asList(h1, h2, h3);
		Project proj = mock(Project.class);
		when(wp.getProject()).thenReturn(proj);
		when(proj.getProjectNumber()).thenReturn(111);
		when(wp.getWorkPackageId()).thenReturn(111);
		when(hm.find(anyInt(), anyInt(), anyInt())).thenReturn(listh);
		EstimatedWorkPackageWorkDays est = mock(EstimatedWorkPackageWorkDays.class);
		when(wp.getEstimatedHourFromLabourGrade(anyInt())).thenReturn(est);
		when(est.getManDay()).thenReturn(4);
		
		assertEquals(new Float(36), testController.getTotalBudgetedToComplete());
	}
	
	@Test
	public void testGetTotalBudgetedToComplete_OutsideIF() {
		testController.setSelectedWorkPackage(null);
		assertNull(testController.getTotalBudgetedToComplete());
	}

	@Test
	public void testGetTotalVariance() {
		WorkPackage wp = mock(WorkPackage.class);
		testController.setSelectedWorkPackage(wp);
		BudgetedWorkPackageWorkDays d = mock(BudgetedWorkPackageWorkDays.class);
		when(wp.getPlannedHourFromLabourGrade(1)).thenReturn(d);
		when(d.getManDay()).thenReturn(5);
		
		Hours h1 = new Hours();
		Hours h2 = new Hours();
		Hours h3 = new Hours();
		h1.setHour(5f);
		h2.setHour(5f);
		h3.setHour(6f);
		List<Hours> list = Arrays.asList(h1, h2, h3);
		Project proj = mock(Project.class);
		when(wp.getProject()).thenReturn(proj);
		when(proj.getProjectNumber()).thenReturn(111);
		when(wp.getWorkPackageId()).thenReturn(111);
		when(hm.find(anyInt(), anyInt(), anyInt())).thenReturn(list);
		EstimatedWorkPackageWorkDays est = mock(EstimatedWorkPackageWorkDays.class);
		when(wp.getEstimatedHourFromLabourGrade(anyInt())).thenReturn(est);
		when(est.getManDay()).thenReturn(4);
		
		LabourGrade l1 = mock(LabourGrade.class);
		LabourGrade l2 = mock(LabourGrade.class);
		LabourGrade l3 = mock(LabourGrade.class);
		List<LabourGrade> listL = Arrays.asList(l1, l2, l3);
		when(lgm.getAllLabourGrades()).thenReturn(listL);
		
		when(l1.getLabourGradeId()).thenReturn(1);
		when(l2.getLabourGradeId()).thenReturn(2);
		when(l3.getLabourGradeId()).thenReturn(3);
		when(l1.getCostRate()).thenReturn(1f);
		when(l2.getCostRate()).thenReturn(2f);
		when(l3.getCostRate()).thenReturn(3f);
		
		BudgetedWorkPackageWorkDays dd = mock(BudgetedWorkPackageWorkDays.class);
		when(wp.getPlannedHourFromLabourGrade(anyInt())).thenReturn(dd);
		when(d.getManDay()).thenReturn(5);
		
		assertEquals(new Float(-36), testController.getTotalVariance());
	}
	
	@Test
	public void testGetTotalVariance_OutsideIF_Null() {
		testController.setSelectedWorkPackage(null);
		assertNull(testController.getTotalVariance());
	}

	@Test
	public void testGetTotalVariancePercent_InsideIF() {
		WorkPackage wp = mock(WorkPackage.class);
		testController.setSelectedWorkPackage(wp);
		BudgetedWorkPackageWorkDays d = mock(BudgetedWorkPackageWorkDays.class);
		when(wp.getPlannedHourFromLabourGrade(anyInt())).thenReturn(d);
		when(d.getManDay()).thenReturn(5);
		
		Hours h1 = new Hours();
		Hours h2 = new Hours();
		Hours h3 = new Hours();
		h1.setHour(5f);
		h2.setHour(5f);
		h3.setHour(6f);
		List<Hours> list = Arrays.asList(h1, h2, h3);
		Project proj = mock(Project.class);
		when(wp.getProject()).thenReturn(proj);
		when(proj.getProjectNumber()).thenReturn(111);
		when(wp.getWorkPackageId()).thenReturn(111);
		when(hm.find(anyInt(), anyInt(), anyInt())).thenReturn(list);
		EstimatedWorkPackageWorkDays est = mock(EstimatedWorkPackageWorkDays.class);
		when(wp.getEstimatedHourFromLabourGrade(anyInt())).thenReturn(est);
		when(est.getManDay()).thenReturn(4);
		
		LabourGrade l1 = mock(LabourGrade.class);
		LabourGrade l2 = mock(LabourGrade.class);
		LabourGrade l3 = mock(LabourGrade.class);
		List<LabourGrade> listL = Arrays.asList(l1, l2, l3);
		when(lgm.getAllLabourGrades()).thenReturn(listL);
		
		when(l1.getLabourGradeId()).thenReturn(1);
		when(l2.getLabourGradeId()).thenReturn(2);
		when(l3.getLabourGradeId()).thenReturn(3);
		when(l1.getCostRate()).thenReturn(1f);
		when(l2.getCostRate()).thenReturn(2f);
		when(l3.getCostRate()).thenReturn(3f);
		
		assertEquals(new Float(-0.2), testController.getTotalVariancePercent());
	}
	
	@Test
	public void testGetTotalVariancePercent_OutsideIF() {
		testController.setSelectedWorkPackage(null);
		assertNull(testController.getTotalVariancePercent());
	}
	

}
