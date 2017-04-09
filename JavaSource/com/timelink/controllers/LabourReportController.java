package com.timelink.controllers;

import com.timelink.Session;
import com.timelink.ejbs.BudgetedWorkPackageWorkDays;
import com.timelink.ejbs.EstimatedWorkPackageWorkDays;
import com.timelink.ejbs.Hours;
import com.timelink.ejbs.LabourGrade;
import com.timelink.ejbs.Project;
import com.timelink.ejbs.WorkPackage;
import com.timelink.managers.BudgetedWorkPackageWorkDaysManager;
import com.timelink.managers.EstimatedWorkPackageWorkDaysManager;
import com.timelink.managers.HoursManager;
import com.timelink.managers.LabourGradeManager;
import com.timelink.managers.WorkPackageManager;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("serial")
@SessionScoped
@Named("LabourReportController")
public class LabourReportController implements Serializable {
  @Inject Session ses;
  @Inject WorkPackageManager wpm;
  @Inject BudgetedWorkPackageWorkDaysManager bwm;
  @Inject EstimatedWorkPackageWorkDaysManager ewm;
  @Inject LabourGradeManager lgm;
  @Inject HoursManager hm;
  
  private Project selectedProject;
  private WorkPackage selectedWorkPackage;
  private int projectId;
  private Integer workPackageId;
  
  public LabourReportController() {}
  
  public LabourReportController(Session ses, WorkPackageManager wpm, BudgetedWorkPackageWorkDaysManager bwm,
		  EstimatedWorkPackageWorkDaysManager ewm,LabourGradeManager lgm,
		  HoursManager hm) {
	  this.ses = ses;
	  this.wpm = wpm;
	  this.bwm = bwm;
	  this.ewm = ewm;
	  this.lgm = lgm;
	  this.hm = hm;
  }
  
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
   * Return the selectedWorkPackage.
   * @return the selectedWorkPackage
   */
  public WorkPackage getSelectedWorkPackage() {
    return selectedWorkPackage;
  }

  /**
   * Set the selectedWorkPackage to selectedWorkPackage.
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
  }
  
  /**
   * Returns the workPackageId.
   * @return the workPackageId
   */
  public Integer getWorkPackageId() {
    return workPackageId;
  }

  /**
   * Sets the workPackageId to workPackageId.
   * @param workPackageId the workPackageId to set
   */
  public void setWorkPackageId(Integer workPackageId) {
    if (workPackageId != null) {
      this.workPackageId = workPackageId;
      selectedWorkPackage = wpm.find(workPackageId);
      this.selectedProject = selectedWorkPackage.getProject();
    }
  }

  /**
   * Get all work packages that are assigned to the current
   * employee.
   * @return list of all work packages assigned to current employee
   */
  public List<WorkPackage> getWorkPackages() {
    return wpm.getAllWithResponsibleEngineer(ses.getCurrentEmployee());
  }
  
  /**
   * Return all labour grades.
   * @return list of all labour grades
   */
  public List<LabourGrade> getLabourGrades() {
    return lgm.getAllLabourGrades();
  }
  
  /**
   * Return the most recent date of the estimated hours.
   * @return most recent date from estimated hours
   */
  public Date getDate() {
    if (selectedWorkPackage != null) {
      List<EstimatedWorkPackageWorkDays> el = 
          ewm.getAllWithWorkPackageUniqueDate(selectedWorkPackage);
      Date mostRecent = new Date();
      for (EstimatedWorkPackageWorkDays eh : el) {
        if (eh.getDateCreated().after(mostRecent)) {
          mostRecent = eh.getDateCreated();
        }
      }
      return mostRecent;
    }
    return null;
  }
  
  /**
   * Returns a BudgetedWorkPackageHours with the given labourGradeId.
   * @param labourGradeId The labourGradeId to be searched
   * @return A BudgetedWorkPackageHours
   */
  public BudgetedWorkPackageWorkDays getBudgetedHourByLabourGrade(int labourGradeId) {
    if (selectedWorkPackage != null) {
      //return bwm.find(selectedWorkPackage, labourGradeId);
    }
    return null;
  }
  
