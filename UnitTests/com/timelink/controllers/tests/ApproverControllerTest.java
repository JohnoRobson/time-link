package com.timelink.controllers.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import static org.mockito.Matchers.any;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import com.timelink.Session;
import com.timelink.enums.TimesheetStatus;
import com.timelink.controllers.ApproverController;
import com.timelink.ejbs.Timesheet;
import com.timelink.ejbs.WorkPackage;
import com.timelink.managers.ProjectManager;
import com.timelink.managers.TimesheetManager;
import com.timelink.managers.WorkPackageManager;
import com.timelink.services.FlextimeService;
import com.timelink.services.VacationService;

public class ApproverControllerTest {
	
	private ApproverController testController;
	 private TimesheetManager tm;
	  private WorkPackageManager wpm;
	  private Session ses;
	  private FlextimeService flextimeService;
	  private VacationService vacationService;
	  private ProjectManager pm;
	
	@Before
    public void setUp() {
        this.tm = mock(TimesheetManager.class);
        this.ses = mock(Session.class);
        this.wpm = mock(WorkPackageManager.class);
		this.flextimeService = mock(FlextimeService.class);
		this.vacationService = mock(VacationService.class);
		this.pm = mock(ProjectManager.class);
		this.testController = new ApproverController(tm, wpm, ses, flextimeService, vacationService, pm);
    }
	
	@Test
	public void testRefreshList() {
		//testController.refreshList();
		//verify(tm).findByApprover(any());
	}

	@Test
	public void testSave() {
		List<Timesheet> testTimesheets = 
				Arrays.asList(mock(Timesheet.class), mock(Timesheet.class));
		testController.setTimesheets(testTimesheets);
		assertEquals(null, testController.save());
		verify(tm, Mockito.atLeast(2)).merge(any());
	}

	@Test
	public void testViewTimesheet() {
		assertEquals(null, testController.viewTimesheet(mock(Timesheet.class)));
		assertNotEquals("LoganIsDrunk", testController.viewTimesheet(mock(Timesheet.class)));
	}

	@Test
	public void testApprove() {
		Timesheet mockTS1 = mock(Timesheet.class);
		Timesheet mockTS2 = mock(Timesheet.class);
		List<Timesheet> testTimesheets = 
				Arrays.asList(mockTS1, mockTS2);
		WorkPackage wp1 = mock(WorkPackage.class);
		WorkPackage wp2 = mock(WorkPackage.class);
		List<WorkPackage> mockWps = Arrays.asList(wp1, wp2);
		when(wpm.getAllInTimesheet(mockTS1)).thenReturn(mockWps);
		testController.setSelectedTimesheets(testTimesheets);
		assertEquals(null, testController.approve());
		verify(wpm, Mockito.atLeast(2)).merge(any());;
		verify(tm, Mockito.atLeast(2)).merge(any());
	}


	@Test
	public void testTimesheetIsApproved() {
		Timesheet ts = new Timesheet();
		ts.setStatus(""+ TimesheetStatus.APPROVED.ordinal());
		testController.setSelectedTimesheets(Arrays.asList(ts));
		assertTrue(testController.timesheetIsApproved());
		Timesheet ts2 = new Timesheet();
		ts2.setStatus(""+ TimesheetStatus.REJECTED.ordinal());
		testController.setSelectedTimesheets(Arrays.asList(ts2));
		assertFalse(testController.timesheetIsApproved());
	}

	@Test
	public void testDeclineSave() {
		List<Timesheet> mockTimesheets = 
				Arrays.asList(mock(Timesheet.class), mock(Timesheet.class));
		testController.setSelectedTimesheets(mockTimesheets);
		assertEquals(null, testController.declineSave());
		verify(flextimeService, Mockito.atLeast(2)).revertFlextime(any());
		verify(tm, Mockito.atLeast(2)).merge(any());
	}


}
