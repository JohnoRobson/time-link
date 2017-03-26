package com.timelink.controllers;

import com.timelink.ejbs.Employee;
import com.timelink.ejbs.Project;
import com.timelink.ejbs.WorkPackage;
import com.timelink.enums.WorkPackageStatusEnum;
import com.timelink.managers.EmployeeManager;
import com.timelink.managers.ProjectManager;
import com.timelink.managers.WorkPackageManager;

import org.primefaces.model.DualListModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("serial")
@SessionScoped
@Named("AssignWPController")
public class AssignWorkPackageController implements Serializable {
    @Inject ProjectManager pm;
    @Inject WorkPackageManager wpm;
    @Inject EmployeeManager em;

    private Project selectedProject;
    private WorkPackage selectedWorkPackage;
    private WorkPackage selectedWPStatus;
    // private WorkPackageStatusEnum status;

    private int projectId;
    
  
    /**
     * Return the selectedProject.
     * @return selectedProject
     */
    public Project getSelectedProject() {
        return selectedProject;
    }

    /**
     * Sets the selectedProject to selectedProject.
     * @param selectedProject the selectedProject to set
     */
    public void setSelectedProject(Project selectedProject) {
        this.selectedProject = selectedProject;
    }

    /**
     * Return the selectedWorkPackage.
     * @return selectedWorkPackage
     */
    public WorkPackage getSelectedWorkPackage() {
        return selectedWorkPackage;
    }

    /**
     * Sets the selectedWorkPackage to selectedWorkPackage.
     * @param selectedWorkPackage the selectedWorkPackage to set
     */
    public void setSelectedWorkPackage(WorkPackage selectedWorkPackage) {
        this.selectedWorkPackage = selectedWorkPackage;
    }

    /**
     * Returns the projectId.
     * @return the projectId
     */
    public int getProjectId() {
        return projectId;
    }

    /**
     * Sets the projectId to projectId.
     * @param projectId the projectId to set
     */
    public void setProjectId(int projectId) {
        if (projectId == -1) {
            selectedProject = null;
            selectedWorkPackage = null;
        } else {
            this.projectId = projectId;
            selectedProject = pm.find(projectId);
        }
    }

    /**
     * Returns all projects.
     * @return list of all projects
     */
    public List<Project> getProjects() {
        return pm.findAll();
    }

    /**
     * Return Employees if selectedProject does not equal
     * null.
     * @return DualIstModel of Employees for that selectedProject
     */
    public DualListModel<Employee> getEmployees() {
        List<Employee> employeeSource = new ArrayList<Employee>();
        List<Employee> employeeTarget = new ArrayList<Employee>();

        if (selectedProject != null && selectedWorkPackage != null) {
            employeeSource = em.findByNotInWorkPackage(selectedProject, selectedWorkPackage);
            employeeTarget.addAll(selectedWorkPackage.getAssignedEmployees());
        }

        return new DualListModel<Employee>(employeeSource, employeeTarget);
    }

    /**
     * Merges all Employees in the target array with
     * the selected project.
     * @param employees that are merged with selectedProject
     */
    public void setEmployees(DualListModel<Employee> employees) {
        if (selectedProject != null && selectedWorkPackage != null) {
            selectedWorkPackage.setAssignedEmployees(new HashSet<Employee>(employees.getTarget()));
            wpm.merge(selectedWorkPackage);
        }
    }

    /**
     * Reset the selectedWorkPackage to null.
     */
    public void resetWp() {
        selectedWorkPackage = null;
    }

    /**
     * 
     * @return selectedWPStatus to change statuses
     */
    public WorkPackage getSelectedWPStatus() {
        return selectedWPStatus;
    }

    /**
     * 
     * @param selectedWPStatus selected work package to set
     */
    public void setSelectedWPStatus(WorkPackage selectedWPStatus) {
        this.selectedWPStatus = selectedWPStatus;
    }

    /**
     * Retrieves from WorkPackageStatusEnum as a String[] from the ordinal value 2
     */
    public String[] getWPStatuses() {
        
        String[] enumtest = new String[4];
        
        for(int i = 0 ; i < 4 ; i++) {
            enumtest[i] = WorkPackageStatusEnum.values()[i+2].toString();
        }
        //return WorkPackageStatusEnum.values();
        return enumtest;
    }

    /**
     * Gets the selectedWPStatus status.
     * @return selectedWPStatus' status
     */
    public WorkPackageStatusEnum getSelectedWorkPackageStatus() {
        if (selectedWPStatus != null) {
     
            return selectedWPStatus.getStatus();
            
        }

        return null;
    }

    /**
     * Sets the selectedWPStatus to the selected enum status and merge it to db.
     * @param status from the WorkPackageStatusEnum
     */
    public void setSelectedWorkPackageStatus(WorkPackageStatusEnum status) {
        if (selectedWPStatus != null) {
            selectedWPStatus.setStatus(status);
            wpm.merge(selectedWPStatus);
            selectedWPStatus = wpm.find(selectedWPStatus.getWorkPackageId());

        }
    }

    /**
     * Saves work package status
     * */
    public String save() {
        if (selectedWPStatus != null) {
            wpm.merge(selectedWPStatus);
            //selectedWorkPackage = wpm.find(selectedWorkPackage.getWorkPackageId());
        }
        return null;
    }
    
}