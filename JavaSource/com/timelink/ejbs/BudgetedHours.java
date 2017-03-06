package com.timelink.ejbs;

import java.io.Serializable;

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
@Table(name = "Budget_Hour")
public class BudgetedHours implements Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "bh_id")
  private int budgetHoursId;
  
  @JoinColumn(name = "bh_level",
      referencedColumnName = "lg_id")
  @ManyToOne
  private LabourGrade labourGrade;
  
  @JoinColumn(name = "bh_wp_id",
      referencedColumnName = "wp_id")
  @ManyToOne(cascade = CascadeType.ALL)
  private WorkPackage workPackage;
  
  @Column(name = "bh_man_day")
  private int manDay;

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
   * Sets he workPackage to workPackage.
   * @param workPackage the workPackage to set
   */
  public void setWorkPackageLineId(WorkPackage workPackage) {
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
    System.out.println("SET MAN DAY TO " + manDay.intValue());
    this.manDay = manDay;
  }
  
}
