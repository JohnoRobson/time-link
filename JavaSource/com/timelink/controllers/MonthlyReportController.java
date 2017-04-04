package com.timelink.controllers;

import com.timelink.Session;
import com.timelink.ejbs.LabourGrade;
import com.timelink.ejbs.Project;
import com.timelink.ejbs.WorkPackage;
import com.timelink.managers.LabourGradeManager;
import com.timelink.managers.ProjectManager;

import org.primefaces.component.datatable.DataTable;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("serial")
@SessionScoped
@Named("MonthlyReportController")
public class MonthlyReportController implements Serializable {
  @Inject LabourGradeManager lgm;
  @Inject LabourReportController lrc;
  @Inject ProjectManager pm;
  @Inject Session ses;
  
  private Project selectedProject;
  private int projectId;
  
  /**
   * Report the selectedProject.
   * @return the selectedProject
   */
  public Project getSelectedProject() {
    return selectedProject;
  }

  /**
   * Set the selectedProject to selectedProject.
   * @param selectedProject the selectedProject to set
   */
  public void setSelectedProject(Project selectedProject) {
    this.selectedProject = selectedProject;
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
  }
  
  /**
   * Get all projects currently managed by this Employee.
   * @return list of projects
   */
  public List<Project> getProjects() {
    return pm.findByProjectManager(ses.getCurrentEmployee()
        .getEmployeeId());
  }
  
  /**
   * Get all work packages for a selected project.
   * @return list of work packages
   */
  public List<WorkPackage> getWorkPackages() {
    if (selectedProject != null) {
      return selectedProject.getWorkPackages();
    }
    return null;
  }
  
  /**
   * Get total budget for a workpackage.
   * @param workPackage to get budget of
   * @return total workpackage budget
   */
  public Integer getBudgeted(WorkPackage workPackage) {
    lrc.setSelectedProject(selectedProject);
    lrc.setSelectedWorkPackage(workPackage);
    Integer budget = new Integer(0);
    for (LabourGrade lb : lgm.getAllLabourGrades()) {
      budget += workPackage.getPlannedHourFromLabourGrade(lb.getLabourGradeId()).getManDay();
    }
    return budget;
  }
  
  /**
   * Get total estimated budget for a workpackage.
   * @param workPackage to get estimated budget of
   * @return total estimated budget
   */
  public Integer getEstimated(WorkPackage workPackage) {
    lrc.setSelectedProject(selectedProject);
    lrc.setSelectedWorkPackage(workPackage);
    Integer estimated = new Integer(0);
    for (LabourGrade lb : lgm.getAllLabourGrades()) {
      estimated += workPackage.getEstimatedHourFromLabourGrade(lb.getLabourGradeId()).getManDay();
    }
    return estimated;
  }
  
  /**
   * Get total budget to complete for a workpackage.
   * @param workPackage to get budget to complete
   * @return total budget to complete
   */
  public Float getBudgetToComplete(WorkPackage workPackage) {
    lrc.setSelectedProject(selectedProject);
    lrc.setSelectedWorkPackage(workPackage);
    Float complete = new Float(0);
    for (LabourGrade lb : lgm.getAllLabourGrades()) {
      complete += lrc.getBudgetToComplete(lb.getLabourGradeId());
    }
    return complete;
  }
  
  /**
   * Get total budgeted hours for a work package.
   * @param workPackage to get budgeted hours
   * @return total budgeted hours
   */
  public Float getTotalBudgeted(WorkPackage workPackage) {
    lrc.setSelectedWorkPackage(workPackage);
    return lrc.getTotalBudgeted();
  }
  
  /**
   * Get total estimated hours from workpackage.
   * @param workPackage to get estimated hours
   * @return total estimated hours
   */
  public Float getTotalEstimated(WorkPackage workPackage) {
    lrc.setSelectedWorkPackage(workPackage);
    return lrc.getTotalEstimated();
  }
  
  /**
   * Get total budget to complete for a workpackage.
   * @param workPackage to get budget to complete
   * @return total budget to complete
   */
  public Float getTotalBudgetToComplete(WorkPackage workPackage) {
    lrc.setSelectedProject(selectedProject);
    lrc.setSelectedWorkPackage(workPackage);
    return lrc.getTotalBudgetedToComplete();
  }
  
  /**
   * Get total variance percent for a workpackage.
   * @param workPackage to get variance percent
   * @return total variance percent
   */
  public Float getVariancePercent(WorkPackage workPackage) {
    lrc.setSelectedProject(selectedProject);
    lrc.setSelectedWorkPackage(workPackage);
    return lrc.getTotalVariancePercent();
  }
  
}
