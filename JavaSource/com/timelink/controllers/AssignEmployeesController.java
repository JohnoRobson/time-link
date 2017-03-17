package com.timelink.controllers;

import com.timelink.ejbs.Employee;
import com.timelink.ejbs.Project;
import com.timelink.managers.EmployeeManager;
import com.timelink.managers.ProjectManager;

import org.primefaces.model.DualListModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("serial")
@SessionScoped
@Named("AssignEmployeesController")
public class AssignEmployeesController implements Serializable {
  @Inject ProjectManager pm;
  @Inject EmployeeManager em;
  
  private Project selectedProject;
  
  public AssignEmployeesController() {}
  
  public AssignEmployeesController (ProjectManager pm) {
	  this.pm = pm;
  }
  
  /**
   * Return the selectedProject.
   * @return selectedProject
   */
  public Project getSelectedProject() {
  }

  /**
   * Sets the selectedProject to selectedProject.
   * @param selectedProject the selectedProject to set
   */
  public void setSelectedProject(Project selectedProject) {
    this.selectedProject = selectedProject;
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
    
    if (selectedProject != null) {
      employeeSource = em.findByNotInProject(selectedProject);
      employeeTarget = selectedProject.getEmployees();
    }
    
    return new DualListModel<Employee>(employeeSource, employeeTarget);
  }
  
  /**
   * Merges all Employees in the target array with
   * the selected project.
   * @param employees that are merged with selectedProject
   */
  public void setEmployees(DualListModel<Employee> employees) {
    if (selectedProject != null) {
      List<Employee> emps = new ArrayList<Employee>();
      for (Employee e : employees.getTarget()) {
        e.getProjects().add(selectedProject);
        emps.add(e);
      }
      selectedProject.setEmployees(emps);
      pm.merge(selectedProject);
    }
  }
}
