package com.timelink.controllers;

import com.timelink.Session;
import com.timelink.ejbs.Timesheet;
import com.timelink.ejbs.TimesheetRow;
import com.timelink.managers.TimesheetManager;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
@Named("TimesheetController")
public class TimesheetController implements Serializable {
  private Timesheet timesheet;
  @Inject TimesheetManager tm;
  @Inject Session ses;
  
  public TimesheetController() {
    
  }
  
  /**
   * Returns timesheet.
   * @return the timesheet
   */
  public Timesheet getTimesheet() {
    if (timesheet == null) {
      timesheet = tm.findLatest(ses.getCurrentEmployee());
      
    }
    return timesheet;
  }

  /**
   * Sets timesheet to timesheet.
   * @param timesheet the timesheet to set
   */
  public void setTimesheet(Timesheet timesheet) {
    this.timesheet = timesheet;
  }
  
  //TODO make this gud
  public String save() {
    tm.merge(timesheet);
    return null;
  }
  
  //TODO make this gud
  public String submit() {
    timesheet.setStatus("gud");
    return null;
  }
  
  public String addRow() {
    ArrayList<TimesheetRow> ar = new ArrayList<TimesheetRow>(timesheet.getRows());
    ar.add(new TimesheetRow());
    timesheet.setRows(ar);
    save();
    return null;
  }
}
