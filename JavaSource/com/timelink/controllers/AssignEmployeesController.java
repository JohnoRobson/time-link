package com.timelink.controllers;

import com.timelink.ejbs.Project;
import com.timelink.managers.ProjectManager;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("serial")
@SessionScoped
@Named("AssignEmployeesController")
public class AssignEmployeesController implements Serializable {
  @Inject ProjectManager pm;
  private List<Project> projects;
  
  public List<Project> getProjects() {
    return pm.findAll();
  }
}
