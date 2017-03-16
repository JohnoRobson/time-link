package com.timelink.controllers;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.timelink.Session;
import com.timelink.ejbs.EstimatedWorkPackageHours;
import com.timelink.ejbs.LabourGrade;
import com.timelink.ejbs.WorkPackage;
import com.timelink.managers.EstimatedWorkPackageHoursManager;
import com.timelink.managers.LabourGradeManager;
import com.timelink.managers.WorkPackageManager;
import com.timelink.services.WeekNumberService;

@SuppressWarnings("serial")
@SessionScoped
@Named("EstimateController")
public class EstimateWorkPackageEffortController implements Serializable {
  @Inject Session session;
  @Inject WorkPackageManager wpm;
  @Inject EstimatedWorkPackageHoursManager ewm;
  @Inject WeekNumberService weekNumberService;
  @Inject LabourGradeManager lgm;
  
  private Integer selectedWeek;
  private Integer selectedYear;
  private String selectedDate;
  private List<LabourGrade> labourGrades;
  
  private WorkPackage selectedWorkPackage;
  private WorkPackage selectedWorkPackageCreation;
  private HashSet<EstimatedWorkPackageHours> estimatedHours;
  
  /**
   * returns the selectedWorkPackage.
   * @return the selectedWorkPackage
   */
  public WorkPackage getSelectedWorkPackage() {
    return selectedWorkPackage;
  }
  
  /**
   * Sets the selectedWorkPackage to selectedWorkPackage.
   * @param selectedWorkPackage the selectedWorkPackage to set
   */
  public void setSelectedWorkPackage(WorkPackage selectedWorkPackage) {
    this.selectedWorkPackage = selectedWorkPackage;
  }
  
  /**
   * returns the selectedWorkPackageId.
   * @return the selectedWorkPackageId
   */
  public Integer getSelectedWorkPackageId() {
    if (selectedWorkPackage != null) {
      return selectedWorkPackage.getWorkPackageId();
    }
    
    return null;
  }
  
  /**
   * Sets the selectedWorkPackageId to selectedWorkPackageId.
   * @param selectedWorkPackageId the selectedWorkPackageId to set
   */
  public void setSelectedWorkPackageId(Integer selectedWorkPackageId) {
    if (selectedWorkPackageId == null) {
      return;
    }
    
    if (this.selectedWorkPackage != null
        && this.selectedWorkPackage.getWorkPackageId() == selectedWorkPackageId) {
      return;
    }
    
    WorkPackage wp = wpm.find(selectedWorkPackageId);
    if (wp != null) {
      wpm.detach(wp);
      this.selectedWorkPackage = wpm.find(selectedWorkPackageId);
    }
  }
  
  /**
   * returns the selectedWorkPackageCreationId.
   * @return the selectedWorkPackageCreationId
   */
  public Integer getSelectedWorkPackageCreationId() {
    if (selectedWorkPackageCreation != null) {
      return selectedWorkPackageCreation.getWorkPackageId();
    }
    
    return null;
  }
  
  /**
   * Sets the selectedWorkPackageId to selectedWorkPackageId.
   * @param selectedWorkPackageId the selectedWorkPackageId to set
   */
  public void setSelectedWorkPackageCreationId(Integer selectedWorkPackageCreationId) {
    if (selectedWorkPackageCreationId == null) {
      return;
    }
    
    if (this.selectedWorkPackageCreation != null
        && this.selectedWorkPackageCreation.getWorkPackageId() == selectedWorkPackageCreationId) {
      return;
    }
    
    WorkPackage wp = wpm.find(selectedWorkPackageCreationId);
    if (wp != null) {
      wpm.detach(wp);
      this.selectedWorkPackageCreation = wpm.find(selectedWorkPackageCreationId);
    }
  }
  
  /**
   * Returns the estimatedHours.
   * @return the estimatedHours
   */
  public HashSet<EstimatedWorkPackageHours> getEstimatedHours() {
    return estimatedHours;
  }
  
  /**
   * Sets the estimatedHours to estimatedHours.
   * @param estimatedHours the estimatedHours to set
   */
  public void setEstimatedHours(HashSet<EstimatedWorkPackageHours> estimatedHours) {
    this.estimatedHours = estimatedHours;
  }
  
