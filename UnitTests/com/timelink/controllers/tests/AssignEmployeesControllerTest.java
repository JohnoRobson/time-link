package com.timelink.controllers.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.timelink.controllers.AssignEmployeesController;
import com.timelink.ejbs.Project;
import com.timelink.managers.ProjectManager;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

public class AssignEmployeesControllerTest {
	private AssignEmployeesController testController;
	private ProjectManager pm;
	@Before
	public void setUp() throws Exception {
		pm = mock(ProjectManager.class);
		testController = new AssignEmployeesController(pm);
	}

	@Test
	public void testGetProjects() {
		List<Project> mockProjects = Arrays.asList(mock(Project.class), mock(Project.class));
		when(pm.findAll()).thenReturn(mockProjects);
		assertEquals(mockProjects, testController.getProjects());
		verify(pm).findAll();
	}

}
