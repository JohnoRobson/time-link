package com.timelink.controllers;

import com.timelink.ejbs.Timesheet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SuppressWarnings("serial")
@SessionScoped
@Named("ApproverController")
public class ApproverController implements Serializable {
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
    Timesheet[] apprTimesheets;
    //TODO Change below to a Timesheet query
    apprTimesheets = new Timesheet[10];
    timesheets = new ArrayList<Timesheet>();
    for (Timesheet t : apprTimesheets) {
      timesheets.add(t);
    }
  }
  
  public String save() {
    //TODO Iterate through timesheets and save values
    return null;
  }
  
  public String viewTimesheet(Timesheet timesheet) {
    viewingTimesheet = timesheet;
    return null;
  }
  
}
