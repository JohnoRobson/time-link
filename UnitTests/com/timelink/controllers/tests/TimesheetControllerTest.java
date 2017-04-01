package com.timelink.controllers.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.timelink.Session;
import com.timelink.enums.TimesheetStatus;
import com.timelink.controllers.TimesheetController;
import com.timelink.ejbs.Employee;
import com.timelink.ejbs.Timesheet;
import com.timelink.ejbs.TimesheetRow;
import com.timelink.ejbs.WorkPackage;
import com.timelink.managers.ProjectManager;
import com.timelink.managers.TimesheetManager;
import com.timelink.managers.WorkPackageManager;
import com.timelink.services.WeekNumberService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
public class TimesheetControllerTest {
	
	private TimesheetController testController;
	private TimesheetManager tm;
	private WorkPackageManager wpm;
	private Session ses;
	private ProjectManager pm;
	private WeekNumberService wns;
	
	@Before
	public void setUp() throws Exception {
		this.tm = mock(TimesheetManager.class);
		this.wpm = mock(WorkPackageManager.class);
		this.ses = mock(Session.class);
		this.pm = mock(ProjectManager.class);
		this.wns = mock(WeekNumberService.class);
		testController = new TimesheetController(tm, wpm, ses, pm, wns);
	}

	@Test
	public void testSave() {
		Timesheet mockTimesheet = mock(Timesheet.class);
		testController.setSelectedTimesheet(mockTimesheet);
		assertEquals(testController.save(), null);
		verify(tm).merge(mockTimesheet);
	}

	@Test
	public void testSubmit() {
		Timesheet mockTimesheet = mock(Timesheet.class);
		testController.setSelectedTimesheet(mockTimesheet);
		when(mockTimesheet.getStatus()).thenReturn(TimesheetStatus.NOTSUBMITTED.toString());
		assertEquals(null, testController.submit());
		verify(mockTimesheet).calculateFlexAndOvertime();
		verify(mockTimesheet).setStatus(any());
	}


	@Test
	public void testAddTimesheet() {
		testController.setSelectedTimesheet(new Timesheet());
		testController.getSelectedTimesheet()
				.setDate(new Date(Calendar.getInstance().getTime().getTime()));
		assertNull(testController.addTimesheet());
		verify(tm, never()).persist(any());
		verify(tm, never()).findLatest(any());
		verify(ses, never()).getCurrentEmployee();
		
		testController.getSelectedTimesheet()
				.setDate(new Date(Calendar.getInstance().getTime().getTime() - 123333333));
		when(ses.getCurrentEmployee()).thenReturn(mock(Employee.class));
		assertNull(testController.addTimesheet());
		verify(tm).persist(any());
		verify(tm).findLatest(any());
	}

	@Test
	public void testAddRow() {
		Timesheet mockTimesheet = mock(Timesheet.class);
		testController.setSelectedTimesheet(mockTimesheet);
		testController.addRow();
		verify(mockTimesheet).addRow();
	}

	@Test
	public void testGetAssignedWorkPackages() {
		List<WorkPackage> mockWP = Arrays.asList(mock(WorkPackage.class), 
				mock(WorkPackage.class));
		when(wpm.findAssigned(any(), any())).thenReturn(mockWP);
		assertEquals(mockWP, testController.getAssignedWorkPackages(1));
	}

	@Test
	public void testGetTimesheets() {
		List<Timesheet> mockTimesheets = Arrays.asList(mock(Timesheet.class), 
				mock(Timesheet.class));
		when(tm.findByEmployee(any())).thenReturn(mockTimesheets);
		assertEquals(mockTimesheets, testController.getTimesheets());
	}

	@Test
	public void testDeleteRow() {
		Timesheet mockTimesheet = mock(Timesheet.class);
		TimesheetRow mockRow = mock(TimesheetRow.class);
		testController.setSelectedTimesheet(mockTimesheet);
		testController.deleteRow(mockRow);
		verify(mockTimesheet).deleteRow(mockRow);
		
	}

	@Test
	public void testGetSickWorkPackage() {
		WorkPackage mockWP = mock(WorkPackage.class);
		when(wpm.findSickDay()).thenReturn(mockWP);
		assertEquals(mockWP, testController.getSickWorkPackage());
	}

}
