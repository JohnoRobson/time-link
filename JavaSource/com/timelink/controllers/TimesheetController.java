package com.timelink.controllers;

import com.timelink.Session;
import com.timelink.ejbs.Project;
import com.timelink.ejbs.Timesheet;
import com.timelink.ejbs.TimesheetRow;
import com.timelink.ejbs.WorkPackage;
import com.timelink.enums.TimesheetStatus;
import com.timelink.managers.ProjectManager;
import com.timelink.managers.TimesheetManager;
import com.timelink.managers.WorkPackageManager;
import com.timelink.services.HRProjectService;
import com.timelink.services.WeekNumberService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("serial")
@SessionScoped
@Named("TimesheetController")
public class TimesheetController implements Serializable {
  private Timesheet selectedTimesheet;
  @Inject TimesheetManager tm;
  @Inject WorkPackageManager wpm;
  @Inject Session ses;
  @Inject ProjectManager pm;
  @Inject WeekNumberService weekNumberService;
  @Inject HRProjectService hrps;
  
  //ADD TIMESHEET MODAL STUFF
  private int week;
  private int year;

  public TimesheetController() {
    
  }
  
  /**
   * Adds a row to the current selectedTimesheet.
   * @return null, to reload the page.
   */
  public String addRow() {
    selectedTimesheet.addRow();
    return null;
  }
  
  //TODO make this work on a weekly, rather than a daily basis.
  /**
   * Adds a new timesheet for the logged in user.
   * If there is already a timesheet that matches the current day,
   * one will not be created.
   * @return Null, so that the page can be reloaded.
   */
  public String addTimesheet() {
    if (selectedTimesheet != null && selectedTimesheet.getDate().toString()
        .equals(new Date(Calendar.getInstance().getTime().getTime()).toString())) {
      return null;
    }
    save();
    selectedTimesheet = new Timesheet(ses.getCurrentEmployee());
    selectedTimesheet.setDate(weekNumberService.getDateFromWeekYear(week, year));
    tm.persist(selectedTimesheet);
    //Update the selectedTimesheet PK so that it can be added to it's rows and hours.
    selectedTimesheet = tm.findLatest(ses.getCurrentEmployee());
    return null;
  }
  
  public void deleteRow(TimesheetRow row) {
    selectedTimesheet.deleteRow(row);
    save();
  }

  public List<Project> getAssignedProjects() {
    List<Project> list = new ArrayList<Project>();
    list.addAll(ses.getCurrentEmployee().getProjects());
    list.add(hrps.getHRProject());
    return list;
  }
  
  /**
   * Returns WorkPackages in the given project that the current employee is
   * assigned to.
   * @param projectNumber The projectId to be searched
   * @return A List of WorkPackages that the current employee is assigned to
   *     that are in the given project.
   */
  public List<WorkPackage> getAssignedWorkPackages(Integer projectNumber) {
    ArrayList<WorkPackage> newList = null;
    Project pro = pm.find(projectNumber);
    if (pro != null) {
      List<WorkPackage> list = wpm.findAssigned(ses.getCurrentEmployee(), pro);
      newList = new ArrayList<WorkPackage>();
      for (WorkPackage wp : list) {
        if (wpm.isLeaf(wp)) {
          newList.add(wp);
        }
      }
      newList.add(hrps.getFlextimeWorkPackage());
      newList.add(hrps.getSickDayWorkPackage());
      newList.add(hrps.getVacationWorkPackage());
    }
    return newList;
  }
  
  public WorkPackage getFlextimeWorkPackage() {
    return hrps.getFlextimeWorkPackage();
  }
  
  public Project getHRCodes() {
    return pm.findHRCodes();
  }
  
//  public WorkPackage getSickWorkPackage() {
//    return wpm.findSickLeave();
//  }
//  
//  public WorkPackage getStatWorkPackage() {
//    return wpm.findStatHoliday();
//  }
//  
//  public WorkPackage getLongTermDisabilityWorkPackage() {
//    return wpm.findLongDisability();
//  }
//  
//  public WorkPackage getShortTermDisabilityWorkPackage() {
//    return wpm.findShortDisability();
//  }
//  
//  public WorkPackage getFlexTimePackage() {
//    return wpm.findFlexTime();
//  }
//  
//  public WorkPackage getVacationWorkPackage() {
//    return wpm.findVacation();
//  }
  
