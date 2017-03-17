package com.timelink.controllers.tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.faces.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import javax.faces.context.FacesContext;
import com.timelink.Session;
import com.timelink.TimesheetStatus;
import com.timelink.controllers.ApproverController;
import com.timelink.ejbs.Timesheet;
import com.timelink.managers.TimesheetManager;
import com.timelink.services.FlextimeService;

public class ApproverControllerTest {
	
	private ApproverController testController;
	private TimesheetManager tm;
	private Session ses;
	private FlextimeService flextimeService;
	
	@Before
    public void setUp() {
        this.tm = mock(TimesheetManager.class);
        this.ses = mock(Session.class);
		this.flextimeService = mock(FlextimeService.class);
		this.testController = new ApproverController(tm, ses, flextimeService);
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
		List<Timesheet> testTimesheets = 
				Arrays.asList(mock(Timesheet.class), mock(Timesheet.class));
		testController.setSelectedTimesheets(testTimesheets);
		assertEquals(null, testController.approve());
		verify(tm, Mockito.atLeast(2)).merge(any());
	}

	@Test
	public void testDeclineValidate() {
//		List<Timesheet> testTimesheets = 
//				Arrays.asList(mock(Timesheet.class), mock(Timesheet.class));
//		testController.setSelectedTimesheets(testTimesheets);
//		testController.declineValidate(mock(ActionEvent.class));
//		FacesContext context = mock(FacesContext.class);
		
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
