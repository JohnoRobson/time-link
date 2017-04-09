package com.timelink.services;

import com.timelink.ejbs.Hours;
import com.timelink.ejbs.LabourGrade;
import com.timelink.ejbs.WorkPackage;
import com.timelink.managers.HoursManager;
import com.timelink.managers.ProjectManager;
import com.timelink.managers.WorkPackageManager;
import com.timelink.services.interfaces.LabourServiceInterface;

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
  public Integer getEstimated(WorkPackage wp, Integer labourGradeId) {
    List<WorkPackage> childWorkPackages = wpm.getChildWorkPackages(wp);
    Integer total = new Integer(0);
    if (childWorkPackages.size() != 0) {
      for (WorkPackage wp2 : childWorkPackages) {
        total += getEstimated(wp2, labourGradeId);
      }
    } else {
      total = wp.getEstimatedHourFromLabourGrade(labourGradeId).getManDay();
    }
    return total;
  }

  @Override
  public Float getBudgetToComplete(WorkPackage wp, Integer labourGradeId) {
    List<WorkPackage> childWorkPackages = wpm.getChildWorkPackages(wp);
    Float total = new Float(0);
    if (childWorkPackages.size() != 0) {
      for (WorkPackage wp2 : childWorkPackages) {
        total += getBudgetToComplete(wp2, labourGradeId);
      }
    } else {
      List<Hours> result = hm.find(wp.getProject().getProjectNumber(),
          wp.getWorkPackageId(), labourGradeId);
      for (Hours h : result) {
        total += h.getHour();
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
  public Float getVariance(WorkPackage wp, Integer labourGradeId) {
    List<WorkPackage> childWorkPackages = wpm.getChildWorkPackages(wp);
    Float total = new Float(0);
    if (childWorkPackages.size() != 0) {
      for (WorkPackage wp2 : childWorkPackages) {
        total += getVariance(wp2, labourGradeId);
      }
    } else {
      total = getManDaysForLabourGrade(wp, labourGradeId)
          - getBudgetToComplete(wp, labourGradeId);
    }
    return total;
  }

  @Override
  public Float getVariancePercent(WorkPackage wp, Integer labourGradeId) {
    List<WorkPackage> childWorkPackages = wpm.getChildWorkPackages(wp);
    Float total = new Float(0);
    if (childWorkPackages.size() != 0) {
      for (WorkPackage wp2 : childWorkPackages) {
        total += getVariancePercent(wp2, labourGradeId);
      }
    } else {
      Integer plannedHours = getManDaysForLabourGrade(wp, labourGradeId);
      if (plannedHours != null && plannedHours.intValue() != 0) {
        total =  getVariance(wp, labourGradeId) / plannedHours;
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
  
  /*
   * 
   * /**
   * Get budget to complete. Which is the estimate,
   * minus the actual hours.
   * @param labourGradeId to find hours by
   * @return estimatedHours minus the actual hours
   * 
   
   List<WorkPackage> childWorkPackages = wpm.getChildWorkPackages(workPackage);
    Float complete = new Float(0);
    lrc.setSelectedProject(selectedProject);
    if (childWorkPackages.size() != 0) {
      for (WorkPackage wp : childWorkPackages) {
        lrc.setSelectedWorkPackage(wp);
        complete += getTotalBudgetToComplete(wp);
      }
    } else {
      lrc.setSelectedWorkPackage(workPackage);
      complete = lrc.getTotalBudgetedToComplete();
    }
    return complete;
  
  public Float getVariancePercent(Integer labourGradeId) {
    Integer plannedHours = getManDaysForLabourGrade(labourGradeId);
    if (selectedWorkPackage != null && plannedHours != null && plannedHours.intValue() != 0) {
      return getVariance(labourGradeId) / plannedHours;
    }
    return null;
  }
  
  public Float getTotalBudgeted() {
    if (selectedWorkPackage != null) {
      Float total = new Float(0);
      for (LabourGrade lb : lgm.getAllLabourGrades()) {
        total += getManDaysForLabourGrade(lb.getLabourGradeId()) * lb.getCostRate();
      }
      return total;
    }
    return null;
  }
  
  public Float getTotalEstimated() {
    if (selectedWorkPackage != null) {
      Float total = new Float(0);
      for (LabourGrade lb : lgm.getAllLabourGrades()) {
        total += selectedWorkPackage.getEstimatedHourFromLabourGrade(lb.getLabourGradeId())
            .getManDay() * lb.getCostRate();
      }
      return total;
    }
    return null;
  }
  
  public Float getTotalBudgetedToComplete() {
    if (selectedWorkPackage != null) {
      Float total = new Float(0);
      for (LabourGrade lb : lgm.getAllLabourGrades()) {
        total += getBudgetToComplete(lb.getLabourGradeId()) * lb.getCostRate();
      }
      return total;
    }
    return null;
  }
  
  public Float getTotalVariance() {
    if (selectedWorkPackage != null) {
      Float total = new Float(0);
      for (LabourGrade lb : lgm.getAllLabourGrades()) {
        total += getVariance(lb.getLabourGradeId()) * lb.getCostRate();
      }
      return total;
    }
    return null;
  }
  
  public Float getTotalVariancePercent() {
    if (selectedWorkPackage != null) {
      Float total = new Float(0);
      Float check = new Float(0);
      int counter = 0;
      for (LabourGrade lb : lgm.getAllLabourGrades()) {
        check = getVariancePercent(lb.getLabourGradeId());
        if (check != null) {
          counter++;
          total += check;
        }
      }
      return counter == 0 ? total : (total / counter);
    }
    return null;
  }
}
   */
}
