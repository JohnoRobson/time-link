package com.timelink.services.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.timelink.ejbs.Project;
import com.timelink.ejbs.WorkPackage;
import com.timelink.managers.ProjectManager;
import com.timelink.managers.WorkPackageManager;
import com.timelink.services.HRProjectService;
import static org.mockito.Mockito.*;

public class HRProjectServiceTest {
	private HRProjectService testService;
	private ProjectManager pm;
	private WorkPackageManager wpm;
	
	@Before
	public void setUp() throws Exception {
		this.pm = mock(ProjectManager.class);
		this.wpm = mock(WorkPackageManager.class);
		this.testService = new HRProjectService(pm, wpm);
	}

	@Test
	public void testGetHRProject() {
		Project p = mock(Project.class);
		when(pm.findByName(any())).thenReturn(p);
		assertEquals(p, testService.getHRProject() );
	}

	@Test
	public void testIsHRProjectTrue() {
		Project p = mock(Project.class);
		when(p.getProjectNumber()).thenReturn(11);
		when(testService.getHRProject()).thenReturn(p);
		assertTrue(testService.isHRProject(p));
	}
	
	@Test
	public void testIsHRProjectFalse() {
		Project p = mock(Project.class);
		Project p2 = mock(Project.class);
		when(p.getProjectNumber()).thenReturn(11);
		when(p2.getProjectNumber()).thenReturn(10);
		when(testService.getHRProject()).thenReturn(p2);
		assertFalse(testService.isHRProject(p));
	}

	@Test
	public void testGetVacationWorkPackage() {
		WorkPackage wp = mock(WorkPackage.class);
		when(wpm.findByName(any())).thenReturn(wp);
		assertEquals(wp, testService.getVacationWorkPackage());
	}

	@Test
	public void testIsVacationWorkPackage_True() {
		WorkPackage wp = mock(WorkPackage.class);
		when(testService.getVacationWorkPackage()).thenReturn(wp);
		when(wp.getWorkPackageId()).thenReturn(100);
		assertTrue(testService.isVacationWorkPackage(wp));
	}
	
	@Test
	public void testIsVacationWorkPackage_False() {
		WorkPackage wp = mock(WorkPackage.class);
		WorkPackage wp2 = mock(WorkPackage.class);
		when(testService.getVacationWorkPackage()).thenReturn(wp);
		when(wp.getWorkPackageId()).thenReturn(100);
		when(wp2.getWorkPackageId()).thenReturn(111);
		assertFalse(testService.isVacationWorkPackage(wp2));
	}

	@Test
	public void testGetSickDayWorkPackage() {
		WorkPackage wp = mock(WorkPackage.class);
		when(wpm.findByName(any())).thenReturn(wp);
		assertEquals(wp, testService.getSickDayWorkPackage());
	}

	@Test
	public void testIsSickDayWorkPackage_True() {
		WorkPackage wp = mock(WorkPackage.class);
		when(testService.getSickDayWorkPackage()).thenReturn(wp);
		when(wp.getWorkPackageId()).thenReturn(1111);
		assertTrue(testService.isSickDayWorkPackage(wp));
	}
	
	@Test
	public void testIsSickDayWorkPackage_False() {
		WorkPackage wp = mock(WorkPackage.class);
		WorkPackage wp2 = mock(WorkPackage.class);
		when(testService.getSickDayWorkPackage()).thenReturn(wp2);
		when(wp.getWorkPackageId()).thenReturn(1111);
		when(wp2.getWorkPackageId()).thenReturn(2222);
		assertFalse(testService.isSickDayWorkPackage(wp));

	}

	@Test
	public void testGetFlextimeWorkPackage() {
		WorkPackage wp = mock(WorkPackage.class);
		when(wpm.findByName(any())).thenReturn(wp);
		assertEquals(wp, testService.getFlextimeWorkPackage());
	}

