package com.timelink.ejbs.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.timelink.ejbs.Project;
import com.timelink.ejbs.WorkPackage;

public class ProjectTest {
    
    Project project;
    
    @Before
    public void setUp() throws Exception {
        project = new Project();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetWorkPackages() {
        project.setWorkPackages(new ArrayList<WorkPackage>());
        assertNotNull(project.getWorkPackages());
        assertEquals(0, project.getWorkPackages().size());

        ArrayList<WorkPackage> wps = new ArrayList<>();
        wps.add(new WorkPackage());
        project.setWorkPackages(wps);
        assertEquals(1,project.getWorkPackages().size());
    }

}
