package com.timelink.ejbs;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "Budget_Project_Hour")
public class BudgetedProjectHours implements Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "bph_id")
  private int budgetHoursId;
  
  @JoinColumn(name = "bph_level",
      referencedColumnName = "lg_id")
  @ManyToOne
  private LabourGrade labourGrade;
  
  @JoinColumn(name = "bph_prj_id",
      referencedColumnName = "prj_id")
  @ManyToOne(cascade = CascadeType.MERGE)
  private Project project;
  
  @Column(name = "bph_man_day")
  private int manDay;
  
  @Column(name = "bph_date_created")
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
   * Returns the project.
   * @return the project
   */
  public Project getProject() {
    return project;
  }
  
  /**
   * Sets the project to project.
   * @param project the project
   */
  public void setProject(Project project) {
    this.project = project;
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
   * Returns dateCreated.
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
