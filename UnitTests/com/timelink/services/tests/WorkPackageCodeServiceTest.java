package com.timelink.services.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.timelink.ejbs.Project;
import com.timelink.ejbs.WorkPackage;
import com.timelink.services.WorkPackageCodeService;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

public class WorkPackageCodeServiceTest {
	
	private WorkPackageCodeService wpCodeService;

	@Before
	public void setUp() throws Exception {
		wpCodeService = new WorkPackageCodeService();

	}

	@Test
	public void generateNewCode_projectNoWorkPackagesAndNullCodeAndUniqueNewNumber_1000000() {
		Project project = new Project();
		project.setWorkPackages(new ArrayList<WorkPackage>());

        assertEquals("1000000", wpCodeService.generateNewCode(project, null, "1"));
	}

	@Test
	public void helloworld() {
		Project project = new Project();
		List<WorkPackage> wps = new ArrayList<WorkPackage>();
		WorkPackage wp1 = new WorkPackage();
		wp1.setCode("1110000");
		wps.add(wp1);
		project.setWorkPackages(wps);

        System.out.println(wpCodeService.generateNewCode(project, null, "1"));
        System.out.println(wpCodeService.generateNewCode(project, "2", "1"));
        System.out.println("hello");
        assertTrue(true);
	}
}
