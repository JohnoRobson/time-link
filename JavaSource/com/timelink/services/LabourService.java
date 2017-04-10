package com.timelink.services;

import com.timelink.ejbs.Hours;
import com.timelink.ejbs.WorkPackage;
import com.timelink.managers.HoursManager;
import com.timelink.managers.WorkPackageManager;
import com.timelink.services.interfaces.LabourServiceInterface;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
@SuppressWarnings("serial")
public class LabourService implements LabourServiceInterface {
  @Inject WorkPackageManager wpm;
  @Inject HoursManager hm;
  
  @Inject
  LabourService(HoursManager hm, WorkPackageManager wpm) {
    this.hm = hm;
    this.wpm = wpm;
  }

  @Override
  public Integer getBudgeted(WorkPackage wp, Integer labourGradeId) {
    List<WorkPackage> childWorkPackages = wpm.getChildWorkPackages(wp);
    Integer total = new Integer(0);
    if (childWorkPackages.size() != 0) {
      for (WorkPackage wp2 : childWorkPackages) {
        total += getBudgeted(wp2, labourGradeId);
      }
    } else {
      total = wp.getPlannedHourFromLabourGrade(labourGradeId).getManDay();
    }
    return total;
  }

  @Override
  public Integer getEstimated(WorkPackage wp, Integer labourGradeId, Date start, Date end) {
    List<WorkPackage> childWorkPackages = wpm.getChildWorkPackages(wp);
    Integer total = new Integer(0);
    if (childWorkPackages.size() != 0) {
      for (WorkPackage wp2 : childWorkPackages) {
        total += getEstimated(wp2, labourGradeId, start, end);
      }
    } else {
      Date budgetDate = wp.getEstimatedHourFromLabourGrade(labourGradeId).getDateCreated();
      if (budgetDate != null 
          && budgetDate.getTime() >= start.getTime() && budgetDate.getTime() <= end.getTime()) {
        total = wp.getEstimatedHourFromLabourGrade(labourGradeId).getManDay();
      }
    }
    return total;
  }

  @Override
  public Float getBudgetToComplete(WorkPackage wp, Integer labourGradeId, Date start, Date end) {
    List<WorkPackage> childWorkPackages = wpm.getChildWorkPackages(wp);
    Float total = new Float(0);
    if (childWorkPackages.size() != 0) {
      for (WorkPackage wp2 : childWorkPackages) {
        total += getBudgetToComplete(wp2, labourGradeId, start, end);
      }
    } else {
      List<Hours> result = hm.find(wp.getProject().getProjectNumber(),
          wp.getWorkPackageId(), labourGradeId);
      System.out.println("START DATE: " + start.toString());
      System.out.println("END DATE: " + end.toString());
      for (Hours h : result) {
        System.out.println("SELECTED DATE: " + h.getDate().toString());
        if (h.getDate().getTime() >= start.getTime() && h.getDate().getTime() <= end.getTime()) {
          total += h.getHour();
        }
      }
      return (total / 8) + wp
          .getEstimatedHourFromLabourGrade(labourGradeId).getManDay();
    }
    return total;
  }

  @Override
  public Integer getManDaysForLabourGrade(WorkPackage wp, Integer labourGradeId) {
    List<WorkPackage> childWorkPackages = wpm.getChildWorkPackages(wp);
    Integer total = new Integer(0);
    if (childWorkPackages.size() != 0) {
      for (WorkPackage wp2 : childWorkPackages) {
        total += getManDaysForLabourGrade(wp2, labourGradeId);
      }
    } else {
      total = wp.getPlannedHourFromLabourGrade(labourGradeId).getManDay();
    }
    return total;
  }

  @Override
  public Float getVariance(WorkPackage wp, Integer labourGradeId, Date start, Date end) {
    List<WorkPackage> childWorkPackages = wpm.getChildWorkPackages(wp);
    Float total = new Float(0);
    if (childWorkPackages.size() != 0) {
      for (WorkPackage wp2 : childWorkPackages) {
        total += getVariance(wp2, labourGradeId, start, end);
      }
    } else {
      total = getManDaysForLabourGrade(wp, labourGradeId)
          - getBudgetToComplete(wp, labourGradeId, start, end);
    }
    return total;
  }

  @Override
  public Float getVariancePercent(WorkPackage wp, Integer labourGradeId, Date start, Date end) {
    List<WorkPackage> childWorkPackages = wpm.getChildWorkPackages(wp);
    Float total = new Float(0);
    if (childWorkPackages.size() != 0) {
      for (WorkPackage wp2 : childWorkPackages) {
        total += getVariancePercent(wp2, labourGradeId, start, end);
      }
    } else {
      Integer plannedHours = getManDaysForLabourGrade(wp, labourGradeId);
      if (plannedHours != null && plannedHours.intValue() != 0) {
        total =  getVariance(wp, labourGradeId, start, end) / plannedHours;
      }
    }
    return total;
  }

  @Override
  public Float getTotalBudgeted(WorkPackage wp) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Float getTotalEstimated(WorkPackage wp) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Float getTotalBudgetedToComplete(WorkPackage wp) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Float getTotalVariance(WorkPackage wp) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Float getTotalVariancePercent(WorkPackage wp) {
    // TODO Auto-generated method stub
    return null;
  }
}
