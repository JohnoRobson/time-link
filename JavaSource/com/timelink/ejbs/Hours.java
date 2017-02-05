package com.timelink.ejbs;

import java.math.BigDecimal;
import java.sql.Date;

public class Hours {
  private Date date;
  private Project project;
  private WorkPackage workPackage;
  private BigDecimal hours;
  
  /**
   * @return the date
   */
  public Date getDate() {
    return date;
  }
  
  /**
   * @param date the date to set
   */
  public void setDate(Date date) {
    this.date = date;
  }
  
  /**
   * @return the project
   */
  public Project getProject() {
    return project;
  }
  
  /**
   * @param project the project to set
   */
  public void setProject(Project project) {
    this.project = project;
  }
  
  /**
   * @return the workPackage
   */
  public WorkPackage getWorkPackage() {
    return workPackage;
  }
  
  /**
   * @param workPackage the workPackage to set
   */
  public void setWorkPackage(WorkPackage workPackage) {
    this.workPackage = workPackage;
  }
  
  /**
   * @return the hours
   */
  public BigDecimal getHours() {
    return hours;
  }
  
  /**
   * @param hours the hours to set
   */
  public void setHours(BigDecimal hours) {
    this.hours = hours;
  }
}
