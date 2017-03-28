package com.timelink.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.timelink.Session;
import com.timelink.ejbs.LabourGrade;
import com.timelink.ejbs.WorkPackage;
import com.timelink.enums.WorkPackageStatusEnum;
import com.timelink.managers.LabourGradeManager;
import com.timelink.managers.WorkPackageManager;

@SuppressWarnings("serial")
@SessionScoped
@Named("BudgetWorkPackageREController")
public class WorkPackageBudgetResponsibleEngineerController implements Serializable {
  
  @Inject Session session;
  @Inject WorkPackageManager wpm;
  @Inject LabourGradeManager lgm;
  
  private List<LabourGrade> labourGrades;
  private List<WorkPackage> workPackageList;
  private WorkPackage editingWorkPackage;
  
  @PostConstruct
  public void reset() {
    editingWorkPackage = null;
    labourGrades = null;
    workPackageList = null;
  }
  
  public List<WorkPackage> getWorkPackageList() {
    if (workPackageList == null) {
      List<WorkPackage> list = wpm.getAllWithResponsibleEngineer(session.getCurrentEmployee());
      workPackageList = new ArrayList<WorkPackage>();
      if (list == null) {
        return workPackageList;
      }
      
      for (WorkPackage wp : list) {
        if (wp.getStatus() == WorkPackageStatusEnum.SUBMITTED_TO_RESPONSIBLE_ENGINEER) {
          workPackageList.add(wp);
        }
      }
      return workPackageList;
    } else {
      return workPackageList;
    }
  }
  
  public void submitWorkPackage() {
    editingWorkPackage.setStatus(WorkPackageStatusEnum.SUBMITTED_TO_PROJECT_MANAGER);
    for (LabourGrade lg : getLabourGrades()) {
      if (editingWorkPackage.getPlannedHourFromLabourGrade(lg.getLabourGradeId()).getManDay() != 0) {
        editingWorkPackage.getPlannedHourFromLabourGrade(lg.getLabourGradeId()).setLabourGrade(lg);
        editingWorkPackage.getPlannedHourFromLabourGrade(lg.getLabourGradeId()).setWorkPackageLineId(editingWorkPackage);
        editingWorkPackage.getPlannedHourFromLabourGrade(lg.getLabourGradeId()).setDateCreated(new Date());
      } else {
        editingWorkPackage.removePlannedHourByLabourGrade(lg.getLabourGradeId());
      }
    }
    wpm.merge(editingWorkPackage);
    reset();
  }
  
  /**
   * Returns a list of all labour grades in the database.
   * @return A list of all labour grades in the database.
   */
  public List<LabourGrade> getLabourGrades() {
    if (labourGrades == null) {
      labourGrades = lgm.getAllLabourGrades();
    }
    
    return labourGrades;
  }
  
  /**
   * Returns the editingWorkPackage.
   * @return the editingWorkPackage
   */
  public WorkPackage getEditingWorkPackage() {
    return editingWorkPackage;
  }

  /**
   * Sets editingWorkPackage.
   * @param editingWorkPackage the editingWorkPackage to set
   */
  public void setEditingWorkPackage(WorkPackage editingWorkPackage) {
    this.editingWorkPackage = editingWorkPackage;
  }
}
