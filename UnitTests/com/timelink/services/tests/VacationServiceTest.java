package com.timelink.services.tests;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import com.timelink.ejbs.Employee;
import com.timelink.ejbs.Timesheet;
import com.timelink.enums.TimesheetStatus;
import com.timelink.managers.EmployeeManager;
import com.timelink.managers.WorkPackageManager;
import com.timelink.services.HRProjectService;
import com.timelink.services.VacationService;
import static org.mockito.Mockito.*;

public class VacationServiceTest {
	private VacationService testService;
	private HRProjectService hrps;
	private WorkPackageManager wpm;
	private EmployeeManager em;
	
	
	@Before
	public void setUp() throws Exception {
	 this.hrps = mock(HRProjectService.class);
	 this.wpm = mock(WorkPackageManager.class);
	 this.em = mock(EmployeeManager.class);
	 testService  = new VacationService(hrps, wpm, em);
	}

	@Test
	public void testClaimVacation_InCondition_1() {
		Timesheet mockTimesheet = mock(Timesheet.class);
		Employee mockEmp = mock(Employee.class);
		when(mockTimesheet.getStatus()).thenReturn(TimesheetStatus.WAITINGFORAPPROVAL.name());
		when(mockTimesheet.getEmployee()).thenReturn(mockEmp);
		when(mockEmp.getVacationTime()).thenReturn((float) 2.0);
		testService.claimVacation(mockTimesheet);
		verify(mockTimesheet, times(3)).getEmployee();
		verify(em).merge(any());
	}
	
	@Test
	public void testClaimVacation_InCondition_2() {
		Timesheet mockTimesheet = mock(Timesheet.class);
		Employee mockEmp = mock(Employee.class);
		when(mockTimesheet.getStatus()).thenReturn(TimesheetStatus.REJECTED.name());
		when(mockTimesheet.getEmployee()).thenReturn(mockEmp);
		when(mockEmp.getVacationTime()).thenReturn((float) 2.0);
		testService.claimVacation(mockTimesheet);
		verify(mockTimesheet, times(3)).getEmployee();
		verify(em).merge(any());
	}
	
	@Test
	public void testClaimVacation_OutSideCondition() {
		Timesheet mockTimesheet = mock(Timesheet.class);
		Employee mockEmp = mock(Employee.class);
		when(mockTimesheet.getStatus()).thenReturn(TimesheetStatus.APPROVED.name());
		
		verify(mockTimesheet, never()).getEmployee();
		verify(em, never()).merge(any());
	}

	@Test
	public void testRevertVacation_InCondition_1() {
		Timesheet mockTimesheet = mock(Timesheet.class);
		Employee mockEmp = mock(Employee.class);
		when(mockTimesheet.getStatus()).thenReturn(TimesheetStatus.REJECTED.name());
		when(mockTimesheet.getEmployee()).thenReturn(mockEmp);
		when(mockEmp.getVacationTime()).thenReturn((float) 2.0);
		testService.revertVacation(mockTimesheet);
		verify(mockTimesheet, times(3)).getEmployee();
		verify(em).merge(any());
	}
	
	@Test
	public void testRevertVacation_InCondition_2() {
		Timesheet mockTimesheet = mock(Timesheet.class);
		Employee mockEmp = mock(Employee.class);
		when(mockTimesheet.getStatus()).thenReturn(TimesheetStatus.WAITINGFORAPPROVAL.name());
		when(mockTimesheet.getEmployee()).thenReturn(mockEmp);
		when(mockEmp.getVacationTime()).thenReturn((float) 2.0);
		testService.revertVacation(mockTimesheet);
		verify(mockTimesheet, times(3)).getEmployee();
		verify(em).merge(any());
	}
	
	@Test
	public void testRevertVacation_OutsideCondition() {
		Timesheet mockTimesheet = mock(Timesheet.class);
		Employee mockEmp = mock(Employee.class);
		when(mockTimesheet.getStatus()).thenReturn(TimesheetStatus.APPROVED.name());
		testService.revertVacation(mockTimesheet);
		verify(mockTimesheet, never()).getEmployee();
		verify(em, never()).merge(any());
	}

}
