package com.timelink.controllers;

import com.timelink.Session;
import com.timelink.TimesheetStatus;
import com.timelink.ejbs.Timesheet;
import com.timelink.ejbs.WorkPackage;
import com.timelink.managers.ProjectManager;
import com.timelink.managers.TimesheetManager;
import com.timelink.managers.WorkPackageManager;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
@Named("TimesheetController")
public class TimesheetController implements Serializable {
  private Timesheet selectedTimesheet;
  @Inject TimesheetManager tm;
  @Inject WorkPackageManager wpm;
  @Inject Session ses;
  @Inject ProjectManager pm;
  
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
    }
    selectedTimesheet = getSelectedTimesheet();
    return null;
  }
  
  //TODO make this gud
  /**
   * Sets the current selectedTimesheet's status to submitted
   * and saves it.
   * @return A null to reload the page.
   */
  public String submit() {
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
    return wpm.findAssigned(ses.getCurrentEmployee(), pm.find(projectNumber));
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
}
