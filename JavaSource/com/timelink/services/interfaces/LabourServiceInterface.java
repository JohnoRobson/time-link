package com.timelink.services.interfaces;

import com.timelink.ejbs.WorkPackage;

import java.io.Serializable;

public interface LabourServiceInterface extends Serializable {
  public Integer getBudgeted(WorkPackage wp, Integer labourGradeId);
  
  public Integer getEstimated(WorkPackage wp, Integer labourGradeId);
  
  public Float getBudgetToComplete(WorkPackage wp, Integer labourGradeId);
  
  public Integer getManDaysForLabourGrade(WorkPackage wp, Integer labourGradeId);
  
  public Float getVariance(WorkPackage wp, Integer labourGradeId);
  
  public Float getVariancePercent(WorkPackage wp, Integer labourGradeId);
  
  public Float getTotalBudgeted(WorkPackage wp);
  
  public Float getTotalEstimated(WorkPackage wp);
  
  public Float getTotalBudgetedToComplete(WorkPackage wp);
  
  public Float getTotalVariance(WorkPackage wp);
  
  public Float getTotalVariancePercent(WorkPackage wp);
}
