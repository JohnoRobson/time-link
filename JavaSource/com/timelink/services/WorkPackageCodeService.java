package com.timelink.services;

import com.timelink.ejbs.Project;
import com.timelink.ejbs.WorkPackage;
import com.timelink.services.interfaces.WorkPackageCodeServiceInterface;

@SuppressWarnings("serial")
public class WorkPackageCodeService implements WorkPackageCodeServiceInterface {
  
  private static final String WP_STARTING_CODE = "1000000";
  private static final String WP_ZERO_CODE     = "0000000";

  @Override
  public String generateNewCode(Project project, String code, String newNumber) {
    if (code == null || code.length() == 0) {
      return incrementSameLevel(findLargestCodeSameLevel(WP_STARTING_CODE, project), newNumber, project);
    } else {
      return incrementSameLevel(findLargestCodeSameLevel(findNextLevel(code), project), newNumber, project);
    }
  }
  
  /**
   * Returns a workpackage code that's been incremented
   * on the same 'level' as the given code.
   * @param code The code to increment
   * @return An incremented WP code.
   */
  private String incrementSameLevel(String code, String newNumber, Project project) {
    StringBuilder sb = new StringBuilder(code);
    
    //If it is the first work package in a project
    if (code.equals(WP_ZERO_CODE)) {
      StringBuilder q = new StringBuilder(WP_ZERO_CODE);
      q.setCharAt(0, uppercase(newNumber));
      checkForUnique(project, q.toString());
      return q.toString();
    }
    
    //Find the first non-zero char
    int toBeInc = sb.indexOf("0") - 1;
    
    char digit = sb.charAt(toBeInc);
    if (digit == 'Z') {
      //Can't increment past Z
      throw new IllegalArgumentException("Cannot increment this workpackage number past " + code);
    } else {
      sb.setCharAt(toBeInc, uppercase(newNumber));
    }
    checkForUnique(project, sb.toString());
    return sb.toString();
  }
  
  /**
   * Finds the largest code on the given workpackage 'level.'
   * @param code The code to search
   * @param project The project given
   * @return The largest wp code on the given 'level.'
   */
  private String findLargestCodeSameLevel(String code, Project project) {
    String highest = code;
    
    //There are no work packages.
    if (project.getWorkPackages().size() == 0) {
      return WP_ZERO_CODE;
    }
    
    for (WorkPackage wp : project.getWorkPackages()) {
      int indexOfChk = wp.getCode().indexOf('0');
      int indexOfCur = code.indexOf('0');
      if (code.substring(0, indexOfChk - 1).equals(wp.getCode().substring(0, indexOfCur - 1))) {
        if (indexOfChk == indexOfCur) {
          if (wp.getCode().charAt(indexOfChk - 1) > highest.charAt(indexOfCur - 1)) {
            highest = wp.getCode();
          }
        }
      }
    }
    
    return highest;
  }
  
  private String findNextLevel(String code) {
    StringBuilder sb = new StringBuilder(code);
    sb.setCharAt(code.indexOf('0'), '.');
    return sb.toString();
  }
  
  private void checkForUnique(Project project, String code) {
    for (WorkPackage wp : project.getWorkPackages()) {
      if (wp.getCode().equals(code)) {
        throw new IllegalArgumentException("Code " + code + " is already taken");
      }
    }
  }
  
  private char uppercase(String code) {
    char c = code.charAt(0);
    if (Character.isDigit(c)) {
      return c;
    } else {
      return Character.toUpperCase(c);
    }
  }

}
