package com.timelink.services.interfaces;

import com.timelink.ejbs.WorkPackage;

import java.io.Serializable;
import java.util.Date;

public interface LabourServiceInterface extends Serializable {
  public Integer getBudgeted(WorkPackage wp, Integer labourGradeId);
  
  public Integer getEstimated(WorkPackage wp, Integer labourGradeId, Date start, Date end);
  
  public Float getBudgetToComplete(WorkPackage wp, Integer labourGradeId, Date start, Date end);
  
  public Integer getManDaysForLabourGrade(WorkPackage wp, Integer labourGradeId);
  
  public Float getVariance(WorkPackage wp, Integer labourGradeId, Date start, Date end);
  
  public Float getVariancePercent(WorkPackage wp, Integer labourGradeId, Date start, Date end);
}
