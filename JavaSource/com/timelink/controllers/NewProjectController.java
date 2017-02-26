package com.timelink.controllers;

import com.timelink.ejbs.Project;
import com.timelink.managers.ProjectManager;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("serial")
@SessionScoped
@Named("NewProjectController")
public class NewProjectController implements Serializable {
  @Inject ProjectManager pm;
  private String projectName;
  private String projectDescription;
  private String customer;
  private String projectManager;
  
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
  public String getProjectManager() {
    return projectManager;
  }
  
  /**
   * Sets the projectManager to projectManager.
   * @param projectManager the projectManager to set
   */
  public void setProjectManager(String projectManager) {
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
    pm.persist(project);
    reset();
    return "assignemp";
  }
}
