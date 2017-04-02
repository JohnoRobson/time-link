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
@Table(name = "budget_wp_work_day")
public class BudgetedWorkPackageWorkDays implements Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "bwd_id")
  private int budgetedWorkPackageHoursId;
  
  @JoinColumn(name = "bwd_level",
      referencedColumnName = "lg_id")
  @ManyToOne
  private LabourGrade labourGrade;
  
  @JoinColumn(name = "bwd_wp_id",
      referencedColumnName = "wp_id")
  @ManyToOne(cascade = CascadeType.MERGE)
  private WorkPackage workPackage;
  
  @Column(name = "bwd_man_day")
  private int manDay;
  
  @Column(name = "bwd_date_created")
  private Date dateCreated;

  /**
   * Returns the budgetedWorkPackageHoursId.
   * @return the budgetedWorkPackageHoursId
   */
  public int getBudgetedWorkPackageHoursId() {
    return budgetedWorkPackageHoursId;
  }

  /**
   * Sets the budgetedWorkPackageHoursId.
   * @param budgetedWorkPackageHoursId the budgetedWorkPackageHoursId to set
   */
  public void setBudgetedWorkPackageHoursId(int budgetedWorkPackageHoursId) {
    this.budgetedWorkPackageHoursId = budgetedWorkPackageHoursId;
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
      List<BudgetedWorkPackageWorkDays> wp = workPackage.getPlannedHours();
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
