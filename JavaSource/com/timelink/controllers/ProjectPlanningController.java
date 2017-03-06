package com.timelink.controllers;

import com.timelink.Session;
import com.timelink.ejbs.Employee;
import com.timelink.ejbs.LabourGrade;
import com.timelink.ejbs.Project;
import com.timelink.ejbs.WorkPackage;
import com.timelink.managers.BudgetedHoursManager;
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
  @Inject BudgetedHoursManager bhm;
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
   * Returns a workpackage code that's been incremented
   * on the same 'level' as the given code.
   * @param code The code to increment
   * @return An incremented WP code.
   */
  private String incrementSameLevel(String code) {
    StringBuilder sb = new StringBuilder(code);
    
    //Find the first non-zero char
    int toBeInc = sb.indexOf("0") - 1;
    
    //Increment it
    if (Character.isDigit((sb.charAt(toBeInc)))) {
      char digit = sb.charAt(toBeInc);
      if (digit < '9') {
        ++digit;
        sb.setCharAt(toBeInc, digit);
      } else {
        sb.setCharAt(toBeInc, 'A');
      }
    } else if (Character.isAlphabetic((sb.charAt(toBeInc)))) {
      //If it is not a digit increment anyway
      char digit = sb.charAt(toBeInc);
      if (digit < 'Z') {
        ++digit;
        sb.setCharAt(toBeInc, digit);
      } else {
        //Can't increment past Z
        throw new IllegalArgumentException("Cannot increment this workpackage number past " + code);
      }
    }
    
    //if the char is a period, it is the first in a level
    if (sb.charAt(toBeInc) == '.') {
      sb.setCharAt(toBeInc, '1');
    }
    
    return sb.toString();
  }
  
  /**
   * Finds the largest code on the given workpackage 'level.'
   * @param code The code to search
   * @return The largest wp code on the given 'level.'
   */
  private String findLargestCodeSameLevel(String code) {
    String highest = code;
    for (WorkPackage wp : currentProject.getWorkPackages()) {
      int indexOfChk = wp.getCode().indexOf('0');
      int indexOfCur = code.indexOf('0');
      if (code.substring(0, indexOfChk - 1).equals(wp.getCode().substring(0, indexOfCur - 1))) {
        if (indexOfChk == indexOfCur) {
          if (wp.getCode().charAt(indexOfChk - 1) > highest.charAt(indexOfCur - 1)) {
            highest = wp.getCode();
          }
        }
      }
    }
    
    return highest;
  }
  
  private String findNextLevel(String code) {
    StringBuilder sb = new StringBuilder(code);
    sb.setCharAt(code.indexOf('0'), '.');
    return sb.toString();
  }
  
  private String findNewCode(String code) {
    if (code == null) {
      return incrementSameLevel(findLargestCodeSameLevel("100000000"));
    } else {
      return incrementSameLevel(findLargestCodeSameLevel(findNextLevel(code)));
    }
  }
  
  /**
   * Creates a new work package with the entered details.
   * @return null to reload the page.
   */
  public String createWorkPackage() {
    WorkPackage wp = new WorkPackage();
    wp.setCode(findNewCode(getWpCode()));
    wp.setDescription(getWpDescription());
    wp.setProject(currentProject);
    wp.setResponsibleEngineer(em.find(getResponsibleEngineer()));
    wpm.persist(wp);
    setCurrentProjectId(currentProject.getProjectNumber());
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
    //setCurrentProjectId(currentProject.getProjectNumber());
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
  
  
  
}
