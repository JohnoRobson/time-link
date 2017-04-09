package com.timelink.controllers.tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import com.timelink.Session;
import com.timelink.controllers.EstimateWorkPackageEffortController;
import com.timelink.ejbs.EstimatedWorkPackageWorkDays;
import com.timelink.ejbs.LabourGrade;
import com.timelink.ejbs.WorkPackage;
import com.timelink.managers.EstimatedWorkPackageWorkDaysManager;
import com.timelink.managers.LabourGradeManager;
import com.timelink.managers.WorkPackageManager;
import com.timelink.services.WeekNumberService;

public class EstimateWorkPackageEffortControllerTest {
	private EstimateWorkPackageEffortController testController;
	private Session session;
	private WorkPackageManager wpm;
	private EstimatedWorkPackageWorkDaysManager ewm;
	private WeekNumberService weekNumberService;
	private LabourGradeManager lgm;
	
	@Before
	public void setUp() throws Exception {
		session = mock(Session.class);
		wpm = mock(WorkPackageManager.class);
		ewm = mock(EstimatedWorkPackageWorkDaysManager.class);
		weekNumberService = mock(WeekNumberService.class);
		lgm = mock(LabourGradeManager.class);
		testController = new EstimateWorkPackageEffortController(session, wpm, ewm, weekNumberService, lgm);
	}

	@Test
	public void testGetWeekYear() {
		when(weekNumberService.getWeekNumber(any())).thenReturn(1);
		EstimatedWorkPackageWorkDays est = mock(EstimatedWorkPackageWorkDays.class);
		when(est.getDateCreated()).thenReturn(new Date());
		String expected = "Week: " + 1 
	      + " Year: " + Calendar.getInstance().get(Calendar.YEAR);
		assertEquals(expected, testController.getWeekYear(est));
	}

	@Test
	public void testReset() {
		
		LabourGrade lb1 = mock(LabourGrade.class);
		LabourGrade lb2 = mock(LabourGrade.class);
		LabourGrade lb3 = mock(LabourGrade.class);
		List<LabourGrade> list = Arrays.asList(lb1, lb2, lb3);
		testController.setLabourGrades(list);
		
		testController.reset();
		assertNull(testController.getSelectedWeek());
		assertNull(testController.getSelectedDate());
	}

	@Test
	public void testGetEstimateFromLabourGrade_InsideIf() {
		LabourGrade lg = mock(LabourGrade.class);
		EstimatedWorkPackageWorkDays days1 = mock(EstimatedWorkPackageWorkDays.class);
		EstimatedWorkPackageWorkDays days2 = mock(EstimatedWorkPackageWorkDays.class);
		HashSet<EstimatedWorkPackageWorkDays> set = new HashSet<>();
		set.add(days1);
		set.add(days2);
		testController.setEstimatedHours(set);
		when(days1.getLabourGrade()).thenReturn(lg);
		when(days2.getLabourGrade()).thenReturn(lg);
		when(lg.getLabourGradeId()).thenReturn(1);
		assertEquals(days2, testController.getEstimateFromLabourGrade(1));
	}
	
	@Test
	public void testGetEstimateFromLabourGrade_OutsideIf() {
		LabourGrade lg = mock(LabourGrade.class);
		LabourGrade lg2 = mock(LabourGrade.class);
		EstimatedWorkPackageWorkDays days1 = mock(EstimatedWorkPackageWorkDays.class);
		EstimatedWorkPackageWorkDays days2 = mock(EstimatedWorkPackageWorkDays.class);
		HashSet<EstimatedWorkPackageWorkDays> set = new HashSet<>();
		set.add(days1);
		set.add(days2);
		testController.setEstimatedHours(set);
		when(days1.getLabourGrade()).thenReturn(lg);
		when(days2.getLabourGrade()).thenReturn(lg);
		when(lg.getLabourGradeId()).thenReturn(2);
		when(lgm.find(2)).thenReturn(lg2);
		assertEquals(lg, testController.getEstimateFromLabourGrade(2).getLabourGrade());
	}

	@Test
	public void testGetNewEstimateWorkPackageFromLabourGrade() {
		LabourGrade lg = mock(LabourGrade.class);
		when(lgm.find(1)).thenReturn(lg);
		HashSet<EstimatedWorkPackageWorkDays> set = mock(HashSet.class);
		testController.setEstimatedHours(set);
		assertNotNull(testController.getNewEstimateWorkPackageFromLabourGrade(1));
		verify(set).add(any());
	}

	@Test
	public void testCreateEstimate() {
		Date date = new Date();
		EstimatedWorkPackageWorkDays days1 = mock(EstimatedWorkPackageWorkDays.class);
		EstimatedWorkPackageWorkDays days2 = mock(EstimatedWorkPackageWorkDays.class);
		HashSet<EstimatedWorkPackageWorkDays> set = new HashSet<>();
		testController.setEstimatedHours(set);
		set.add(days1);
		set.add(days2);
		WorkPackage wp = mock(WorkPackage.class);
		testController.setSelectedWorkPackageCreation(wp);
		testController.setSelectedWeek(1);
		testController.setSelectedYear(2017);
		when(weekNumberService.getDateFromWeekYear(anyInt(), anyInt())).thenReturn(date);
		assertNull(testController.createEstimate());
		verify(wpm).merge(wp);
		verify(ewm, times(2)).persist(any());
		
	}

	@Test
	public void testViewEstimate_InsideIF() {
		String date = "2017-04-04";
		WorkPackage wp = mock(WorkPackage.class);
		testController.setSelectedDate(date);
		testController.setSelectedWorkPackage(wp);
		EstimatedWorkPackageWorkDays days1 = mock(EstimatedWorkPackageWorkDays.class);
		EstimatedWorkPackageWorkDays days2 = mock(EstimatedWorkPackageWorkDays.class);
		List<EstimatedWorkPackageWorkDays> set = Arrays.asList(days1, days2);
		when(ewm.find(any(), any())).thenReturn(set);
		assertEquals(set, testController.viewEstimate());
	}
	
	@Test
	public void testViewEstimate_OutsideIF_Null() {
		String date = null;
		WorkPackage wp = mock(WorkPackage.class);
		testController.setSelectedDate(date);
		testController.setSelectedWorkPackage(wp);
		assertNull(testController.viewEstimate());
	}
	
}
