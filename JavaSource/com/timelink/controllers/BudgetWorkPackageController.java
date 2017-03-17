package com.timelink.controllers;

import com.timelink.Session;
import com.timelink.ejbs.Employee;
import com.timelink.ejbs.LabourGrade;
import com.timelink.ejbs.Project;
import com.timelink.ejbs.WorkPackage;
import com.timelink.enums.RoleEnum;
import com.timelink.managers.BudgetedWorkPackageHoursManager;
import com.timelink.managers.EmployeeManager;
import com.timelink.managers.LabourGradeManager;
import com.timelink.managers.ProjectManager;
import com.timelink.managers.WorkPackageManager;
import com.timelink.services.WorkPackageCodeService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("serial")
@SessionScoped
@Named("BudgetWorkPackageController")
public class BudgetWorkPackageController implements Serializable {
  
  @Inject ProjectManager pm;
  @Inject Session ses;
  @Inject LabourGradeManager lgm;
  @Inject EmployeeManager em;
  @Inject WorkPackageManager wpm;
  @Inject BudgetedWorkPackageHoursManager bhm;
  @Inject WorkPackageCodeService workPackageCodeService;
  private Integer responsibleEngineerId;
  private String wpCode;
  private String wpDescription;
  private Project currentProject;
  private List<LabourGrade> labourGrades;
  private WorkPackage editingWorkPackageId;
  
  public List<Project> getProjects() {
    return pm.findByProjectManager(ses.getCurrentEmployee().getEmployeeId());
  }

  /**
   * Returns the current project's id.
   * @return the currentProject
   */
  public Integer getCurrentProjectId() {
    if (currentProject == null) {
      return null;
    }
    return currentProject.getProjectNumber();
  }

  /**
   * Sets the current project to one with the given
   * ID.
   * @param currentProject the currentProject to set
   */
  public void setCurrentProjectId(Integer currentProject) {
    if (this.currentProject != null
        && this.currentProject.getProjectNumber() == currentProject) {
      return;
    }
    this.currentProject = pm.find(currentProject);
  }
  
  public Project getCurrentProject() {
    return currentProject;
  }
  
  /**
   * Saves the planned project.
   * @return null to reload the page.
   */
  public String save() {
    //Save all planned hours in the project
    //Remove any planned hours that have 0 manDays.
    for (WorkPackage wp : currentProject.getWorkPackages()) {
      for (LabourGrade lg : getLabourGrades()) {
        if (wp.getPlannedHourFromLabourGrade(lg.getLabourGradeId()).getManDay() != 0) {
          wp.getPlannedHourFromLabourGrade(lg.getLabourGradeId()).setLabourGrade(lg);
          wp.getPlannedHourFromLabourGrade(lg.getLabourGradeId()).setWorkPackageLineId(wp);
        } else {
          wp.removePlannedHourByLabourGrade(lg.getLabourGradeId());
        }
      }
    }
    pm.merge(currentProject);
    currentProject = pm.find(currentProject.getProjectNumber());
    return null;
  }
  
  
  //WORKPACKAGE MODAL STUFF
  
  /**
   * Returns the wpCode.
   * @return the wpCode
   */
  public String getWpCode() {
    return wpCode;
  }
  
  /**
   * Sets the wpCode to wpCode.
   * @param wpCode the wpCode to set
   */
  public void setWpCode(String wpCode) {
    this.wpCode = wpCode;
  }
  
  /**
   * Returns the wpDescription.
   * @return the wpDescription
   */
  public String getWpDescription() {
    return wpDescription;
  }
  
  /**
   * Sets the wpDescription to wpDescription.
   * @param wpDescription the wpDescription to set
   */
  public void setWpDescription(String wpDescription) {
    this.wpDescription = wpDescription;
  }
  
  /**
   * Returns the responsibleEngineer for the current project.
   * @return the responsibleEngineer
   */
  public Integer getResponsibleEngineer() {
    return responsibleEngineerId;
  }
  
  /**
   * Sets the responsibleEngineer to one with the given ID.
   * @param responsibleEngineerId the responsibleEngineer to set
   */
  public void setResponsibleEngineer(Integer responsibleEngineerId) {
    this.responsibleEngineerId = responsibleEngineerId;
  }
  
  /**
   * Creates a new work package with the entered details.
   * @return null to reload the page.
   */
  public String createWorkPackage() {
    WorkPackage wp = new WorkPackage();
    wp.setCode(workPackageCodeService.generateNewCode(currentProject, getWpCode()));
    wp.setDescription(getWpDescription());
    wp.setProject(currentProject);
    wp.setResponsibleEngineer(em.find(getResponsibleEngineer()));
    wpm.persist(wp);
    setCurrentProjectId(currentProject.getProjectNumber());
    save();
    return null;
  }
  
  /**
   * Edits the work package with Id matching editingWorkPackageId.
   * @return null to reload the page.
   */
  public String editWorkPackage() {
    //WorkPackage wp = wpm.find(editingWorkPackageId);
    //wp.setCode(getWpCode());
    editingWorkPackageId.setDescription(getWpDescription());
    editingWorkPackageId.setProject(currentProject);
    //wp.setResponsibleEngineer(em.find(getResponsibleEngineer()));
    wpm.merge(editingWorkPackageId);
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
  
  /**
   * Returns a list of all labour grades in the database.
   * @return A list of all labour grades in the database.
   */
  public List<LabourGrade> getLabourGrades() {
    if (labourGrades == null) {
      labourGrades = lgm.getAllLabourGrades();
    }
    
    return labourGrades;
  }

  /**
   * Returns the Id of the work package to edit.
   * @return the editingWorkPackageId
   */
  public WorkPackage getEditingWorkPackageId() {
    return editingWorkPackageId;
  }

  /**
   * Sets the Id of the work package to edit.
   * @param editingWorkPackageId the editingWorkPackageId to set
   */
  public void setEditingWorkPackageId(WorkPackage editingWorkPackageId) {
    this.editingWorkPackageId = editingWorkPackageId;
  }
  
  /**
   * Returns a list of work packages that have not been charged in the current project.
   * @return A list of non charged work packages.
   */
  public List<WorkPackage> getNonChargedWorkPackages() {
    if (currentProject != null) {
      List<WorkPackage> list = currentProject.getWorkPackages();
      List<WorkPackage> newList = new ArrayList<WorkPackage>();
      for (WorkPackage wp : list) {
        if (!wp.isCharged()) {
          newList.add(wp);
        }
      }
      return newList;
    }
    return null;
  }
  
}
