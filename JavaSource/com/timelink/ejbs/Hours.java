package com.timelink.ejbs;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "ts_hour")
public class Hours implements Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "tsho_id")
  private int hourId;
  
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "tsh_id",
      referencedColumnName = "tsh_id")
  private Timesheet timesheet;
  
  @Column(name = "tsho_prjh_id")
  private int projectId;
  
  @Column(name = "tsho_wp_id")
  private int workPackageId;
  
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "tsl_id",
      referencedColumnName = "tsl_id")
  private TimesheetRow timesheetRow;
  
  @JoinColumn(name = "lg_id",
      referencedColumnName = "lg_id")
  @ManyToOne
  private LabourGrade labourGrade;
  
  @Column(name = "tsho_date")
  private Date date;
  
  @Column(name = "tsho_hour")
  private float hour;
  
  @Column(name = "tsho_labour_cost")
  private Float labourCost;

  /**
   * Returns the hourId.
   * @return the hourId
   */
  public final int getHourId() {
    return hourId;
  }

  /**
   * Sets the hourId to hourId.
   * @param hourId the hourId to set
   */
  public final void setHourId(int hourId) {
    this.hourId = hourId;
  }

  /**
   * Returns the timesheetId.
   * @return the timesheetId
   */
  public final Timesheet getTimesheet() {
    return timesheet;
  }

  /**
   * Sets the timesheetId to timesheetId.
   * @param timesheetId the timesheetId to set
   */
  public final void setTimesheet(Timesheet timesheet) {
    this.timesheet = timesheet;
  }

  /**
   * Returns the projectId.
   * @return the projectId
   */
  public final int getProjectId() {
    return projectId;
  }

  /**
   * Sets projectId to projectId.
   * @param projectId the projectId to set
   */
  public final void setProjectId(int projectId) {
    this.projectId = projectId;
  }

  /**
   * Returns the workPackageId.
   * @return the workPackageId
   */
  public final int getWorkPackageId() {
    return workPackageId;
  }

  /**
   * Sets the workpackageId to workpackageId.
   * @param workPackageId the workPackageId to set
   */
  public final void setWorkPackageId(int workPackageId) {
    this.workPackageId = workPackageId;
  }

  /**
   * Returns the timesheetRow.
   * @return the timesheetRow
   */
  public TimesheetRow getTimesheetRow() {
    return timesheetRow;
  }

  /**
   * Sets the timesheetRow to timesheetRow.
   * @param timesheetRow the timesheetRow to set
   */
  public void setTimesheetRow(TimesheetRow timesheetRow) {
    this.timesheetRow = timesheetRow;
  }

  /**
   * Returns the labourGrade.
   * @return the labourGrade
   */
  public LabourGrade getLabourGrade() {
    return labourGrade;
  }

  /**
   * Sets the labourGrade.
   * @param labourGrade the labourGrade to set
   */
  public void setLabourGrade(LabourGrade labourGrade) {
    this.labourGrade = labourGrade;
  }

  /**
   * Returns the date.
   * @return the date
   */
  public final Date getDate() {
    return date;
  }

  /**
   * Sets the date to date.
   * @param date the date to set
   */
  public final void setDate(Date date) {
    this.date = date;
  }

  /**
   * Returns the hour.
   * @return the hour
   */
  public final float getHour() {
    return hour;
  }

  /**
   * Sets the hour to hour.
   * @param hour the hour to set
   */
  public final void setHour(float hour) {
    this.hour = hour;
  }

  /**
   * Returns the labourCost.
   * @return the labourCost
   */
  public float getLabourCost() {
    return labourCost;
  }

  /**
   * Sets the labourCost to labourCost.
   * @param labourCost the labourCost to set
   */
  public void setLabourCost(float labourCost) {
    this.labourCost = labourCost;
  }
  
  
}
