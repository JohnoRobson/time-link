package com.timelink.services.interfaces;

import com.timelink.ejbs.Project;
import com.timelink.ejbs.WorkPackage;

import java.io.Serializable;

public interface WorkPackageCodeServiceInterface extends Serializable {
  public String generateNewCode(Project project, String code, String newNumber);
  
  public String generateChildWildcardCode(WorkPackage wp);
  
  public String generateTopLevelWildcardCode();
}
