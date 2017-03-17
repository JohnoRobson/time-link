package com.timelink.services.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.timelink.TimesheetStatus;
import com.timelink.ejbs.Employee;
import com.timelink.ejbs.Timesheet;
import com.timelink.services.FlextimeService;
import static org.mockito.Mockito.*;

public class FlextimeServiceTest {

	private FlextimeService testService;
	
	@Before
	public void setUp() throws Exception {
		testService = new FlextimeService();
	}

	@Test
	public void testApplyFlextime() {
		Timesheet mockTimesheet = mock(Timesheet.class);
		Employee mockEmp = mock(Employee.class);
		when(mockTimesheet.getStatus()).thenReturn(TimesheetStatus.WAITINGFORAPPROVAL.name());
		when(mockTimesheet.getEmployee()).thenReturn(mockEmp);
		when(mockEmp.getFlexTime()).thenReturn(2);
		when(mockTimesheet.getFlextime()).thenReturn((float) 3.0);
		testService.applyFlextime(mockTimesheet);
		verify(mockTimesheet, times(2)).getEmployee();
		
		Timesheet mockTimesheet2 = mock(Timesheet.class);
		when(mockTimesheet2.getStatus()).thenReturn(TimesheetStatus.APPROVED.name());
		testService.applyFlextime(mockTimesheet2);
		verify(mockTimesheet2, never()).getEmployee();
	}

	@Test
	public void testRevertFlextime() {
		Timesheet mockTimesheet = mock(Timesheet.class);
		Employee mockEmp = mock(Employee.class);
		when(mockTimesheet.getStatus()).thenReturn(TimesheetStatus.WAITINGFORAPPROVAL.name());
		when(mockTimesheet.getEmployee()).thenReturn(mockEmp);
		when(mockEmp.getFlexTime()).thenReturn(2);
		when(mockTimesheet.getFlextime()).thenReturn((float) 3.0);
		testService.revertFlextime(mockTimesheet);
		verify(mockTimesheet, times(2)).getEmployee();
		
		Timesheet mockTimesheet2 = mock(Timesheet.class);
		when(mockTimesheet2.getStatus()).thenReturn(TimesheetStatus.REJECTED.name());
		testService.revertFlextime(mockTimesheet2);
		verify(mockTimesheet2, never()).getEmployee();
	}

}
