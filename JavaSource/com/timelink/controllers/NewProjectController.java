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
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
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
  private Integer projectManagerAssistant;
  
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
  
  /**
   * Returns the projectManager.
   * @return the projectManager
   */
  public Integer getProjectManagerAssistant() {
    return projectManager;
  }
  
  /**
   * Sets the projectManager to projectManager.
   * @param projectManagerAssistant the projectManager to set
   */
  public void setProjectManagerAssistant(Integer projectManagerAssistant) {
    this.projectManagerAssistant = projectManagerAssistant;
  }
  
  private void reset() {
    projectName = null;
    projectDescription = null;
    customer = null;
    projectManager = null;
    projectManagerAssistant = null;
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
    //Only Project Manager assistant is optional.
    if (projectManagerAssistant != null) {
      makeEmployeePma();
      project.setProjectManagerAssistant(em.find(projectManagerAssistant));
    }
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
  
  /**
   * Gives an employee the Project Manager Assistant Role if it doesn't
   * already have it.
   */
  private void makeEmployeePma() {
    Employee emp = em.find(projectManagerAssistant);
    if (!emp.hasRole(RoleEnum.PROJECT_MANAGER_ASSISTANT)) {
      Role role = new Role();
      role.setEmployee(emp);
      role.setRole(RoleEnum.PROJECT_MANAGER_ASSISTANT);
      rm.persist(role);
    }
  }
  
  /**
   * Returns true if the project name is unique.
   * @param context etc
   * @param component etc
   * @param value the name being checked
   * @throws ValidatorException etc
   */
  public void validateProjectName(FacesContext context, UIComponent component, Object value)
      throws ValidatorException {
    if (!(value instanceof String)) {
      throw new IllegalArgumentException("value not a String");
    }
    
    if (!pm.projectNameIsUnique((String) value)) {
      throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
          "Project name must be unique", "Project name must be unique"));
    }
  }
}

