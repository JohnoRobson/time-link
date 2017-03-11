package com.timelink.services;

import com.timelink.ejbs.Project;
import com.timelink.ejbs.WorkPackage;
import com.timelink.services.interfaces.WorkPackageCodeServiceInterface;

@SuppressWarnings("serial")
public class WorkPackageCodeService implements WorkPackageCodeServiceInterface {
  
  private static final String WP_STARTING_CODE = "100000000";
  
  private Project project;

  @Override
  public String generateNewCode(Project project, String code) {
    this.project = project;
    
    if (code == null) {
      return incrementSameLevel(findLargestCodeSameLevel(WP_STARTING_CODE));
    } else {
      return incrementSameLevel(findLargestCodeSameLevel(findNextLevel(code)));
    }
  }
  
  /**
   * Returns a workpackage code that's been incremented
   * on the same 'level' as the given code.
   * @param code The code to increment
   * @return An incremented WP code.
   */
  private String incrementSameLevel(String code) {
    StringBuilder sb = new StringBuilder(code);
    
    //Find the first non-zero char
    int toBeInc = sb.indexOf("0") - 1;
    
    //Increment it
    if (Character.isDigit((sb.charAt(toBeInc)))) {
      char digit = sb.charAt(toBeInc);
      if (digit < '9') {
        ++digit;
        sb.setCharAt(toBeInc, digit);
      } else {
        sb.setCharAt(toBeInc, 'A');
      }
    } else if (Character.isAlphabetic((sb.charAt(toBeInc)))) {
      //If it is not a digit increment anyway
      char digit = sb.charAt(toBeInc);
      if (digit < 'Z') {
        ++digit;
        sb.setCharAt(toBeInc, digit);
      } else {
        //Can't increment past Z
        throw new IllegalArgumentException("Cannot increment this workpackage number past " + code);
      }
    }
    
    //if the char is a period, it is the first in a level
    if (sb.charAt(toBeInc) == '.') {
      sb.setCharAt(toBeInc, '1');
    }
    
    return sb.toString();
  }
  
  /**
   * Finds the largest code on the given workpackage 'level.'
   * @param code The code to search
   * @return The largest wp code on the given 'level.'
   */
  private String findLargestCodeSameLevel(String code) {
    String highest = code;
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

}
