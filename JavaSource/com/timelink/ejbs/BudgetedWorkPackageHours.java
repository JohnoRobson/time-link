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

@SuppressWarnings("serial")
@Entity
@Table(name = "Budget_Wp_Hour")
public class BudgetedWorkPackageHours implements Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "bwh_id")
  private int budgetHoursId;
  
  @JoinColumn(name = "bwh_level",
      referencedColumnName = "lg_id")
  @ManyToOne
  private LabourGrade labourGrade;
  
  @JoinColumn(name = "bwh_wp_id",
      referencedColumnName = "wp_id")
  @ManyToOne(cascade = CascadeType.MERGE)
  private WorkPackage workPackage;
  
  @Column(name = "bwh_man_day")
  private int manDay;
  
  @Column(name = "bwh_date_created")
  private Date dateCreated;

  /**
   * Returns the budgetHoursId.
   * @return the budgetHoursId
   */
  public int getBudgetedHoursId() {
    return budgetHoursId;
  }

  /**
   * Sets the budgetHoursId.
   * @param budgetHoursId the budgetHoursId to set
   */
  public void setPlannedHoursId(int budgetHoursId) {
    this.budgetHoursId = budgetHoursId;
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
   * Returns the workPackage.
   * @return the workPackage
   */
  public WorkPackage getWorkPackage() {
    return workPackage;
  }

  /**
   * Sets the workPackage to workPackage.
   * @param workPackage the workPackage to set
   */
  public void setWorkPackageLineId(WorkPackage workPackage) {
    if (workPackage != null && !workPackage.getPlannedHours().contains(this)) {
      List<BudgetedWorkPackageHours> wp = workPackage.getPlannedHours();
      wp.add(this);
      workPackage.setPlannedHours(wp);
    }
    this.workPackage = workPackage;
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
   * Sets the dateCreated.
   * @param dateCreated the dateCreated to set
   */
  public void setDateCreated(Date dateCreated) {
    this.dateCreated = dateCreated;
  }
  
  
  
}
