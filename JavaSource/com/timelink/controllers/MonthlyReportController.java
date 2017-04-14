package com.timelink.controllers;

import com.timelink.Session;
import com.timelink.ejbs.EstimatedWorkPackageWorkDays;
import com.timelink.ejbs.LabourGrade;
import com.timelink.ejbs.Project;
import com.timelink.ejbs.WorkPackage;
import com.timelink.managers.EstimatedWorkPackageWorkDaysManager;
import com.timelink.managers.LabourGradeManager;
import com.timelink.managers.ProjectManager;
import com.timelink.managers.WorkPackageManager;
import com.timelink.services.LabourService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("serial")
@SessionScoped
@Named("MonthlyReportController")
public class MonthlyReportController implements Serializable {
  @Inject LabourGradeManager lgm;
  @Inject WorkPackageManager wpm;
  @Inject EstimatedWorkPackageWorkDaysManager ewm;
  @Inject LabourReportController lrc;
  @Inject ProjectManager pm;
  @Inject Session ses;
  @Inject LabourService ls;
  
  private Project selectedProject;
  private int projectId;
  private Integer selectedDate;
  
  public MonthlyReportController() {}
  
  public MonthlyReportController(LabourGradeManager lgm, LabourReportController lrc, 
		  ProjectManager pm, Session ses) {
	  this.lgm = lgm;
	  this.lrc = lrc;
	  this.pm = pm;
	  this.ses = ses;
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
   * Return the selecteDate.
   * @return the selectedDate
   */
  public Integer getSelectedDate() {
    return selectedDate;
  }

  /**
   * Set the selectedDate to selectedDate.
   * @param selectedDate the selectedDate to set
   */
  public void setSelectedDate(Integer selectedDate) {
    this.selectedDate = selectedDate;
  }

  /**
   * Get all projects currently managed by this Employee.
   * @return list of projects
   */
  public List<Project> getProjects() {
    if (ses.isProjectManager() && ses.isProjectManagerAssistant()) {
      return pm.findByProjectManagerAndAssistant(ses.getCurrentEmployee().getEmployeeId());
    } else if (ses.isProjectManager()) {
      return pm.findByProjectManager(ses.getCurrentEmployee().getEmployeeId());
    } else if (ses.isProjectManagerAssistant()) {
      return pm.findByProjectManagerAssistant(ses.getCurrentEmployee().getEmployeeId());
    }
    return null;
  }
  
  /**
   * Return possible months to select report by.
   * @return Months to select report by
   */
  public List<Integer> getMonths() {
    if (selectedProject != null) {
      Calendar ca = Calendar.getInstance();
      List<Integer> months = new ArrayList<Integer>();
      EstimatedWorkPackageWorkDays latest = ewm.findLatest(selectedProject);
      EstimatedWorkPackageWorkDays earliest = ewm.findEarliest(selectedProject);
      if (latest != null && earliest != null) {
        ca.setTime(latest.getDateCreated());
        Integer latestInt = ca.get(Calendar.MONTH);
        ca.setTime(earliest.getDateCreated());
        Integer earliestInt = ca.get(Calendar.MONTH);
        if (latestInt == earliestInt) {
          months.add(latestInt);
        } else {
          while (earliestInt != latestInt) {
            months.add(earliestInt++);
          }
        }
        return months;
      }
    }
    return null;
  }
  
  /**
   * Get all work packages for a selected project.
   * @return list of work packages
   */
  public List<WorkPackage> getWorkPackages() {
    if (selectedProject != null && selectedDate != null) {
      return selectedProject.getWorkPackages();
    }
    return null;
  }
  
  /**
   * Return the start date of month number.
   * @param month to get date of
   * @return start date of week number
   */
  public Date getStartDateFromMonth(Integer month) {
    Calendar ca = new GregorianCalendar();
    ca.set(Calendar.MONTH, month);
    ca.set(Calendar.DAY_OF_MONTH, 1);
    return ca.getTime();
  }
  
  /**
   * Return start date of month number.
   * @param month to get date of
   * @return start date of month number
   */
  public Date getEndDateFromMonth(Integer month) {
    Calendar ca = new GregorianCalendar();
    ca.set(Calendar.MONTH, month);
    ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
    return ca.getTime();
  }
  
  /**
   * Get total budget for a workpackage.
   * @param workPackage to get budget of
   * @return total workpackage budget
   */
  public Integer getBudgeted(WorkPackage workPackage) {
    Integer budget = new Integer(0);
    for (LabourGrade lb : lgm.getAllLabourGrades()) {
      budget += ls.getBudgeted(workPackage, lb.getLabourGradeId());
    }
    return budget;
  }
  
  /**
   * Get total estimated budget for a workpackage.
   * @param workPackage to get estimated budget of
   * @return total estimated budget
   */
  public Integer getEstimated(WorkPackage workPackage) {
    Integer estimated = new Integer(0);
    for (LabourGrade lb : lgm.getAllLabourGrades()) {
      estimated += ls.getEstimated(workPackage, lb.getLabourGradeId(),
          getStartDateFromMonth(selectedDate), getEndDateFromMonth(selectedDate));
    }
    return estimated;
  }
  
  /**
   * Get total budget to complete for a workpackage.
   * @param workPackage to get budget to complete
   * @return total budget to complete
   */
  public Float getBudgetToComplete(WorkPackage workPackage) {
    Float complete = new Float(0);
    for (LabourGrade lb : lgm.getAllLabourGrades()) {
      complete += ls.getBudgetToComplete(workPackage, lb.getLabourGradeId(),
          getStartDateFromMonth(selectedDate), getEndDateFromMonth(selectedDate));
    }
    return complete;
  }
  
  /**
   * Get total budgeted hours for a work package.
   * @param workPackage to get budgeted hours
   * @return total budgeted hours
   */
  public Float getTotalBudgeted(WorkPackage workPackage) {
    if (selectedDate != null) {
      Float total = new Float(0);
      for (LabourGrade lb : lgm.getAllLabourGrades()) {
        total += ls.getBudgeted(workPackage, lb.getLabourGradeId()) * lb.getCostRate();
      }
      return total;
    }
    return null;
  }
  
  /**
   * Get total estimated hours from workpackage.
   * @param workPackage to get estimated hours
   * @return total estimated hours
   */
  public Float getTotalEstimated(WorkPackage workPackage) {
    if (selectedDate != null) {
      Float total = new Float(0);
      for (LabourGrade lb : lgm.getAllLabourGrades()) {
        total += ls.getEstimated(workPackage, lb.getLabourGradeId(),
            getStartDateFromMonth(selectedDate), 
            getEndDateFromMonth(selectedDate)) * lb.getCostRate();
      }
      return total;
    }
    return null;
  }
  
  /**
   * Get total budget to complete for a workpackage.
   * @param workPackage to get budget to complete
   * @return total budget to complete
   */
  public Float getTotalBudgetToComplete(WorkPackage workPackage) {
    if (selectedDate != null) {
      Float total = new Float(0);
      for (LabourGrade lb : lgm.getAllLabourGrades()) {
        total += ls.getBudgetToComplete(workPackage, lb.getLabourGradeId(),
            getStartDateFromMonth(selectedDate), 
            getEndDateFromMonth(selectedDate)) * lb.getCostRate();
      }
      return total;
    }
    return null;
  }
  
  /**
   * Get total variance percent for a workpackage.
   * @param workPackage to get variance percent
   * @return total variance percent
   */
  public Float getVariancePercent(WorkPackage workPackage) {
    if (selectedDate != null) {
      Integer plannedHours = getBudgeted(workPackage);
      if (workPackage != null && plannedHours != null && plannedHours.intValue() != 0
          && selectedDate != null) {
        return (plannedHours - getBudgetToComplete(workPackage)) / plannedHours;
      }
    }
    return null; 
  }
}
