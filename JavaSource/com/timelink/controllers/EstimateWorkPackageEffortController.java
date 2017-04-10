package com.timelink.controllers;

import com.timelink.Session;
import com.timelink.ejbs.EstimatedWorkPackageWorkDays;
import com.timelink.ejbs.LabourGrade;
import com.timelink.ejbs.WorkPackage;
import com.timelink.managers.EstimatedWorkPackageWorkDaysManager;
import com.timelink.managers.LabourGradeManager;
import com.timelink.managers.WorkPackageManager;
import com.timelink.services.WeekNumberService;

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

@SuppressWarnings("serial")
@SessionScoped
@Named("EstimateController")
public class EstimateWorkPackageEffortController implements Serializable {
  @Inject Session session;
  @Inject WorkPackageManager wpm;
  @Inject EstimatedWorkPackageWorkDaysManager ewm;
  @Inject WeekNumberService weekNumberService;
  @Inject LabourGradeManager lgm;
  
  private Integer selectedWeek;
  private Integer selectedYear;
  private String selectedDate;
  private List<LabourGrade> labourGrades;
  
  

  private WorkPackage selectedWorkPackage;
  private WorkPackage selectedWorkPackageCreation;
  private HashSet<EstimatedWorkPackageWorkDays> estimatedHours;
  
  public EstimateWorkPackageEffortController() {}
  
  public EstimateWorkPackageEffortController(Session session,WorkPackageManager wpm,EstimatedWorkPackageWorkDaysManager ewm,
		  WeekNumberService weekNumberService,LabourGradeManager lgm) {
	  this.session = session;
	  this.wpm = wpm;
	  this.ewm = ewm;
	  this.weekNumberService = weekNumberService;
	  this.lgm = lgm;
  }
  
  
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
   * Sets the selectedWorkPackageCreationId to selectedWorkPackageCreationId.
   * @param selectedWorkPackageCreationId the selectedWorkPackageCreationId to set
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
  public HashSet<EstimatedWorkPackageWorkDays> getEstimatedHours() {
    return estimatedHours;
  }
  
  /**
   * Sets the estimatedHours to estimatedHours.
   * @param estimatedHours the estimatedHours to set
   */
  public void setEstimatedHours(HashSet<EstimatedWorkPackageWorkDays> estimatedHours) {
    this.estimatedHours = estimatedHours;
  }
  
  public List<WorkPackage> getWorkPackages() {
    return wpm.getAllWithResponsibleEngineer(session.getCurrentEmployee());
  }

  /**
   * Returns the selectedWeek.
   * @return the selectedWeek
   */
  public Integer getSelectedWeek() {
    return selectedWeek;
  }

  /**
   * Sets the selectedWeek.
   * @param selectedWeek the selectedWeek to set
   */
  public void setSelectedWeek(Integer selectedWeek) {
    this.selectedWeek = selectedWeek;
  }

  /**
   * Returns the selectedYear.
   * @return the selectedYear
   */
  public Integer getSelectedYear() {
    return selectedYear;
  }

  /**
   * Sets the selectedYear.
   * @param selectedYear the selectedYear to set
   */
  public void setSelectedYear(Integer selectedYear) {
    this.selectedYear = selectedYear;
  }
  
  /**
   * Returns the selectedDate.
   * @return the selectedDate
   */
  public String getSelectedDate() {
    return selectedDate;
  }

  /**
   * Sets the selectedDate.
   * @param selectedDate the selectedDate to set
   */
  public void setSelectedDate(String selectedDate) {
    this.selectedDate = selectedDate;
  }

  /**
   * Returns the selectedWorkPackageCreation.
   * @return the selectedWorkPackageCreation
   */
  public WorkPackage getSelectedWorkPackageCreation() {
    return selectedWorkPackageCreation;
  }

  /**
   * Sets the selectedWorkPackageCreation.
   * @param selectedWorkPackageCreation the selectedWorkPackageCreation to set
   */
  public void setSelectedWorkPackageCreation(WorkPackage selectedWorkPackageCreation) {
    this.selectedWorkPackageCreation = selectedWorkPackageCreation;
  }
  
