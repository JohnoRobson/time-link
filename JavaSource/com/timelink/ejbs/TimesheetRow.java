package com.timelink.ejbs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.Dependent;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Dependent
@Table(name = "ts_line")
public class TimesheetRow {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "tsl_id")
  private int timesheetRowId;
  
  @Transient
  private Integer projectId = 0;
  
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "tsl_tsh_id",
      referencedColumnName = "tsh_id")
  private Timesheet timesheet;
  
  @Transient
  private Integer workPackageId = 0;
  
  @OneToMany(fetch = FetchType.EAGER,
      cascade = CascadeType.ALL)
  @JoinColumn(name = "tsl_id",
      referencedColumnName = "tsl_id")
  @OrderBy("hourId ASC")
  private Set<Hours> hours;
  
  @Column(name = "tsl_note")
  private String note;
  
  //TODO Fix this stub.
  public boolean validate() {
    return true;
  }
  
  /**
   * The constructor for TimesheetRow.
   */
  public TimesheetRow() {
    hours = new HashSet<Hours>();
    for (int i = 0; i < 7; ++i) {
      Hours hrs = new Hours();
      hrs.setTimesheetRow(this);
      if (timesheet != null) {
        hrs.setTimesheetId(timesheet.getTimesheetId());
      }
      hours.add(hrs);
    }
  }
  
  /**
   * The constructor for TimesheetRow used when added a new timesheet row
   * to a timesheet.
   */
  public TimesheetRow(Timesheet ts) {
    timesheet = ts;
    hours = new HashSet<Hours>();
    for (int i = 0; i < 7; ++i) {
      Hours hrs = new Hours();
      hrs.setTimesheetRow(this);
      hrs.setTimesheetId(ts.getTimesheetId());
      hours.add(hrs);
    }
  }
  
  /**
   * Returns the projectId.
   * @return the projectId.
   */
  public Integer getProjectId() {
    if (projectId == 0 && getHours().size() > 0) {
      projectId = getHours().get(0).getProjectId();
    }
    return projectId;
  }
  
  /**
   * Sets projectId to projectId.
   * Also sets the projectId in the hours to projectId.
   * @param projId The projectId to be set.
   */
  public void setProjectId(Integer projId) {
    projectId = projId;
    for (Hours hrs : hours) {
      hrs.setProjectId(projectId);
    }
  }
  
  /**
   * Returns the workpackageId, or initializes the workPackageId
   * to the value of the hours stored in this TimesheetRow.
   */
  public Integer getWorkPackageId() {
    if (workPackageId == 0 && getHours().size() > 0) {
      workPackageId = getHours().get(0).getWorkPackageId();
    }
    return workPackageId;
  }
  
  /**
   * Sets the workPackageId to wpId.
   * Also sets the workPackageId in the hours to workPackageId.
   * @param wpId The workPackageId to be set.
   */
  public void setWorkPackageId(Integer wpId) {
    workPackageId = wpId;
    for (Hours hrs : hours) {
      hrs.setWorkPackageId(workPackageId);
    }
  }
  
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
   * Returns hours.
   * @return the hours
   */
  public List<Hours> getHours() {
    if (hours != null) {
      return new ArrayList<Hours>(hours);
    }
    return new ArrayList<Hours>();
  }
  
  /**
   * Sets hours to hours.
   * @param hours the hours to set
   */
  public void setHours(List<Hours> hours) {
    this.hours = new HashSet<Hours>(hours);
  }
  
  public String getNote() {
    return note;
  }
  
  public void setNote(String note) {
    this.note = note;
  }
  
}
