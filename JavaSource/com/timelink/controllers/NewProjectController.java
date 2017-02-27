package com.timelink.controllers;

import com.timelink.ejbs.Employee;
import com.timelink.ejbs.Project;
import com.timelink.ejbs.Role;
import com.timelink.managers.EmployeeManager;
import com.timelink.managers.ProjectManager;
import com.timelink.managers.RoleManager;
import com.timelink.roles.RoleEnum;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("serial")
@SessionScoped
@Named("NewProjectController")
public class NewProjectController implements Serializable {
  @Inject ProjectManager pm;
  @Inject EmployeeManager em;
  @Inject RoleManager rm;
  private String projectName;
  private String projectDescription;
  private String customer;
  private Integer projectManager;
  
  /**
   * Returns the projectName.
   * @return the projectName
   */
  public String getProjectName() {
    return projectName;
  }
  
  /**
   * Sets the projectName to projectName.
   * @param projectName the projectName to set
   */
  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }
  
  /**
   * Returns the projectDescription.
   * @return the projectDescription
   */
  public String getProjectDescription() {
    return projectDescription;
  }
  
  /**
   * Sets the projectDescription to projectDescription.
   * @param projectDescription the projectDescription to set
   */
  public void setProjectDescription(String projectDescription) {
    this.projectDescription = projectDescription;
  }
  
  /**
   * Returns the customer.
   * @return the customer
   */
  public String getCustomer() {
    return customer;
  }
  
  /**
   * Sets the customer to customer.
   * @param customer the customer to set
   */
  public void setCustomer(String customer) {
    this.customer = customer;
  }
  
  /**
   * Returns the projectManager.
   * @return the projectManager
   */
  public Integer getProjectManager() {
    return projectManager;
  }
  
  /**
   * Sets the projectManager to projectManager.
   * @param projectManager the projectManager to set
   */
  public void setProjectManager(Integer projectManager) {
    this.projectManager = projectManager;
  }
  
  private void reset() {
    projectName = null;
    projectDescription = null;
    customer = null;
    projectManager = null;
  }
  
  /**
   * Create a new project and navigate to result
   * page afterward.
   * @return String for navigation
   */
  public String createProject() {
    Project project = new Project();
    project.setProjectName(projectName);
    project.setDescription(projectDescription);
    project.setCustomer(customer);
    makeEmployeePm();
    project.setProjectManager(em.find(projectManager));
    pm.persist(project);
    reset();
    return "assignemp";
  }
  
  /**
   * Returns a list of all employees.
   * @return Return a list of all employees.
   */
  public List<Employee> getEmployees() {
    return em.getAll();
  }
  
  /**
   * Gives an employee the Project Manager Role if it doesn't
   * already have it.
   */
  private void makeEmployeePm() {
    Employee emp = em.find(projectManager);
    if (!emp.hasRole(RoleEnum.PROJECT_MANAGER)) {
      Role role = new Role();
      role.setEmployee(emp);
      role.setRole(RoleEnum.PROJECT_MANAGER);
      rm.persist(role);
    }
  }
}
