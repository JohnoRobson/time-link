package com.timelink.controllers;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

@SuppressWarnings("serial")
@Named
public class LoginController implements Serializable {
  
  public String getHello() {
    return "hello";
  }
}