	@Test
	public void testIsFlextimeWorkPackage_True() {
		WorkPackage wp = mock(WorkPackage.class);
		when(testService.getFlextimeWorkPackage()).thenReturn(wp);
		when(wp.getWorkPackageId()).thenReturn(1111);
		assertTrue(testService.isFlextimeWorkPackage(wp));
	}
	
	@Test
	public void testIsFlextimeWorkPackage_False() {
		WorkPackage wp = mock(WorkPackage.class);
		WorkPackage wp2 = mock(WorkPackage.class);
		when(testService.getFlextimeWorkPackage()).thenReturn(wp);
		when(wp.getWorkPackageId()).thenReturn(1111);
		when(wp2.getWorkPackageId()).thenReturn(2222);
		assertFalse(testService.isFlextimeWorkPackage(wp2));
	}

	@Test
	public void testGetLongTermDisabilityWorkPackage() {
		WorkPackage wp = mock(WorkPackage.class);
		when(wpm.findByName(any())).thenReturn(wp);
		assertEquals(wp, testService.getLongTermDisabilityWorkPackage());
	}

	@Test
	public void testIsLongTermDisabilityWorkPackage_True() {
		WorkPackage wp = mock(WorkPackage.class);
		when(testService.getLongTermDisabilityWorkPackage()).thenReturn(wp);
		when(wp.getWorkPackageId()).thenReturn(1111);
		assertTrue(testService.isLongTermDisabilityWorkPackage(wp));
	}
	
	@Test
	public void testIsLongTermDisabilityWorkPackage_False() {
		WorkPackage wp = mock(WorkPackage.class);
		WorkPackage wp2 = mock(WorkPackage.class);
		when(testService.getLongTermDisabilityWorkPackage()).thenReturn(wp);
		when(wp.getWorkPackageId()).thenReturn(1111);
		when(wp2.getWorkPackageId()).thenReturn(2222);
		assertFalse(testService.isLongTermDisabilityWorkPackage(wp2));
	}

	@Test
	public void testGetShortTermDisabilityWorkPackage() {
		WorkPackage wp = mock(WorkPackage.class);
		when(wpm.findByName(any())).thenReturn(wp);
		assertEquals(wp, testService.getShortTermDisabilityWorkPackage());
	}

	@Test
	public void testIsShortTermDisabilityWorkPackage_True() {
		WorkPackage wp = mock(WorkPackage.class);
		when(testService.getShortTermDisabilityWorkPackage()).thenReturn(wp);
		when(wp.getWorkPackageId()).thenReturn(1111);
		assertTrue(testService.isShortTermDisabilityWorkPackage(wp));
	}

	@Test
	public void testGetStatHolidayWorkPackage_False() {
		WorkPackage wp = mock(WorkPackage.class);
		WorkPackage wp2 = mock(WorkPackage.class);
		when(testService.getShortTermDisabilityWorkPackage()).thenReturn(wp);
		when(wp.getWorkPackageId()).thenReturn(1111);
		when(wp2.getWorkPackageId()).thenReturn(2222);
		assertFalse(testService.isShortTermDisabilityWorkPackage(wp2));
	}

	@Test
	public void testIsStatHolidayWorkPackage_True() {
		WorkPackage wp = mock(WorkPackage.class);
		when(testService.getStatHolidayWorkPackage()).thenReturn(wp);
		when(wp.getWorkPackageId()).thenReturn(1111);
		assertTrue(testService.isStatHolidayWorkPackage(wp));
	}
	
	@Test
	public void testIsStatHolidayWorkPackage_False() {
		WorkPackage wp = mock(WorkPackage.class);
		WorkPackage wp2 = mock(WorkPackage.class);
		when(testService.getStatHolidayWorkPackage()).thenReturn(wp);
		when(wp.getWorkPackageId()).thenReturn(1111);
		when(wp2.getWorkPackageId()).thenReturn(2222);
		assertFalse(testService.isStatHolidayWorkPackage(wp2));
	}

}
