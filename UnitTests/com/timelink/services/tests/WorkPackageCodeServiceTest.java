package com.timelink.services.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.timelink.ejbs.Project;
import com.timelink.services.WorkPackageCodeService;
import static org.mockito.Mockito.*;

public class WorkPackageCodeServiceTest {
	
	private WorkPackageCodeService testService;

	@Before
	public void setUp() throws Exception {
		testService = new WorkPackageCodeService();
	}

	@Test
	public void testGenerateNewCode() {
		Project testProject = new Project();
	}

}
