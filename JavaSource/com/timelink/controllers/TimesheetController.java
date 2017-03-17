package com.timelink.controllers;

import com.timelink.Session;
import com.timelink.ejbs.Timesheet;
import com.timelink.ejbs.TimesheetRow;
import com.timelink.ejbs.WorkPackage;
import com.timelink.enums.TimesheetStatus;
import com.timelink.managers.ProjectManager;
import com.timelink.managers.TimesheetManager;
import com.timelink.managers.WorkPackageManager;
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
  
  //ADD TIMESHEET MODAL STUFF
  private int week;
  private int year;
  
  public TimesheetController() {
    
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
   * Sets selectedTimesheet to selectedTimesheet.
   * @param selectedTimesheet the selectedTimesheet to set
   */
  public void setSelectedTimesheet(Timesheet selectedTimesheet) {
    this.selectedTimesheet = selectedTimesheet;
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
   * Sets the current selectedTimesheet's status to submitted
   * and saves it.  Also changes the employee's flex hours.
   * @return A null to reload the page.
   */
  public String submit() {
    if (selectedTimesheet.getStatus().equals(TimesheetStatus.NOTSUBMITTED.toString())) {
      selectedTimesheet.calculateFlexAndOvertime();
    }
    selectedTimesheet.setStatus("" + TimesheetStatus.WAITINGFORAPPROVAL.ordinal());
    save();
    return null;
  }
  
  public void refresh() {
    selectedTimesheet = null;
    getSelectedTimesheet();
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
  
  /**
   * Adds a row to the current selectedTimesheet.
   * @return null, to reload the page.
   */
  public String addRow() {
    selectedTimesheet.addRow();
    return null;
  }
  
  /**
   * Returns WorkPackages in the given project that the current employee is
   * assigned to.
   * @param projectNumber The projectId to be searched
   * @return A List of WorkPackages that the current employee is assigned to
   *     that are in the given project.
   */
  public List<WorkPackage> getAssignedWorkPackages(int projectNumber) {
    List<WorkPackage> list = wpm.findAssigned(ses.getCurrentEmployee(), pm.find(projectNumber));
    ArrayList<WorkPackage> newList = new ArrayList<WorkPackage>();
    for (WorkPackage wp : list) {
      if (wpm.isLeaf(wp)) {
        newList.add(wp);
      }
    }
    return newList;
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
  
  public List<Timesheet> getTimesheets() {
    return tm.findByEmployee(ses.getCurrentEmployee());
  }
  
  public void deleteRow(TimesheetRow row) {
    selectedTimesheet.deleteRow(row);
    save();
  }
  
  public WorkPackage getSickWorkPackage() {
    return wpm.findSickDay();
  }
  
  //ADD TIMESHEET MODAL
  /**
   * Returns week.
   * @return the week
   */
  public int getWeek() {
    return week;
  }

  /**
   * Sets week to week.
   * @param week the week to set
   */
  public void setWeek(int week) {
    this.week = week;
  }

  /**
   * Returns week.
   * @return the year
   */
  public int getYear() {
    return year;
  }

  /**
   * Sets week to week.
   * @param year the year to set
   */
  public void setYear(int year) {
    this.year = year;
  }
  
  public int getWeekNumber(Timesheet ts) {
    return weekNumberService.getWeekNumber(ts.getDate());
  }
}
