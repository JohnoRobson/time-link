package com.timelink.services.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.timelink.enums.TimesheetStatus;
import com.timelink.managers.EmployeeManager;
import com.timelink.managers.WorkPackageManager;
import com.timelink.ejbs.Employee;
import com.timelink.ejbs.Timesheet;
import com.timelink.services.FlextimeService;
import com.timelink.services.HRProjectService;

import static org.mockito.Mockito.*;

import javax.inject.Inject;

public class FlextimeServiceTest {

	private FlextimeService testService;
	private HRProjectService hrps;
	private WorkPackageManager wpm;
	private EmployeeManager em;
	  
	
	@Before
	public void setUp() throws Exception {
		this.hrps = mock(HRProjectService.class);
		this.wpm = mock(WorkPackageManager.class);
		this.em = mock(EmployeeManager.class);
		testService = new FlextimeService(hrps, wpm, em);
	}

	@Test
	public void testClaimFlextime() {
		Timesheet mockTimesheet = mock(Timesheet.class);
		Employee mockEmp = mock(Employee.class);
		when(mockTimesheet.getStatus()).thenReturn(TimesheetStatus.WAITINGFORAPPROVAL.name());
		when(mockTimesheet.getEmployee()).thenReturn(mockEmp);
		when(mockEmp.getVacationTime()).thenReturn((float) 2.0);
		when(mockTimesheet.getFlextime()).thenReturn((float) 3.0);
		testService.claimFlextime(mockTimesheet);
		verify(mockTimesheet, atLeast(3)).getEmployee();
		
		Timesheet mockTimesheet2 = mock(Timesheet.class);
		Employee mockEmp2 = mock(Employee.class);
		when(mockTimesheet2.getStatus()).thenReturn(TimesheetStatus.APPROVED.name());
		when(mockTimesheet2.getEmployee()).thenReturn(mockEmp2);
		when(mockEmp2.getVacationTime()).thenReturn((float) 2.0);
		when(mockTimesheet2.getFlextime()).thenReturn((float) 3.0);
		testService.claimFlextime(mockTimesheet2);
		verify(mockTimesheet2, times(3)).getEmployee();
	}

	@Test
	public void testRevertFlextime() {
		Timesheet mockTimesheet = mock(Timesheet.class);
		Employee mockEmp = mock(Employee.class);
		when(mockTimesheet.getStatus()).thenReturn(TimesheetStatus.WAITINGFORAPPROVAL.name());
		when(mockTimesheet.getEmployee()).thenReturn(mockEmp);
		when(mockEmp.getFlexTime()).thenReturn((float) 2);
		when(mockTimesheet.getFlextime()).thenReturn((float) 3.0);
		testService.revertFlextime(mockTimesheet);
		verify(mockTimesheet, times(3)).getEmployee();
		
		Timesheet mockTimesheet2 = mock(Timesheet.class);
		when(mockTimesheet2.getStatus()).thenReturn(TimesheetStatus.APPROVED.name());
		testService.revertFlextime(mockTimesheet2);
		verify(mockTimesheet2, never()).getEmployee();
	}

}