  public Project getHRProject() {
    return hrps.getHRProject();
  }

  /**
   * Returns selectedTimesheet.  If there isn't a timesheet for the current employee
   * one will be created.
   * @return the selectedTimesheet
   */
  public Timesheet getSelectedTimesheet() {
    if (selectedTimesheet == null) {
      selectedTimesheet = null;
    }
    return selectedTimesheet;
  }

  /**
   * Return the selectedTimesheetId.
   * @return the selectedTimesheetId
   */
  public Integer getSelectedTimesheetId() {
    if (selectedTimesheet == null) {
      return null;
    }
    return selectedTimesheet.getTimesheetId();
  }
  
  public WorkPackage getSickWorkPackage() {
    return hrps.getSickDayWorkPackage();
  }

  public List<Timesheet> getTimesheets() {
    return tm.findByEmployee(ses.getCurrentEmployee());
  }

  public WorkPackage getVacationWorkPackage() {
    return hrps.getVacationWorkPackage();
  }
  
  //ADD TIMESHEET MODAL
  /**
   * Returns week.
   * @return the week
   */
  public int getWeek() {
    return week;
  }
  
  public int getWeekNumber(Timesheet ts) {
    return weekNumberService.getWeekNumber(ts.getDate());
  }
  
  /**
   * Returns week.
   * @return the year
   */
  public int getYear() {
    return year;
  }
  
  public void refresh() {
    selectedTimesheet = null;
    getSelectedTimesheet();
  }
  
  //TODO make this gud
  /**
   * Saves the current selectedTimesheet and reloads it from the database.
   * @return A null to reload the page.
   */
  public String save() {
    if (selectedTimesheet != null) {
      tm.merge(selectedTimesheet);
      selectedTimesheet = tm.find(selectedTimesheet.getTimesheetId());
    }
    
    //selectedTimesheet = getSelectedTimesheet();
    return null;
  }

  /**
   * Sets selectedTimesheet to selectedTimesheet.
   * @param selectedTimesheet the selectedTimesheet to set
   */
  public void setSelectedTimesheet(Timesheet selectedTimesheet) {
    this.selectedTimesheet = selectedTimesheet;
  }
  
  /**
   * Set the selectedTimesheetId to selectedTimesheetId.
   * @param selectedTimesheetId the selectedTimesheetId to set
   */
  public void setSelectedTimesheetId(Integer selectedTimesheetId) {
    if (selectedTimesheetId == null) {
      return;
    }
    
    if (this.selectedTimesheet != null
        && this.selectedTimesheet.getTimesheetId() == selectedTimesheetId) {
      return;
    }
    
    Timesheet ts = tm.find(selectedTimesheetId);
    tm.detach(ts);
    this.selectedTimesheet = tm.find(selectedTimesheetId);
  }
  
  /**
   * Sets week to week.
   * @param week the week to set
   */
  public void setWeek(int week) {
    this.week = week;
  }
  
  /**
   * Sets week to week.
   * @param year the year to set
   */
  public void setYear(int year) {
    this.year = year;
  }
  
  /**
   * Sets the current selectedTimesheet's status to submitted
   * and saves it.  Also changes the employee's flex hours.
   * @return A null to reload the page.
   */
  public String submit() {
    if (selectedTimesheet.getStatus().equals(TimesheetStatus.NOTSUBMITTED.toString())) {
      if (selectedTimesheet.isValid()) {
        selectedTimesheet.setStatus("" + TimesheetStatus.WAITINGFORAPPROVAL.ordinal());
      } else {
        //TODO set to display an error message explaining validation failure
      }
    }
    save();
    return null;
  }
  
}
