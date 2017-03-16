package com.timelink.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.timelink.Session;
import com.timelink.ejbs.BudgetedProjectHours;
import com.timelink.ejbs.EstimatedWorkPackageHours;
import com.timelink.ejbs.Hours;
import com.timelink.ejbs.LabourGrade;
import com.timelink.ejbs.Project;
import com.timelink.managers.BudgetedProjectHoursManager;
import com.timelink.managers.EstimatedWorkPackageHoursManager;
import com.timelink.managers.HoursManager;
import com.timelink.managers.LabourGradeManager;
import com.timelink.managers.ProjectManager;
import com.timelink.services.WeekNumberService;

@SuppressWarnings("serial")
@SessionScoped
@Named("ProjectPlanningController")
public class ProjectPlanningController implements Serializable {
  
  @Inject Session session;
  @Inject ProjectManager pm;
  @Inject LabourGradeManager lgm;
  @Inject BudgetedProjectHoursManager bhm;
  @Inject EstimatedWorkPackageHoursManager ewm;
  @Inject WeekNumberService weekNumberService;
  @Inject HoursManager hm;
  
  private HashSet<Project> projects;
  private List<LabourGrade> labourGrades;
  private Project selectedProject;
  private HashSet<BudgetedProjectHours> budgetedHours;
  
  @PostConstruct
  public void reset() {
    projects = new HashSet<Project>(pm.findByProjectManager(session.getCurrentEmployee().getEmployeeId()));
    labourGrades = lgm.getAllLabourGrades();
    budgetedHours = new HashSet<BudgetedProjectHours>();
    
    for (LabourGrade lg : labourGrades) {
      BudgetedProjectHours bp = new BudgetedProjectHours();
      bp.setLabourGrade(lg);
      budgetedHours.add(bp);
    }
    selectedProject = null;
  }
  
  public List<Project> getProjects() {
    return new ArrayList<Project>(projects);
  }

  /**
   * @return the selectedProject
   */
  public Project getSelectedProject() {
    return selectedProject;
  }

  /**
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
  
  private List<EstimatedWorkPackageHours> getEstimateByLabourGrade(int labourGradeId) {
    if (selectedProject != null) {
      List <EstimatedWorkPackageHours> list = ewm.find(selectedProject, labourGradeId);
      if (list != null) {
        return list; 
      }
    }
    return new ArrayList<EstimatedWorkPackageHours>();
  }
  
  public Integer getTotalEstimate(int labourGradeId) {
    Integer total = 0;
    List<EstimatedWorkPackageHours> list = getEstimateByLabourGrade(labourGradeId);
    for (EstimatedWorkPackageHours ew : list) {
      total += ew.getManDay();
    }
    return total;
  }
  
  public Integer getLastEstimate(int labourGradeId) {
    Integer total = 0;
    Date weekStart = new Date();
    int weekNumber = weekNumberService.getWeekNumber(weekStart);
    weekStart = weekNumberService.getDateFromWeekNumber(weekNumber);
    List<EstimatedWorkPackageHours> list = getEstimateByLabourGrade(labourGradeId);
    for (EstimatedWorkPackageHours ew : list) {
      if (ew.getDateCreated().equals(weekStart)) {
        total += ew.getManDay();
      }
    }
    return total;
  }
  
  public BudgetedProjectHours getBudgetedHourByLabourGrade(int labourGradeId) {
    
    if (selectedProject != null) {
      for (BudgetedProjectHours q : budgetedHours) {
        if (q.getLabourGrade().getLabourGradeId() == labourGradeId) {
          return q;
        }
      }
    }
    
    return new BudgetedProjectHours();
  }
  
  //TODO make this work off of the hour's labour grade, not the employee's
  public Float getTotalChargedHours(int labourGradeId) {
    Float total = 0.0f;
    if (selectedProject != null) {
      List<Hours> list = hm.findByLabourGradeInProject(labourGradeId, selectedProject.getProjectNumber());
      for (Hours ew : list) {
          total += ew.getHour();
      }
    }
    return total;
  }
  
  public String saveChanges() {
    for (BudgetedProjectHours q : budgetedHours) {
      bhm.merge(q);
    }
    reset();
    return null;
  }
  
  public void retriveBudgetedHours() {
    budgetedHours = new HashSet<BudgetedProjectHours>(bhm.find(selectedProject));
    if (budgetedHours.size() == 0) {
      budgetedHours = new HashSet<BudgetedProjectHours>();
      
      for (LabourGrade lg : labourGrades) {
        BudgetedProjectHours bp = new BudgetedProjectHours();
        bp.setLabourGrade(lg);
        bp.setProject(selectedProject);
        budgetedHours.add(bp);
      }
    }
  }
}
