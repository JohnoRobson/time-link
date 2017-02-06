package com.timelink.ejbs;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "ts_hour")
public class Hours implements Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "tsho_id")
  private int hourId;
  
  @Column(name = "tsh_id")
  private int timesheetId;
  
  @Column(name = "tsho_prjh_id")
  private int projectId;
  
  @Column(name = "tsho_wp_id")
  private int workPackageId;
  
  @Column(name = "tsl_id")
  private int timesheetLineId;
  
  @Column(name = "tsho_date")
  private Date date;
  
  @Column(name = "tsho_hour")
  private float hour;

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
  public final int getTimesheetId() {
    return timesheetId;
  }

  /**
   * Sets the timesheetId to timesheetId.
   * @param timesheetId the timesheetId to set
   */
  public final void setTimesheetId(int timesheetId) {
    this.timesheetId = timesheetId;
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
   * Returns the timesheetLineId.
   * @return the timesheetLineId
   */
  public final int getTimesheetLineId() {
    return timesheetLineId;
  }

  /**
   * Sets the timesheetLineId to timesheetLineId.
   * @param timesheetLineId the timesheetLineId to set
   */
  public final void setTimesheetLineId(int timesheetLineId) {
    this.timesheetLineId = timesheetLineId;
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
  
  
}
