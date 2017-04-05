package com.timelink.services;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.timelink.ejbs.Project;
import com.timelink.ejbs.WorkPackage;
import com.timelink.managers.ProjectManager;
import com.timelink.managers.WorkPackageManager;
import com.timelink.services.interfaces.HRProjectServiceInterface;

@Dependent
@SuppressWarnings("serial")
public class HRProjectService implements HRProjectServiceInterface {
  
  private ProjectManager pm;
  private WorkPackageManager wpm;
  
  final String HR_PROJECT_NAME = "10";
  final String HR_VACATION_NAME = "VACA";
  final String HR_SICKDAY_NAME = "SICKDAY";
  final String HR_FLEXTIME_NAME = "FLEX";
  
  @Inject
  HRProjectService(ProjectManager pm, WorkPackageManager wpm) {
    this.pm = pm;
    this.wpm = wpm;
  }
  
  @Override
  public Project getHRProject() {
    return pm.findByName(HR_PROJECT_NAME);
  }

  @Override
  public boolean isHRProject(Project project) {
    return getHRProject().getProjectNumber() == project.getProjectNumber();
  }

  @Override
  public WorkPackage getVacationWorkPackage() {
    return wpm.findByName(HR_VACATION_NAME);
  }

  @Override
  public boolean isVacationWorkPackage(WorkPackage workPackage) {
    return getVacationWorkPackage().getWorkPackageId() == workPackage.getWorkPackageId();
  }

  @Override
  public WorkPackage getSickDayWorkPackage() {
    return wpm.findByName(HR_SICKDAY_NAME);
  }

  @Override
  public boolean isSickDayWorkPackage(WorkPackage workPackage) {
    return getSickDayWorkPackage().getWorkPackageId() == workPackage.getWorkPackageId();
  }

  @Override
  public WorkPackage getFlextimeWorkPackage() {
    return wpm.findByName(HR_FLEXTIME_NAME);
  }

  @Override
  public boolean isFlextimeWorkPacakge(WorkPackage workPackage) {
    return getFlextimeWorkPackage().getWorkPackageId() == workPackage.getWorkPackageId();
  }

}
