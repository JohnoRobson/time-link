package com.timelink.ejbs;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CredentialsId implements Serializable {
  String username;
  String password;
  
  public CredentialsId() {
    
  }
  
  public CredentialsId(String username, String password) {
    this.username = username;
    this.password = password;
  }

  /**
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * @param username the username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }
}
