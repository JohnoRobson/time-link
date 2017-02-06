package com.timelink.ejbs;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "ts_line")
public class TimesheetRow {
  
  @Id
  @Column(name = "tsl_id")
  private int timesheetRowId;
  
  @Transient
  private Project project;
  
  @ManyToOne
  @JoinColumn(name = "tsl_tsh_id",
      referencedColumnName = "tsh_id")
  private Timesheet timesheet;
  
  @Transient
  private WorkPackage workPackage;
  
  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "tsl_id",
      referencedColumnName = "tsl_id")
  private List<Hours> hours;
  
  @Column(name = "tsl_note")
  private String note;
  
  /**
   * Returns timesheetRowId.
   * @return the timesheetRowId
   */
  public int getTimesheetRowId() {
    return timesheetRowId;
  }
  
  /**
   * Set timesheetRowId to timesheetRowId.
   * @param timesheetRowId the timesheetRowId to set
   */
  public void setTimesheetRowId(int timesheetRowId) {
    this.timesheetRowId = timesheetRowId;
  }
  
  /**
   * Returns project.
   * @return the project
   */
  public Project getProject() {
    return project;
  }
  
  /**
   * Sets project to project.
   * @param project the project to set
   */
  public void setProject(Project project) {
    this.project = project;
  }
  
  /**
   * Returns timesheet.
   * @return the timesheet
   */
  public Timesheet getTimesheet() {
    return timesheet;
  }
  
  /**
   * Sets timesheet to timesheet.
   * @param timesheet the timesheet to set
   */
  public void setTimesheet(Timesheet timesheet) {
    this.timesheet = timesheet;
  }
  
  /**
   * Returns workpackage.
   * @return the workPackage
   */
  public WorkPackage getWorkPackage() {
    return workPackage;
  }
  
  /**
   * Sets workpackage to workpackage.
   * @param workPackage the workPackage to set
   */
  public void setWorkPackage(WorkPackage workPackage) {
    this.workPackage = workPackage;
  }
  
  /**
   * Returns hours.
   * @return the hours
   */
  public List<Hours> getHours() {
    return hours;
  }
  
  /**
   * Sets hours to hours.
   * @param hours the hours to set
   */
  public void setHours(List<Hours> hours) {
    this.hours = hours;
  }
  
  public String getNote() {
    return note;
  }
  
  public void setNote(String note) {
    this.note = note;
  }
  
}
