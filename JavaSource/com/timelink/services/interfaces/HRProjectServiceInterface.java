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
  
  boolean isFlextimeWorkPackage(WorkPackage workPackage);
  
  WorkPackage getLongTermDisabilityWorkPackage();
  
  boolean isLongTermDisabilityWorkPackage(WorkPackage workPackage);
  
  WorkPackage getShortTermDisabilityWorkPackage();
  
  boolean isShortTermDisabilityWorkPackage(WorkPackage workPackage);
  
  WorkPackage getStatHolidayWorkPackage();
  
  boolean isStatHolidayWorkPackage(WorkPackage workPackage);
}
