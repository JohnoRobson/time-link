package com.timelink.controllers;

import com.timelink.Session;
import com.timelink.ejbs.Employee;
import com.timelink.ejbs.Project;
import com.timelink.ejbs.WorkPackage;
import com.timelink.managers.EmployeeManager;
import com.timelink.managers.LabourGradeManager;
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
@Named("ProjectPlanningController")
public class ProjectPlanningController implements Serializable {
  
  @Inject ProjectManager pm;
  @Inject Session ses;
  @Inject LabourGradeManager lgm;
  @Inject EmployeeManager em;
  @Inject WorkPackageManager wpm;
  private Integer responsibleEngineerId;
  private String wpCode;
  private String wpDescription;
  private Project currentProject;
  
  public List<Project> getProjects() {
    return pm.findByProjectManager(ses.getCurrentEmployee().getEmployeeId());
  }

  /**
   * @return the currentProject
   */
  public final Integer getCurrentProjectId() {
    if (currentProject == null) {
      return null;
    }
    return currentProject.getProjectNumber();
  }

  /**
   * @param currentProject the currentProject to set
   */
  public final void setCurrentProjectId(Integer currentProject) {
    this.currentProject = pm.find(currentProject);
  }
  
  public Project getCurrentProject() {
    return currentProject;
  }
  
  public String save() {
    pm.merge(currentProject);
    return null;
  }
  
  
  //=============
  
  /**
   * @return the wpCode
   */
  public String getWpCode() {
    return wpCode;
  }
  /**
   * @param wpCode the wpCode to set
   */
  public void setWpCode(String wpCode) {
    this.wpCode = wpCode;
  }
  /**
   * @return the wpDescription
   */
  public String getWpDescription() {
    return wpDescription;
  }
  /**
   * @param wpDescription the wpDescription to set
   */
  public void setWpDescription(String wpDescription) {
    this.wpDescription = wpDescription;
  }
  
  /**
   * @return the responsibleEngineer
   */
  public Integer getResponsibleEngineer() {
    return responsibleEngineerId;
  }
  /**
   * @param responsibleEngineer the responsibleEngineer to set
   */
  public void setResponsibleEngineer(Integer responsibleEngineerId) {
    this.responsibleEngineerId = responsibleEngineerId;
  }
  public String createWorkPackage() {
    WorkPackage wp = new WorkPackage();
    wp.setCode(getWpCode());
    wp.setDescription(getWpDescription());
    wp.setProject(currentProject);
    //wp.setResponsibleEngineer(em.find(getResponsibleEngineer()));
    wpm.persist(wp);
    setCurrentProjectId(currentProject.getProjectNumber());
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
