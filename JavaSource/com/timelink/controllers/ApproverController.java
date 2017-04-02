package com.timelink.controllers;

import com.timelink.Session;
import com.timelink.ejbs.Timesheet;
import com.timelink.ejbs.WorkPackage;
import com.timelink.enums.TimesheetStatus;
import com.timelink.managers.TimesheetManager;
import com.timelink.managers.WorkPackageManager;
import com.timelink.services.FlextimeService;
import com.timelink.services.VacationService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("serial")
@SessionScoped
@Named("ApproverController")
public class ApproverController implements Serializable {
  @Inject TimesheetManager tm;
  @Inject WorkPackageManager wpm;
  @Inject Session ses;
  @Inject transient FlextimeService flextimeService;
  @Inject transient VacationService vacationService;
  private Set<Timesheet> timesheets;
  private Set<Timesheet> selectedTimesheets;
  private Timesheet viewingTimesheet;

  /**
   * Returns the timesheets.
   * @return the timesheets
   */
  public List<Timesheet> getTimesheets() {
    if (timesheets != null) {
      return new ArrayList<Timesheet>();
    }
    return new ArrayList<Timesheet>(timesheets);
  }

  /**
   * Sets the timesheets to timesheets.
   * @param timesheets the timesheets to set
   */
  public void setTimesheets(List<Timesheet> timesheets) {
    this.timesheets = new HashSet<Timesheet>(timesheets);
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
    return new ArrayList<Timesheet>(timesheets);
  }
  
  /**
   * Returns the selectedTimesheets.
   * @return the selectedTimesheets
   */
  public List<Timesheet> getSelectedTimesheets() {
    if (selectedTimesheets != null) {
      return new ArrayList<Timesheet>(selectedTimesheets);
    }
    return new ArrayList<Timesheet>();
  }

  /**
   * Sets the selectedTimesheets to the selectedTimesheets.
   * @param selectedTimesheets the selectedTimesheets to set
   */
  public void setSelectedTimesheets(List<Timesheet> selectedTimesheets) {
    this.selectedTimesheets = new HashSet<Timesheet>(selectedTimesheets);
  }

  /**
   * Refresh the list of timesheets to be reviewed.
   */
  public void refreshList() {
    List<Timesheet> apprTimesheets;
    //TODO Change below to a Timesheet query
    apprTimesheets = tm.findByApprover(ses.getCurrentEmployee().getEmployeeId());
    timesheets = new HashSet<Timesheet>();
    for (Timesheet t : apprTimesheets) {
      if (!t.getStatus().equals("" + TimesheetStatus.NOTSUBMITTED)) {
        timesheets.add(t);
      }
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
  
  /**
   * Approves all timesheets in the selectedTimesheets List.
   * @return null to reload the page.
   */
  public String approve() {
    for (Timesheet t : selectedTimesheets) {
      //flextimeService.applyFlextime(t);
      t.setStatus("" + TimesheetStatus.APPROVED.ordinal());
      chargeWorkPackages(t);
      tm.merge(t);
    }
    return null;
  }
  
  /**
   * Declines a selected timesheet.
   */
  public void declineValidate(ActionEvent actionEvent) {
    if (selectedTimesheets.size() > 1) {
      FacesContext context = FacesContext.getCurrentInstance();
      context.addMessage(null,
          new FacesMessage("Cannot decline more than one timesheet at a time."));
      return;
    } else if (selectedTimesheets.size() == 0) {
      FacesContext context = FacesContext.getCurrentInstance();
      context.addMessage(null,
          new FacesMessage("Must have a timesheet selected."));
      return;
    } else {
      if (getSingleTimesheet().getStatus().equals(TimesheetStatus.APPROVED.name())) {
        //If the one selected timesheet is already approved.
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null,
            new FacesMessage("Cannot reject an already approved timesheet."));
        return;
      }
    }
    
    return;
  }
  
  public boolean timesheetIsApproved() {
    return getSingleTimesheet().getStatus().equals(TimesheetStatus.APPROVED.name());
  }
  
  /**
   * Declines a timesheet.
   * @return null to reload the page
   */
  public String declineSave() {
    for (Timesheet t : selectedTimesheets) {
//      flextimeService.revertFlextime(t);
//      vacationService.revertVacation(t);
      t.setStatus("" + TimesheetStatus.REJECTED.ordinal());
      tm.merge(t);
    }
    return null;
  }
  
  /**
   * Returns one timesheet.
   * @return A single timesheet.
   */
  public Timesheet getSingleTimesheet() {
    if (selectedTimesheets != null && selectedTimesheets.size() == 1) {
      return selectedTimesheets.iterator().next();
    } else {
      return new Timesheet();
    }
  }
  
  /**
   * Sets all work packages in the approved timesheet to charged.
   * @param ts The timesheet to be approved.
   */
  public void chargeWorkPackages(Timesheet ts) {
    for (WorkPackage wp : wpm.getAllInTimesheet(ts)) {
      wp.setCharged(true);
      wpm.merge(wp);
    }
  }
}