  /**
   * Get budget to complete. Which is the estimate,
   * minus the actual hours.
   * @param labourGradeId to find hours by
   * @return estimatedHours minus the actual hours
   */
  public Float getBudgetToComplete(Integer labourGradeId) {
    if (selectedWorkPackage != null) {
      float total = 0;
      List<Hours> result = hm.find(selectedWorkPackage.getProject().getProjectNumber(),
          selectedWorkPackage.getWorkPackageId(), labourGradeId);
      for (Hours h : result) {
        total += h.getHour();
      }
      return (total / 8) + selectedWorkPackage
          .getEstimatedHourFromLabourGrade(labourGradeId).getManDay();
    }
    return null;
  }
  
  /**
   * Returns the man days from a planned hour for the labour grade.
   * @param labourGradeId to find planned hour by
   * @return man days from found planned hour
   */
  public Integer getManDaysForLabourGrade(Integer labourGradeId) {
    if (selectedWorkPackage != null) {
      return selectedWorkPackage.getPlannedHourFromLabourGrade(labourGradeId).getManDay();
    }
    return null;
  }
  
  /**
   * Returns the difference between the actual timesheet hours, and
   * estimated time to complete for a work package.
   * @param labourGradeId to identify labour grade by
   * @return Float variance between timesheet hours and estimated time
   */
  public Float getVariance(Integer labourGradeId) {
    if (selectedWorkPackage != null) {
      return getManDaysForLabourGrade(labourGradeId)
          - getBudgetToComplete(labourGradeId);
    }
    return null;
  }
  
  /**
   * Return the difference between the actual timesheet hours, and
   * estimated time to complete for work package, returned as percent.
   * @param labourGradeId to find hours by
   * @return Float variance between timesheet hours and estimated time given as a percent 
   */
  public Float getVariancePercent(Integer labourGradeId) {
    Integer plannedHours = getManDaysForLabourGrade(labourGradeId);
    if (selectedWorkPackage != null && plannedHours != null && plannedHours.intValue() != 0) {
      return getVariance(labourGradeId) / plannedHours;
    }
    return null;
  }
  
  /**
   * Return the total labour cost of a work package budget.
   * @return total labour cost of work package budget
   */
  public Float getTotalBudgeted() {
    if (selectedWorkPackage != null) {
      Float total = new Float(0);
      for (LabourGrade lb : lgm.getAllLabourGrades()) {
        total += getManDaysForLabourGrade(lb.getLabourGradeId()) * lb.getCostRate();
      }
      return total;
    }
    return null;
  }
  
  /**
   * Return the total labour cost of a work package estimation.
   * @return total labour cost of work package estimation
   */
  public Float getTotalEstimated() {
    if (selectedWorkPackage != null) {
      Float total = new Float(0);
      for (LabourGrade lb : lgm.getAllLabourGrades()) {
        total += selectedWorkPackage.getEstimatedHourFromLabourGrade(lb.getLabourGradeId())
            .getManDay() * lb.getCostRate();
      }
      return total;
    }
    return null;
  }
  
  /**
   * Return the total labour cost of a work package budgeted to complete.
   * @return total labour cost of work package budgeted to complete
   */
  public Float getTotalBudgetedToComplete() {
    if (selectedWorkPackage != null) {
      Float total = new Float(0);
      for (LabourGrade lb : lgm.getAllLabourGrades()) {
        total += getBudgetToComplete(lb.getLabourGradeId()) * lb.getCostRate();
      }
      return total;
    }
    return null;
  }
  
  /**
   * Return the total labour cost of a work package budgeted to complete.
   * @return total labour cost of work package budgeted to complete
   */
  public Float getTotalVariance() {
    if (selectedWorkPackage != null) {
      Float total = new Float(0);
      for (LabourGrade lb : lgm.getAllLabourGrades()) {
        total += getVariance(lb.getLabourGradeId()) * lb.getCostRate();
      }
      return total;
    }
    return null;
  }
  
  /**
   * Return the total variance percent of a work package.
   * @return total variance percent of a work package
   */
  public Float getTotalVariancePercent() {
    if (selectedWorkPackage != null) {
      Float total = new Float(0);
      Float check = new Float(0);
      int counter = 0;
      for (LabourGrade lb : lgm.getAllLabourGrades()) {
        check = getVariancePercent(lb.getLabourGradeId());
        if (check != null) {
          counter++;
          total += check;
        }
      }
      return counter == 0 ? total : (total / counter);
    }
    return null;
  }
}
