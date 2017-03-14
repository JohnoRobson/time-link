package com.timelink.services.interfaces;

import com.timelink.ejbs.Project;

import java.io.Serializable;

public interface WorkPackageCodeServiceInterface extends Serializable {
  public String generateNewCode(Project project, String code);
}
