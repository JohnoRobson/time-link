package com.timelink.controllers;

import com.timelink.ejbs.Employee;
import com.timelink.ejbs.Project;
import com.timelink.ejbs.WorkPackage;
import com.timelink.managers.EmployeeManager;
import com.timelink.managers.ProjectManager;
import com.timelink.managers.WorkPackageManager;
import com.timelink.roles.RoleEnum;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("serial")
@SessionScoped
@Named("NewWorkPackageController")
public class NewWorkPackageController implements Serializable {
  
  @Inject EmployeeManager em;
  @Inject WorkPackageManager wpm;
  @Inject ProjectManager pm;
  private Integer responsibleEngineerId;
  private Integer projectId;
  private String wpCode;
  private String wpDescription;
  
  /**
   * @return the wpCode
   */
  public final String getWpCode() {
    return wpCode;
  }
  /**
   * @param wpCode the wpCode to set
   */
  public final void setWpCode(String wpCode) {
    this.wpCode = wpCode;
  }
  /**
   * @return the wpDescription
   */
  public final String getWpDescription() {
    return wpDescription;
  }
  /**
   * @param wpDescription the wpDescription to set
   */
  public final void setWpDescription(String wpDescription) {
    this.wpDescription = wpDescription;
  }
  
  /**
   * @return the responsibleEngineer
   */
  public final Integer getResponsibleEngineer() {
    return responsibleEngineerId;
  }
  /**
   * @param responsibleEngineer the responsibleEngineer to set
   */
  public final void setResponsibleEngineer(Integer responsibleEngineerId) {
    this.responsibleEngineerId = responsibleEngineerId;
  }
  /**
   * @return the project
   */
  public final Integer getProjectId() {
    return projectId;
  }
  /**
   * @param project the project to set
   */
  public final void setProjectId(Integer projectId) {
    this.projectId = projectId;
  }
  public String createWorkPackage() {
    WorkPackage wp = new WorkPackage();
    wp.setCode(getWpCode());
    wp.setDescription(getWpDescription());
    wp.setProject(pm.find(getProjectId()));
    //wp.setResponsibleEngineer(em.find(getResponsibleEngineer()));
    wpm.persist(wp);
    return null;
  }
  
  public List<Employee> getResponsibleEngineers() {
    return em.getAllEmployeesWithRole(RoleEnum.RESPONSIBLE_ENGINEER);
  }
  
  public List<Project> getAllProjects() {
    return pm.findAll();
  }
  
  public boolean validateWorkPackage() {
    return true;
  }
}