  /**
   * Returns all EstimatedWorkPackageHours associated with the selectedWorkPackage.
   * @return A List of EstimatedWorkPackageHours.
   */
  public List<EstimatedWorkPackageWorkDays> getOnePerWeek() {
    if (selectedWorkPackage == null) {
      return null;
    }
    List<EstimatedWorkPackageWorkDays> list = ewm.getAllWithWorkPackageUniqueDate(selectedWorkPackage);
    
    return list;
  }
  
  public void setLabourGrades(List<LabourGrade> labourGrades) {
		this.labourGrades = labourGrades;
	}
  
  /**
   * Returns a String containing the week number and year of
   * the given EstimatedWorkPackageHours.
   * @param ew the EstimatedWorkPackageHours the be checked.
   * @return A String.
   */
  public String getWeekYear(EstimatedWorkPackageWorkDays ew) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(ew.getDateCreated());
    return ("Week: " + weekNumberService.getWeekNumber(ew.getDateCreated())
      + " Year: " + cal.get(Calendar.YEAR));
  }
  
  /**
   * Returns a List of labourGrades in the database.
   * @return A List of labourGrades.
   */
  public List<LabourGrade> getLabourGrades() {
    if (labourGrades == null) {
      labourGrades = lgm.getAllLabourGrades();
    }
    
    return labourGrades;
  }
  
  /**
   * Resets the state of this SessionBean.
   */
  @PostConstruct
  public void reset() {
    labourGrades = lgm.getAllLabourGrades();
    estimatedHours = new HashSet<EstimatedWorkPackageWorkDays>();
    for (LabourGrade lg : labourGrades) {
      EstimatedWorkPackageWorkDays ew = new EstimatedWorkPackageWorkDays();
      ew.setLabourGrade(lg);
      estimatedHours.add(ew);
    }
    selectedWeek = null;
    selectedYear = null;
    selectedDate = null;
    selectedWorkPackage = null;
    selectedWorkPackageCreation = null;
  }
  
  /**
   * Returns an EstimatedWorkPackageHours that has the given labourGradeId.
   * @param lgId The labourGrade's ID to be searched.
   * @return An EstimatedWorkPackageHours.
   */
  public EstimatedWorkPackageWorkDays getEstimateFromLabourGrade(int lgId) {
    if (estimatedHours != null) {
      for (EstimatedWorkPackageWorkDays ew : estimatedHours) {
        if (ew.getLabourGrade().getLabourGradeId() == lgId) {
          return ew;
        }
      }
    }

    return getNewEstimateWorkPackageFromLabourGrade(lgId);
  }
  
  /**
   * Returns a new EstimatedWorkPackageHours with the given labourGradeId.
   * @param lgId The labourGradeId to be used.
   * @return A new EstimatedWorkPackageHours.
   */
  public EstimatedWorkPackageWorkDays getNewEstimateWorkPackageFromLabourGrade(int lgId) {
    EstimatedWorkPackageWorkDays ew = new EstimatedWorkPackageWorkDays();
    
    LabourGrade lg = new LabourGrade();
    lg = lgm.find(lgId);
    ew.setLabourGrade(lg);
    estimatedHours.add(ew);
    return ew;
  }
  
  /**
   * Creates and persists a collection of EstimatedWorkPackageHours for the
   * selectedWorkPackage and date.
   * @return null, to reload the page.
   */
  public String createEstimate() {
    Date dd = weekNumberService.getDateFromWeekYear(selectedWeek, selectedYear);
    for (EstimatedWorkPackageWorkDays ew : estimatedHours) {
      ew.setDateCreated(dd);
      ew.setWorkpackage(selectedWorkPackageCreation);
      ewm.persist(ew);
    }
    wpm.merge(selectedWorkPackageCreation);
    
    reset();
    
    return null;
  }
  
  /**
   * Returns a List of EstimatedWorkPackageHours associated with
   * the selectedWorkPackage and selectedDate.
   * @return A List of EstimatedWorkPackageHours.
   */
  public List<EstimatedWorkPackageWorkDays> viewEstimate() {
    if (selectedWorkPackage != null && selectedDate != null) {
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      Calendar cal = Calendar.getInstance();
      try {
        cal.setTime(df.parse(selectedDate));
      } catch (ParseException ex) {
        ex.printStackTrace();
      }
      return ewm.find(selectedWorkPackage, cal.getTime());
    }
    return null;
  }
  
}
