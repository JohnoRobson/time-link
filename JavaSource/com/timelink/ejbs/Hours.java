package com.timelink.ejbs;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Id;

@SuppressWarnings("serial")
//@Entity
//@IdClass(HoursId.class)
//@Table(name = "Hours")
public class Hours implements Serializable {
  
  @Id
  private Date date;
  
  @Id
  private Project project;
  
  @Id
  private WorkPackage workPackage;
  private BigDecimal hours;
  
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
  
  /**
   * Returns hours.
   * @return the hours
   */
  public BigDecimal getHours() {
    return hours;
  }
  
  /**
   * Sets hours to hours.
   * @param hours the hours to set
   */
  public void setHours(BigDecimal hours) {
    this.hours = hours;
  }
}
