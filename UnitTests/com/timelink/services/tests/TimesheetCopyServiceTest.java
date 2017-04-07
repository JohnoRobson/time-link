package com.timelink.services.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.timelink.ejbs.Employee;
import com.timelink.ejbs.Timesheet;
import com.timelink.services.TimesheetCopyService;

import static org.mockito.Mockito.*;

public class TimesheetCopyServiceTest {
	TimesheetCopyService testService;
	
	@Before
	public void setUp() throws Exception {
		testService = new TimesheetCopyService();
	}

	@Test
	public void testCopyTimesheet() {
		Timesheet ts = mock(Timesheet.class);
		Timesheet newTS = new Timesheet();
		when(ts.getEmployee()).thenReturn(new Employee());
		when(testService.copyTimesheet(ts, any(), any())).thenReturn(newTS);
		assertEquals(newTS, testService.copyTimesheet(ts, 12, 2017));
		
	}

}
