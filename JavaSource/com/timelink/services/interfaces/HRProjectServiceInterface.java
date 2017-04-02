package com.timelink.services.interfaces;

import java.io.Serializable;

import com.timelink.ejbs.Project;
import com.timelink.ejbs.WorkPackage;

public interface HRProjectServiceInterface extends Serializable {
  Project getHRProject();
  
  boolean isHRProject(Project project);
  
  WorkPackage getVacationWorkPackage();
  
  boolean isVacationWorkPackage(WorkPackage workPackage);
  
  WorkPackage getSickDayWorkPackage();
  
  boolean isSickDayWorkPackage(WorkPackage workPackage);
  
  WorkPackage getFlextimeWorkPackage();
  
  boolean isFlextimeWorkPacakge(WorkPackage workPackage);
}