  public List<WorkPackage> getWorkPackages() {
    return wpm.getAllWithResponsibleEngineer(session.getCurrentEmployee());
  }

  /**
   * @return the selectedWeek
   */
  public Integer getSelectedWeek() {
    return selectedWeek;
  }

  /**
   * @param selectedWeek the selectedWeek to set
   */
  public void setSelectedWeek(Integer selectedWeek) {
    this.selectedWeek = selectedWeek;
  }

  /**
   * @return the selectedYear
   */
  public Integer getSelectedYear() {
    return selectedYear;
  }

  /**
   * @param selectedYear the selectedYear to set
   */
  public void setSelectedYear(Integer selectedYear) {
    this.selectedYear = selectedYear;
  }
  
  /**
   * @return the selectedDate
   */
  public String getSelectedDate() {
    return selectedDate;
  }

  /**
   * @param selectedDate the selectedDate to set
   */
  public void setSelectedDate(String selectedDate) {
    this.selectedDate = selectedDate;
  }

  /**
   * @return the selectedWorkPackageCreation
   */
  public WorkPackage getSelectedWorkPackageCreation() {
    return selectedWorkPackageCreation;
  }

  /**
   * @param selectedWorkPackageCreation the selectedWorkPackageCreation to set
   */
  public void setSelectedWorkPackageCreation(WorkPackage selectedWorkPackageCreation) {
    this.selectedWorkPackageCreation = selectedWorkPackageCreation;
  }

  public List<EstimatedWorkPackageHours> getAllWithWorkPackage() {
    if (selectedWorkPackage == null) {
      return null;
    }
    return ewm.getAllWithWorkPackage(selectedWorkPackage);
  }
  
  public String getWeekYear(EstimatedWorkPackageHours ew) {
    return ("Week: " + weekNumberService.getWeekNumber(ew.getDateCreated()) +
        " Year: " + (ew.getDateCreated().getYear() + 1900));
  }
  
  public List<LabourGrade> getLabourGrades() {
    if (labourGrades == null) {
      labourGrades = lgm.getAllLabourGrades();
    }
    
    return labourGrades;
  }
  
  @PostConstruct
  public void reset() {
    labourGrades = lgm.getAllLabourGrades();
    estimatedHours = new HashSet<EstimatedWorkPackageHours>();
    for (LabourGrade lg : labourGrades) {
      EstimatedWorkPackageHours ew = new EstimatedWorkPackageHours();
      ew.setLabourGrade(lg);
      estimatedHours.add(ew);
    }
    selectedWeek = null;
    selectedYear = null;
    selectedDate = null;
    selectedWorkPackage = null;
    selectedWorkPackageCreation = null;
  }
  
  public EstimatedWorkPackageHours getEstimateFromLabourGrade(int lgId) {
    if (estimatedHours != null) {
      for (EstimatedWorkPackageHours ew : estimatedHours) {
        if (ew.getLabourGrade().getLabourGradeId() == lgId) {
          return ew;
        }
      }
    }

    return getNewEstimateWorkPackageFromLabourGrade(lgId);
  }
  
  public EstimatedWorkPackageHours getNewEstimateWorkPackageFromLabourGrade(int lgId) {
    EstimatedWorkPackageHours ew = new EstimatedWorkPackageHours();
    
    LabourGrade lg = new LabourGrade();
    lg = lgm.find(lgId);
    ew.setLabourGrade(lg);
    estimatedHours.add(ew);
    return ew;
  }
  
  public String createEstimate() {
    Date d = weekNumberService.getDateFromWeekYear(selectedWeek, selectedYear);
    for (EstimatedWorkPackageHours ew : estimatedHours) {
      ew.setDateCreated(d);
      ew.setWorkPackage(selectedWorkPackageCreation);
      ewm.persist(ew);
    }
    wpm.merge(selectedWorkPackageCreation);
    
    reset();
    
    return null;
  }
  
  public List<EstimatedWorkPackageHours> viewEstimate() {
    if (selectedWorkPackage != null && selectedDate != null) {
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      Date d = new Date();
      try {
        d = df.parse(selectedDate);
        d.setHours(0);
        d.setMinutes(0);
        d.setSeconds(0);
      } catch (ParseException e) {
        e.printStackTrace();
      }
      return ewm.find(selectedWorkPackage, d);
    }
    return null;
  }
  
}
