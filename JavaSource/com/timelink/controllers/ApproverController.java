package com.timelink.controllers;

import com.timelink.Session;
import com.timelink.ejbs.Timesheet;
import com.timelink.managers.TimesheetManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("serial")
@SessionScoped
@Named("ApproverController")
public class ApproverController implements Serializable {
  @Inject TimesheetManager tm;
  @Inject Session ses;
  private List<Timesheet> timesheets;
  private Timesheet viewingTimesheet;

  /**
   * Returns the timesheets.
   * @return the timesheets
   */
  public List<Timesheet> getTimesheets() {
    return timesheets;
  }

  /**
   * Sets the timesheets to timesheets.
   * @param timesheets the timesheets to set
   */
  public void setTimesheets(List<Timesheet> timesheets) {
    this.timesheets = timesheets;
  }
  
  /**
   * Returns the viewing timesheet.
   * @return the viewing timesheet
   */
  public Timesheet getViewingTimesheet() {
    return viewingTimesheet;
  }

  /**
   * Sets the viewing timesheet.
   * @param viewingTimesheet the viewingTimesheet to set
   */
  public void setViewingTimesheet(Timesheet viewingTimesheet) {
    this.viewingTimesheet = viewingTimesheet;
  }

  /**
   * Get list of timesheets to be approved.
   * @return the list of timesheets
   */
  public List<Timesheet> getList() {
    refreshList();
    return timesheets;
  }
  
  /**
   * Refresh the list of timesheets to be reviewed.
   */
  public void refreshList() {
    List<Timesheet> apprTimesheets;
    //TODO Change below to a Timesheet query
    apprTimesheets = tm.findByApprover(ses.getCurrentEmployee().getEmployeeId());
    timesheets = new ArrayList<Timesheet>();
    for (Timesheet t : apprTimesheets) {
      timesheets.add(t);
    }
  }
  
  /**
   * Save the new status of the timesheets.
   * @return null to reload page
   */
  public String save() {
    for (Timesheet t: timesheets) {
      tm.merge(t);
    }
    return null;
  }
  
  public String viewTimesheet(Timesheet timesheet) {
    viewingTimesheet = timesheet;
    return null;
  }
  
}
