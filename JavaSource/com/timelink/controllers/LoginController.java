package com.timelink.controllers;

import com.timelink.managers.CredentialManager;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("serial")
@ManagedBean
@Named("LoginController")
public class LoginController implements Serializable {
  
  @Inject CredentialManager cm;
  
  public String getHello() {
    return cm.find("Admin", "Admin").getUsername();
  }
  
  
}
