package com.timelink.ejbs;

import java.io.Serializable;

@SuppressWarnings("serial")

/**
 * Required so that Credentials can have a composite primary key.
 * @author Johnathan
 */
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
   * Returns the username.
   * @return the username
   */
  public String getUsername() {
    return username;
  }
  
  /**
   * Sets the username to username.
   * @param username the username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }
  
  /**
   * Returns the password.
   * @return the password
   */
  public String getPassword() {
    return password;
  }
  
  /**
   * Sets the password to password.
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }
}
