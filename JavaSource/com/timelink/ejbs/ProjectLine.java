package com.timelink.ejbs;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "prj_line")
public class ProjectLine implements Serializable {
  
  @Id
  @Column(name = "prjl_id")
  private int projectLineId;
  
  @Column(name = "prjl_prjh_id")
  private int projectId;
  
  @Column(name = "prjl_emp_id")
  private int projectEmployeeId;
  
  /**
   * Returns the projectLineId.
   * @return the projectLineId
   */
  public int getProjectLineId() {
    return projectLineId;
  }
  
  /**
   * Sets the projectLineId to projectLineId.
   * @param projectLineId the projectLineId to set
   */
  public void setProjectLineId(int projectLineId) {
    this.projectLineId = projectLineId;
  }
  
  /**
   * Returns the projectId.
   * @return the projectId
   */
  public int getProjectId() {
    return projectId;
  }
  
  /**
   * Sets the projectLineId to projectLineId.
   * @param projectId the projectId to set
   */
  public void setProjectId(int projectId) {
    this.projectId = projectId;
  }
  
  /**
   * Returns the projectEmployeeId.
   * @return the projectEmployeeId
   */
  public int getProjectEmployeeId() {
    return projectEmployeeId;
  }
  
  /**
   * Sets the projectEmployeeId to projectEmployeeId.
   * @param projectEmployeeId the projectEmployeeId to set
   */
  public void setProjectEmployeeId(int projectEmployeeId) {
    this.projectEmployeeId = projectEmployeeId;
  }
  
}
