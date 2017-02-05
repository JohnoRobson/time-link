package com.timelink.ejbs;

import java.io.Serializable;
import java.sql.Date;


@SuppressWarnings("serial")
/**
 * Required so that Hours can have a composite primary key.
 * @author Johno
 */
public class HoursId implements Serializable {
  
  private Date date;
  private Project project;
  private WorkPackage workPackage;
  
  /**
   * Returns date.
   * @return the date
   */
  public Date getDate() {
    return date;
  }
  
  /**
   * Sets the date to date.
   * @param date the date to set
   */
  public void setDate(Date date) {
    this.date = date;
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
  
  
}
