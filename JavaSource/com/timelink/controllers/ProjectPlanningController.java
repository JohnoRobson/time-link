package com.timelink.controllers;

import com.timelink.Session;
import com.timelink.ejbs.BudgetedProjectWorkDays;
import com.timelink.ejbs.EstimatedWorkPackageWorkDays;
import com.timelink.ejbs.Hours;
import com.timelink.ejbs.LabourGrade;
import com.timelink.ejbs.Project;
import com.timelink.managers.BudgetedProjectWorkDaysManager;
import com.timelink.managers.EstimatedWorkPackageHoursManager;
import com.timelink.managers.HoursManager;
import com.timelink.managers.LabourGradeManager;
import com.timelink.managers.ProjectManager;
import com.timelink.services.WeekNumberService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("serial")
@SessionScoped
@Named("ProjectPlanningController")
public class ProjectPlanningController implements Serializable {
  
  @Inject Session session;
  @Inject ProjectManager pm;
  @Inject LabourGradeManager lgm;
  @Inject BudgetedProjectWorkDaysManager bhm;
  @Inject EstimatedWorkPackageHoursManager ewm;
  @Inject WeekNumberService weekNumberService;
  @Inject HoursManager hm;
  
  private HashSet<Project> projects;
  private List<LabourGrade> labourGrades;
  private Project selectedProject;
  private HashSet<BudgetedProjectWorkDays> budgetedHours;
  
  /**
   * Resets the state of this SessionBean.
   */
  @PostConstruct
  public void reset() {
    projects = new HashSet<Project>(
          pm.findByProjectManager(session.getCurrentEmployee().getEmployeeId())
        );
    labourGrades = lgm.getAllLabourGrades();
    budgetedHours = new HashSet<BudgetedProjectWorkDays>();
    
    for (LabourGrade lg : labourGrades) {
      BudgetedProjectWorkDays bp = new BudgetedProjectWorkDays();
      bp.setLabourGrade(lg);
      budgetedHours.add(bp);
    }
    selectedProject = null;
  }
  
  public List<Project> getProjects() {
    return new ArrayList<Project>(projects);
  }

  /**
   * Returns selectedProject.
   * @return the selectedProject
   */
  public Project getSelectedProject() {
    return selectedProject;
  }

  /**
   * Sets the selectedProject.
   * @param selectedProject the selectedProject to set
   */
  public void setSelectedProject(Project selectedProject) {
    this.selectedProject = selectedProject;
  }
  
  /**
   * returns the selectedProjectId.
   * @return the selectedProjectId
   */
  public Integer getSelectedProjectId() {
    if (selectedProject != null) {
      return selectedProject.getProjectNumber();
    }
    
    return null;
  }
  
  /**
   * Sets the selectedProjectId to selectedProjectId.
   * @param selectedProjectId the selectedProjectId to set
   */
  public void setSelectedProjectId(Integer selectedProjectId) {
    if (selectedProjectId == null) {
      return;
    }
    
    if (this.selectedProject != null
        && this.selectedProject.getProjectNumber() == selectedProjectId) {
      return;
    }
    
    Project pro = pm.find(selectedProjectId);
    if (pro != null) {
      pm.detach(pro);
      this.selectedProject = pm.find(selectedProjectId);
      retriveBudgetedHours();
    }
  }
  
  public List<LabourGrade> getLabourGrades() {
    return labourGrades;
  }
  
  private List<EstimatedWorkPackageWorkDays> getEstimateByLabourGrade(int labourGradeId) {
    if (selectedProject != null) {
      List<EstimatedWorkPackageWorkDays> list = ewm.find(selectedProject, labourGradeId);
      if (list != null) {
        return list; 
      }
    }
    return new ArrayList<EstimatedWorkPackageWorkDays>();
  }
  
//  /**
//   * Returns the sum of the estimates that have the labourGradeId.
//   * @param labourGradeId The labourGradeId to be searched.
//   * @return A sum of the estimates.
//   */
//  public Integer getTotalEstimate(int labourGradeId) {
//    Integer total = 0;
//    List<EstimatedWorkPackageHours> list = getEstimateByLabourGrade(labourGradeId);
//    for (EstimatedWorkPackageHours ew : list) {
//      total += ew.getManDay();
//    }
//    return total;
//  }
  
  /**
   * Returns the sum of the estimates of the last week
   * with the given labourGrade.
   * @param labourGradeId The labourGradeId to be searched.
   * @return A sum of the estimates.
   */
  public Integer getLastEstimate(int labourGradeId) {
    Integer total = 0;
    if (selectedProject != null) {
      EstimatedWorkPackageWorkDays last = ewm.findLatest(selectedProject, labourGradeId);
      List<EstimatedWorkPackageWorkDays> list = getEstimateByLabourGrade(labourGradeId);
      for (EstimatedWorkPackageWorkDays ew : list) {
        if (ew.getDateCreated().equals(last.getDateCreated())) {
          total += ew.getManDay();
        }
      }
    }
    return total;
  }
  
  /**
   * Returns a BudgetedProjectHour with the given labourGradeId.
   * @param labourGradeId The labourGradeId to be searched.
   * @return A BudgetedProjectHour.
   */
  public BudgetedProjectWorkDays getBudgetedHourByLabourGrade(int labourGradeId) {
    if (selectedProject != null) {
      for (BudgetedProjectWorkDays q : budgetedHours) {
        if (q.getLabourGrade().getLabourGradeId() == labourGradeId) {
          return q;
        }
      }
    }
    
    return new BudgetedProjectWorkDays();
  }
  
  /**
   * Returns the total of the charged hours for the given labourGradeId
   * @param labourGradeId The labourGradeId to be searched.
   * @return The sum of the charged hours.
   */
  //TODO make this work off of the hour's labour grade, not the employee's
  public Float getTotalChargedHours(int labourGradeId) {
    Float total = 0.0f;
    if (selectedProject != null) {
      List<Hours> list
          = hm.findApprovedByLabourGradeInProject(labourGradeId,
              selectedProject.getProjectNumber());
      for (Hours ew : list) {
        total += ew.getHour();
      }
    }
    return total;
  }
  
  /**
   * Merges all budgetedHours to the database and resets this bean.
   * @return null, to reload the page.
   */
  public String saveChanges() {
    for (BudgetedProjectWorkDays q : budgetedHours) {
      bhm.merge(q);
    }
    reset();
    return null;
  }
  
  /**
   * Sets budgetedHours to the budgetedHours for the selectedProject that are found in the database.
   */
  public void retriveBudgetedHours() {
    budgetedHours = new HashSet<BudgetedProjectWorkDays>(bhm.find(selectedProject));
    if (budgetedHours.size() == 0) {
      budgetedHours = new HashSet<BudgetedProjectWorkDays>();
      
      for (LabourGrade lg : labourGrades) {
        BudgetedProjectWorkDays bp = new BudgetedProjectWorkDays();
        bp.setLabourGrade(lg);
        bp.setProject(selectedProject);
        budgetedHours.add(bp);
      }
    }
  }
}
