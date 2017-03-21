package com.timelink.ejbs;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "estimate_wp_hour")
public class EstimatedWorkPackageHours implements Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ewh_id")
  private int estimatedHoursId;
  
  @JoinColumn(name = "ewh_level",
      referencedColumnName = "lg_id")
  @ManyToOne
  private LabourGrade labourGrade;
  
  @JoinColumn(name = "ewh_wp_id",
      referencedColumnName = "wp_id")
  @ManyToOne(cascade = CascadeType.MERGE)
  private WorkPackage workpackage;
  
  @Column(name = "ewh_man_day")
  private int manDay;
  
  @Column(name = "ewh_date_created")
  @Temporal(TemporalType.DATE)
  private Date dateCreated;

  /**
   * Returns the estimatedHoursId.
   * @return the estimatedHoursId
   */
  public int getEstimatedHoursId() {
    return estimatedHoursId;
  }

  /**
   * Sets the estimatedHoursId.
   * @param estimatedHoursId the estimatedHoursId to set
   */
  public void setEstimatedHoursId(int estimatedHoursId) {
    this.estimatedHoursId = estimatedHoursId;
  }

  /**
   * Returns the labourGrade.
   * @return the labourGrade
   */
  public LabourGrade getLabourGrade() {
    return labourGrade;
  }

  /**
   * Sets the labourGrade to labourGrade.
   * @param labourGrade the labourGrade to set
   */
  public void setLabourGrade(LabourGrade labourGrade) {
    this.labourGrade = labourGrade;
  }

  /**
   * Returns the workpackage.
   * @return the workpackage
   */
  public WorkPackage getWorkpackage() {
    return workpackage;
  }
  
  /**
   * Sets the workpackage to workpackage.
   * @param workpackage the workpackage to set
   */
  public void setWorkpackage(WorkPackage workpackage) {
    this.workpackage = workpackage;
  }
  
  /**
   * Sets the workpackage to workpackage.
   * @param workpackage the workpackage to set
   */
  public void setWorkPackageLineId(WorkPackage workpackage) {
    if (workpackage != null && !workpackage.getEstimatedHours().contains(this)) {
      List<EstimatedWorkPackageHours> wp = workpackage.getEstimatedHours();
      wp.add(this);
      workpackage.setEstimatedHours(wp);
    }
    this.workpackage = workpackage;
  }

  /**
   * Returns the manDay.
   * @return the manDay
   */
  public Integer getManDay() {
    return manDay;
  }

  /**
   * Sets the manDay to manDay.
   * @param manDay the manDay to set
   */
  public void setManDay(Integer manDay) {
    this.manDay = manDay;
  }

  /**
   * Returns the dateCreated.
   * @return the dateCreated
   */
  public Date getDateCreated() {
    return dateCreated;
  }

  /**
   * Sets the dateCreated to dateCreated.
   * @param dateCreated the dateCreated to set
   */
  public void setDateCreated(Date dateCreated) {
    this.dateCreated = dateCreated;
  }
  
  /**
   * Returns true if the entered object is the same as this object.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    
    if (!(obj instanceof EstimatedWorkPackageHours)) {
      return false;
    }
    
    EstimatedWorkPackageHours  emp = (EstimatedWorkPackageHours) obj;
    
    if (emp.getEstimatedHoursId() != getEstimatedHoursId()) {
      return false;
    }
    
    return true;
  }
  
}
