package com.timelink.controllers;

import com.timelink.Session;
import com.timelink.ejbs.BudgetedProjectWorkDays;
import com.timelink.ejbs.BudgetedWorkPackageWorkDays;
import com.timelink.ejbs.LabourGrade;
import com.timelink.ejbs.WorkPackage;
import com.timelink.enums.WorkPackageStatusEnum;
import com.timelink.managers.BudgetedProjectWorkDaysManager;
import com.timelink.managers.BudgetedWorkPackageWorkDaysManager;
import com.timelink.managers.LabourGradeManager;
import com.timelink.managers.WorkPackageManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("serial")
@SessionScoped
@Named("BudgetWorkPackageREController")
public class WorkPackageBudgetResponsibleEngineerController implements Serializable {
  
  @Inject Session session;
  @Inject WorkPackageManager wpm;
  @Inject LabourGradeManager lgm;
  @Inject BudgetedWorkPackageWorkDaysManager bwphm;
  @Inject BudgetedProjectWorkDaysManager bphm;
  
  private List<LabourGrade> labourGrades;
  private List<WorkPackage> workPackageList;
  private WorkPackage editingWorkPackage;
  
  /**
   * Resets the state of this controller.
   */
  @PostConstruct
  public void reset() {
    editingWorkPackage = null;
    labourGrades = null;
    workPackageList = null;
  }
  
  /**
   * Returns a list of work packages that have been submitted to the logged in responsible engineer.
   * @return A List of Work Packages.
   */
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
  
  /**
   * Submits the edited work package.
   */
  public void submitWorkPackage() {
    editingWorkPackage.setStatus(WorkPackageStatusEnum.SUBMITTED_TO_PROJECT_MANAGER);
    for (LabourGrade lg : getLabourGrades()) {
      if (editingWorkPackage.getPlannedHourFromLabourGrade(lg.getLabourGradeId())
          .getManDay() != 0) {
        editingWorkPackage.getPlannedHourFromLabourGrade(lg.getLabourGradeId())
          .setLabourGrade(lg);
        editingWorkPackage.getPlannedHourFromLabourGrade(lg.getLabourGradeId())
          .setWorkPackageLineId(editingWorkPackage);
        editingWorkPackage.getPlannedHourFromLabourGrade(lg.getLabourGradeId())
          .setDateCreated(new Date());
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
  
  /**
   * Returns the sum of the budget for the given workpackage,
   *  minus the sum of the budget for its children.
   * @param wp The given Work Package
   * @return The budget remaining.
   */
  public String getDaysRemaining(WorkPackage wp, Integer labourGradeId) {
    WorkPackage parent = wpm.findParent(wp);
    float parentBudget = 0;
    float parentCosts  = 0;
    
    if (parent == null) {
      List<BudgetedProjectWorkDays> hrs = bphm.find(wp.getProject());
      
      for (BudgetedProjectWorkDays bh : hrs) {
        if (bh.getLabourGrade().getLabourGradeId() == labourGradeId) {
          parentBudget += bh.getManDay();
        }
      }
      
      List<WorkPackage> children = wpm.getTopLevelWorkPackages(wp.getProject());
      
      for (WorkPackage ww : children) {
        for (BudgetedWorkPackageWorkDays bh : ww.getPlannedHours()) {
          if (bh.getLabourGrade().getLabourGradeId() == labourGradeId) {
            parentCosts += bh.getManDay();
          }
        }
      }
      
      return "" + (parentBudget - parentCosts);
    }
    
    for (BudgetedWorkPackageWorkDays bh : parent.getPlannedHours()) {
      if (bh.getLabourGrade().getLabourGradeId() == labourGradeId) {
        parentBudget += bh.getManDay();
      }
    }
    
    List<WorkPackage> children = wpm.getChildWorkPackages(parent);
    
    for (WorkPackage ww : children) {
      for (BudgetedWorkPackageWorkDays bh : ww.getPlannedHours()) {
        if (bh.getLabourGrade().getLabourGradeId() == labourGradeId) {
          parentCosts += bh.getManDay();
        }
      }
    }
    
    return "" + (parentBudget - parentCosts);
  }
  
  /**
   * JSF validator to ensure that the budgeted days cannot exceed the remaining days
   * in the parent work package.
   * @param context etc
   * @param component etc
   * @param value the name being checked
   * @throws ValidatorException etc
   */
  public void validateDays(FacesContext context, UIComponent component, Object value)
      throws ValidatorException {
    if (!(value instanceof Integer)) {
      throw new IllegalArgumentException("value not a Float");
    }
    
    String days = (String) component.getAttributes().get("remainingDays");
    
    float inputDays = (Integer) value;
    float remainingDays = Float.parseFloat(days);
    
    if (remainingDays - inputDays < 0) {
      throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
          "Cannot budget for more days than availible",
          "Cannot budget for more days than availible"));
    }
  }
  
}
