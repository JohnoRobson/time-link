package com.timelink.managers;

import com.timelink.ejbs.Employee;
import com.timelink.ejbs.Project;
import com.timelink.ejbs.Timesheet;
import com.timelink.ejbs.WorkPackage;
import com.timelink.enums.WorkPackageStatusEnum;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Dependent
@Stateless
public class WorkPackageManager {
  
  @PersistenceContext(unitName = "timesheet-jpa") EntityManager em;
  
  public void detach(WorkPackage wp) {
    em.detach(wp);
  }
  
  /**
   * Returns a WorkPackage from the database with id wpId.
   * 
   * @param wpId The ID to be searched for.
   * @return The workpackage matching the given ID,
   *         or null if one is not found.
   */
  public WorkPackage find(int wpId) {
    return em.find(WorkPackage.class, wpId);
  } 

  
  /**
   * Returns all workpackages assigned to proj.
   * 
   * @param proj The project whose workpackages will
   *             be returned.
   * @return A list of workpackages within proj.
   */
  public List<WorkPackage> find(Project proj) {
    TypedQuery<WorkPackage> query = em.createQuery("SELECT wp FROM WP_Header"
        + "WHERE prj_header_id = :proId", WorkPackage.class)
        .setParameter("proId", proj.getProjectNumber());
    return query.getResultList();
  }
  
  /**
   * Returns a list of WorkPackages that the given employee is assigned to
   * that are in the given Project
   * @param emp The employee
   * @param proj The project
   * @return A list of WorkPackages.
   */
  public List<WorkPackage> findAssigned(Employee emp, Project proj) {
    TypedQuery<WorkPackage> query = em.createQuery("SELECT DISTINCT wph FROM WorkPackage AS wph, "
        + "Project AS proj "
        + "WHERE wph.project.projectNumber = :projNumber "
        + "AND :empPara MEMBER OF wph.assignedEmployees", WorkPackage.class)
        .setParameter("projNumber", proj.getProjectNumber())
        .setParameter("empPara", emp);
    return query.getResultList();
  }
  
  /**
   * Returns the sick day WorkPackage.
   * @return The sick day WorkPackage.
   */
  public WorkPackage findByName(String name) {
    TypedQuery<WorkPackage> query = em.createQuery("SELECT wp FROM WorkPackage AS wp "
        + "WHERE wp.code = :name", WorkPackage.class)
        .setParameter("name", name);
    return query.getSingleResult();
  }
  
//  public List<WorkPackage> findHRWorkPackages(){
//     TypedQuery<WorkPackage> query = em.createQuery("SELECT wp FROM WorkPackage AS wp "
//         + "WHERE wp.project = :code", WorkPackage.class)
//         .setParameter("code", 10);
//     return query.getResultList();
//   }
  
  /**
   * Returns the parent of the given work package.
   * @param wp The work package whose parent will be returned.
   * @return the parent work package, or null if the given work package was
   *     a top-level work package.
   */
  public WorkPackage findParent(WorkPackage wp) {
    //Check if wp is a top-level work package.
    if (wp.getCode().indexOf('0') == 1) {
      return null;
    }
    
    StringBuilder sb = new StringBuilder(wp.getCode());
    int index = sb.indexOf("0");
    
    //If wp is a lowest level work package
    if (index == -1) {
      sb.setCharAt(sb.length(), '0');
    } else {
      sb.setCharAt(index, '0');
    }
    
    TypedQuery<WorkPackage> query = em.createQuery("SELECT wp FROM WorkPackage AS wp "
        + "WHERE wp.code = :code", WorkPackage.class)
        .setParameter("code", sb.toString());
    return query.getSingleResult();
  }
  
  /**
   * Returns all WorkPackages in the given timesheet.
   * @param ts The timesheet to be searched
   * @return A List of WorkPackages in the given timesheet.
   */
  public List<WorkPackage> getAllInTimesheet(Timesheet ts) {
    TypedQuery<WorkPackage> query = em.createQuery("SELECT DISTINCT wp FROM WorkPackage AS wp, "
        + "Hours AS hour WHERE hour.timesheetId = :tsId AND "
        + "wp.workPackageId = hour.workPackageId", WorkPackage.class)
        .setParameter("tsId", ts.getTimesheetId());
    return query.getResultList();
  }
  
  /**
   * Returns a List of all WorkPackages with the given employee
   *   as their responsible engineer.
   * @param emp The responsible engineer.
   * @return A List of WorkPackages where emp is the responsible engineer.
   */
  public List<WorkPackage> getAllWithResponsibleEngineer(Employee emp) {
    TypedQuery<WorkPackage> query = em.createQuery("SELECT wp FROM WorkPackage AS wp "
        + "WHERE wp.responsibleEngineer = :emp", WorkPackage.class)
        .setParameter("emp", emp);
    return query.getResultList();
  }
  
  /**
   * Returns a list of WorkPackages in the given project that have been
   * submitted by a responsible engineer.
   * @param project
   * @return A List of WorkPackages.
   */
  public List<WorkPackage> getWorkPackagesWithStatus(Project project, WorkPackageStatusEnum status) {
    List<WorkPackage> list = new ArrayList<WorkPackage>();
    
    if (project == null) {
      return list;
    } else {
      TypedQuery<WorkPackage> query = em.createQuery("SELECT wp FROM WorkPackage AS wp "
          + "WHERE wp.project = :project AND wp.status = :status", WorkPackage.class)
          .setParameter("project", project)
          .setParameter("status", status);
      list = query.getResultList();
    }
    
    return list;
  }
  
  /**
   * Returns true if the given WorkPackage does not have children.
   * @param wp The WorkPackages to be searched.
   * @return True, if wp has no children.
   */
  public boolean isLeaf(WorkPackage wp) {
    StringBuilder sb = new StringBuilder(wp.getCode());
    int index = sb.indexOf("0");
    if (index == -1) {
      return true;
    } else {
      sb = new StringBuilder(sb.substring(0, index));
    }
    
    TypedQuery<WorkPackage> query = em.createQuery("SELECT wp FROM WorkPackage AS wp "
        + "WHERE wp.project = :workProject AND wp.code LIKE :code "
        + "AND wp.workPackageId != :wpId", WorkPackage.class)
        .setParameter("workProject", wp.getProject())
        .setParameter("code", sb.toString() + "%")
        .setParameter("wpId", wp.getWorkPackageId());
    return query.getResultList().size() == 0;
  }
  
  /**
   * Updates wp in the database.
   * 
   * @param wp the workpackage to be updated in the database.
   */
  public void merge(WorkPackage wp) {
    em.merge(wp);
  }
  
  /**
   * Adds wp to the database.
   * 
   * @param wp the workpackage to be added to the database.
   */
  public void persist(WorkPackage wp) {
    em.persist(wp);
  }
}
