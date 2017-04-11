package com.timelink.controllers;

import com.timelink.Session;
import com.timelink.ejbs.EstimatedWorkPackageWorkDays;
import com.timelink.ejbs.LabourGrade;
import com.timelink.ejbs.Project;
import com.timelink.ejbs.WorkPackage;
import com.timelink.managers.BudgetedWorkPackageWorkDaysManager;
import com.timelink.managers.EstimatedWorkPackageWorkDaysManager;
import com.timelink.managers.HoursManager;
import com.timelink.managers.LabourGradeManager;
import com.timelink.managers.WorkPackageManager;
import com.timelink.services.LabourService;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
@Named("LabourReportController")
public class LabourReportController implements Serializable {
  @Inject Session ses;
  @Inject WorkPackageManager wpm;
  @Inject BudgetedWorkPackageWorkDaysManager bwm;
  @Inject EstimatedWorkPackageWorkDaysManager ewm;
  @Inject LabourGradeManager lgm;
  @Inject HoursManager hm;
  @Inject LabourService ls;
  
  private Project selectedProject;
  private WorkPackage selectedWorkPackage;
  private int projectId;
  private Integer workPackageId;
  private Integer selectedDate;
  
  public LabourReportController() {}
  
  /**
   * Constructor for testing.
   * @param ses Sessions
   * @param wpm WorkPackageManager
   * @param bwm BudgetedWorkPackageWorkDaysManager
   * @param ewm EstimatedWorkPackageWorkDaysManager
   * @param lgm LabourGradeManager
   * @param hm HoursManager
   */
  public LabourReportController(Session ses, WorkPackageManager wpm, 
      BudgetedWorkPackageWorkDaysManager bwm,
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
   * Get the selectedDate.
   * @return the selectedDate
   */
  public Integer getSelectedDate() {
    return selectedDate;
  }

  /**
   * Sets the selectedDate to selectedDate.
   * @param selectedDate the selectedDate to set
   */
  public void setSelectedDate(Integer selectedDate) {
    this.selectedDate = selectedDate;
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
   * Return the week of year of a given date.
   * @param date to get week of year of
   * @return week number
   */
  private Integer getWeekOf(Date date) {
    Calendar ca = Calendar.getInstance();
    ca.setTime(date);
    ca.setFirstDayOfWeek(Calendar.SATURDAY);
    return ca.get(Calendar.WEEK_OF_YEAR);
  }
  
  /*
   * if (selectedProject != null) {
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
   */
  
  /**
   * Return possible weeks to select report by.
   * @return Date to select report by
   */
  public List<Integer> getWeeks() {
    if (selectedWorkPackage != null) {
      List<Integer> weeks = new ArrayList<Integer>();
      EstimatedWorkPackageWorkDays latest = ewm.findLatest(selectedWorkPackage);
      EstimatedWorkPackageWorkDays earliest = ewm.findEarliest(selectedWorkPackage);
      if (latest != null && earliest != null) {
        Integer latestInt = getWeekOf(latest.getDateCreated());
        Integer earliestInt = getWeekOf(earliest.getDateCreated());
        if (latestInt == earliestInt) {
          weeks.add(latestInt);
        } else {
          while (earliestInt != latestInt) {
            weeks.add(earliestInt++);
          }
        }
        return weeks;
      }
    }
    return null;
  }
  
  /**
   * Return start date of week number.
   * @param date to get date of
   * @return start date of week number
   */
  public Date getStartDateFromWeek(Integer date) {
    Calendar ca = new GregorianCalendar();
    ca.setFirstDayOfWeek(Calendar.SATURDAY);
    ca.set(Calendar.WEEK_OF_YEAR, date);        
    ca.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY); 
    ca.set(Calendar.HOUR, 0);
    ca.set(Calendar.MINUTE, 0);
    ca.set(Calendar.SECOND, 0);
    return ca.getTime();
  }
  
  /**
   * Return start date of week number.
   * @param date to get date of
   * @return start date of week number
   */
  public Date getEndDateFromWeek(Integer date) {
    Calendar ca = new GregorianCalendar();
    ca.setFirstDayOfWeek(Calendar.SATURDAY);
    ca.set(Calendar.WEEK_OF_YEAR, date);        
    ca.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
    ca.set(Calendar.HOUR, 22);
    ca.set(Calendar.MINUTE, 58);
    ca.set(Calendar.SECOND, 58);
    return ca.getTime();
  }
  
  /**
   * Return the most recent date of the estimated hours.
   * @return most recent date from estimated hours
   */
  public Date getDate() {
    if (selectedWorkPackage != null && selectedDate != null) {
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
  public Integer getBudgetedHourByLabourGrade(int labourGradeId) {
    if (selectedWorkPackage != null && selectedDate != null) {
      return ls.getBudgeted(selectedWorkPackage, labourGradeId);
    }
    return null;
  }
  
  /**
   * Returns a BudgetedWorkPackageHours with the given labourGradeId.
   * @param labourGradeId The labourGradeId to be searched
   * @return A BudgetedWorkPackageHours
   */
  public Integer getEstimatedHourByLabourGrade(int labourGradeId) {
    if (selectedWorkPackage != null && selectedDate != null) {
      return ls.getEstimated(selectedWorkPackage, labourGradeId,
          getStartDateFromWeek(selectedDate),
          getEndDateFromWeek(selectedDate));
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
    if (selectedWorkPackage != null && selectedDate != null) {
      return ls.getBudgetToComplete(selectedWorkPackage, labourGradeId,
          getStartDateFromWeek(selectedDate),
          getEndDateFromWeek(selectedDate));
    }
    return null;
  }
  
  /**
   * Returns the man days from a planned hour for the labour grade.
   * @param labourGradeId to find planned hour by
   * @return man days from found planned hour
   */
  public Integer getManDaysForLabourGrade(Integer labourGradeId) {
    if (selectedWorkPackage != null && selectedDate != null) {
      return ls.getManDaysForLabourGrade(selectedWorkPackage, labourGradeId);
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
    if (selectedWorkPackage != null && selectedDate != null) {
      return ls.getVariance(selectedWorkPackage, labourGradeId,
          getStartDateFromWeek(selectedDate),
          getEndDateFromWeek(selectedDate));
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
    if (selectedWorkPackage != null && plannedHours != null && plannedHours.intValue() != 0
        && selectedDate != null) {
      return getVariance(labourGradeId) / plannedHours;
    }
    return null; 
  }
  
  /**
   * Return the total labour cost of a work package budget.
   * @return total labour cost of work package budget
   */
  public Float getTotalBudgeted() {
    if (selectedWorkPackage != null && selectedDate != null) {
      Float total = new Float(0);
      for (LabourGrade lb : lgm.getAllLabourGrades()) {
        total += getBudgetedHourByLabourGrade(lb.getLabourGradeId()) * lb.getCostRate();
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
    if (selectedWorkPackage != null && selectedDate != null) {
      Float total = new Float(0);
      for (LabourGrade lb : lgm.getAllLabourGrades()) {
        total += getEstimatedHourByLabourGrade(lb.getLabourGradeId()) * lb.getCostRate();
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
    if (selectedWorkPackage != null && selectedDate != null) {
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
    if (selectedWorkPackage != null && selectedDate != null) {
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
    if (selectedWorkPackage != null && selectedDate != null) {
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
