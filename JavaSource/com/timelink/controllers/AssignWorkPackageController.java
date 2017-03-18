package com.timelink.controllers;

import com.timelink.ejbs.Employee;
import com.timelink.ejbs.Project;
import com.timelink.ejbs.WorkPackage;
import com.timelink.managers.EmployeeManager;
import com.timelink.managers.ProjectManager;
import com.timelink.managers.WorkPackageManager;

import org.primefaces.model.DualListModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("serial")
@SessionScoped
@Named("AssignWPController")
public class AssignWorkPackageController implements Serializable {
  @Inject ProjectManager pm;
  @Inject WorkPackageManager wpm;
  @Inject EmployeeManager em;
  
  private Project selectedProject;
  private WorkPackage selectedWorkPackage;
  
  private int projectId;

  /**
   * Return the selectedProject.
   * @return selectedProject
   */
  public Project getSelectedProject() {
    return selectedProject;
  }

  /**
   * Sets the selectedProject to selectedProject.
   * @param selectedProject the selectedProject to set
   */
  public void setSelectedProject(Project selectedProject) {
    this.selectedProject = selectedProject;
  }
  
  /**
   * Return the selectedWorkPackage.
   * @return selectedWorkPackage
   */
  public WorkPackage getSelectedWorkPackage() {
    return selectedWorkPackage;
  }

  /**
   * Sets the selectedWorkPackage to selectedWorkPackage.
   * @param selectedWorkPackage the selectedWorkPackage to set
   */
  public void setSelectedWorkPackage(WorkPackage selectedWorkPackage) {
    this.selectedWorkPackage = selectedWorkPackage;
  }

  /**
   * Returns the projectId.
   * @return the projectId
   */
  public int getProjectId() {
    return projectId;
  }

  /**
   * Sets the projectId to projectId.
   * @param projectId the projectId to set
   */
  public void setProjectId(int projectId) {
    this.projectId = projectId;
    selectedProject = pm.find(projectId);
    //selectedWorkPackage = null;
  }

  /**
   * Returns all projects.
   * @return list of all projects
   */
  public List<Project> getProjects() {
    return pm.findAll();
  }
  
  /**
   * Return Employees if selectedProject does not equal
   * null.
   * @return DualIstModel of Employees for that selectedProject
   */
  public DualListModel<Employee> getEmployees() {
    List<Employee> employeeSource = new ArrayList<Employee>();
    List<Employee> employeeTarget = new ArrayList<Employee>();
    
    if (selectedProject != null && selectedWorkPackage != null) {
      employeeSource = em.findByNotInWorkPackage(selectedProject, selectedWorkPackage);
      employeeTarget.addAll(selectedWorkPackage.getAssignedEmployees());
    }
    
    return new DualListModel<Employee>(employeeSource, employeeTarget);
  }
  
  /**
   * Merges all Employees in the target array with
   * the selected project.
   * @param employees that are merged with selectedProject
   */
  public void setEmployees(DualListModel<Employee> employees) {
    /*
    if (selectedProject != null) {
      List<Employee> emps = new ArrayList<Employee>();
      for (Employee e : employees.getTarget()) {
        e.getProjects().add(selectedProject);
        emps.add(e);
      }
      selectedProject.setEmployees(emps);
      pm.merge(selectedProject);
    } */
    if (selectedProject != null && selectedWorkPackage != null) {
      List<Employee> emps = new ArrayList<Employee>();
      for (Employee e : employees.getTarget()) {
        emps.add(e);
      }
      selectedWorkPackage.setAssignedEmployees(new HashSet<Employee>(emps));
      wpm.merge(selectedWorkPackage);
    }
  }
  
  public void resetWP() {
    selectedWorkPackage = null;
  }
  
  // TODO Must update JavaDoc comments
  /**
   * Reload page and display project.
   * @return navigation string
   */
  public String assign() {
    /*
    selectedProject = pm.find(projectId);
    selectedWorkPackage = null;
    System.out.println("ASSIGNHAPPENSNOW"); */
    return "assigntowp"; 
  }
}
