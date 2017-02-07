package com.timelink.ejbs;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "wp_line")
public class WorkPackageLine implements Serializable {
  
  @Id
  @Column(name = "wpl_id")
  private int workPackageId;
  
  @Column(name = "wpl_wph_id")
  private int workPackageHeaderId;
  
  @Column(name = "wpl_emp_id")
  private int workPackageEmployeeId;

  /**
   * Returns the workPackageId.
   * @return the workPackageId
   */
  public int getWorkPackageId() {
    return workPackageId;
  }

  /**
   * Sets the workPackageId to workPackageId.
   * @param workPackageId the workPackageId to set
   */
  public void setWorkPackageId(int workPackageId) {
    this.workPackageId = workPackageId;
  }

  /**
   * Returns the workPackageHeaderId.
   * @return the workPackageHeaderId
   */
  public int getWorkPackageHeaderId() {
    return workPackageHeaderId;
  }

  /**
   * Sets the workPackageHeaderId to workPackageHeaderId.
   * @param workPackageHeaderId the workPackageHeaderId to set
   */
  public void setWorkPackageHeaderId(int workPackageHeaderId) {
    this.workPackageHeaderId = workPackageHeaderId;
  }

  /**
   * Returns the workPacakgeEmployeeId.
   * @return the workPackageEmployeeId
   */
  public int getWorkPackageEmployeeId() {
    return workPackageEmployeeId;
  }

  /**
   * Sets the workPacakgeEmployeeId to workPackageEmployeeId.
   * @param workPackageEmployeeId the workPackageEmployeeId to set
   */
  public void setWorkPackageEmployeeId(int workPackageEmployeeId) {
    this.workPackageEmployeeId = workPackageEmployeeId;
  }
  
  
}
