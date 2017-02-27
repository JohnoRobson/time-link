package com.timelink.ejbs;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "labour_grade")
public class LabourGrade implements Serializable {
  
  @Id
  @Column(name = "lg_id")
  private int labourGradeId;
  
  @Column(name = "lg_name")
  private String name;
  
  @Column(name = "lg_cost_rate")
  private float costRate;

  /**
   * @return the labourGradeId
   */
  public int getLabourGradeId() {
    return labourGradeId;
  }

  /**
   * @param labourGradeId the labourGradeId to set
   */
  public void setLabourGradeId(int labourGradeId) {
    this.labourGradeId = labourGradeId;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the costRate
   */
  public float getCostRate() {
    return costRate;
  }

  /**
   * @param costRate the costRate to set
   */
  public void setCostRate(float costRate) {
    this.costRate = costRate;
  }
  
  
}
