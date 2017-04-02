package com.timelink.managers;

import com.timelink.ejbs.EstimatedWorkPackageWorkDays;
import com.timelink.ejbs.Project;
import com.timelink.ejbs.WorkPackage;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

@Dependent
@Stateless
public class EstimatedWorkPackageWorkDaysManager {
  @PersistenceContext(unitName = "timesheet-jpa") EntityManager em;
  
  public EstimatedWorkPackageWorkDays find(int id) {
    return em.find(EstimatedWorkPackageWorkDays.class, id);
  }
  
  /**
   * Returns an EstimatedWorkPackageWorkDays that is for the given parameters.
   * @param wp The WorkPackage that the EstimatedWorkPackageWorkDays is in.
   * @param date The date for the Estimation.
   * @return A List of EstimatedWorkPackageWorkDays, or null if one is not found.
   */
  public List<EstimatedWorkPackageWorkDays> find(WorkPackage wp, Date date) {
    try {
      TypedQuery<EstimatedWorkPackageWorkDays> query
          = em.createQuery("SELECT ew FROM EstimatedWorkPackageWorkDays AS ew WHERE "
          + "ew.workpackage.workPackageId = :wpId "
          + "AND ew.dateCreated = :date "
          + "ORDER BY ew.labourGrade.labourGradeId ASC", EstimatedWorkPackageWorkDays.class)
          .setParameter("wpId", wp.getWorkPackageId())
          .setParameter("date", date, TemporalType.DATE);
      if (query.getResultList().size() > 0) {
        return query.getResultList();
      } else {
        return null;
      }
    } catch (PersistenceException ex) {
      return null;
    }
  }
  
  /**
   * Returns a List EstimatedWorkPackageWorkDays that is for the given parameters.
   * @param pro The Project that the EstimatedWorkPackageWorkDays is for.
   * @param labourGradeId The LabourGrade of the BudgetedHours.
   * @return A List of EstimatedWorkPackageWorkDays, or null if one is not found.
   */
  public List<EstimatedWorkPackageWorkDays> find(Project pro, int labourGradeId) {
    try {
      TypedQuery<EstimatedWorkPackageWorkDays> query
          = em.createQuery("SELECT DISTINCT ew FROM EstimatedWorkPackageWorkDays AS ew, "
          + "Project AS proj WHERE "
          + "proj.projectNumber = :proId "
          + "AND ew.workpackage MEMBER OF proj.workPackages "
          + "AND ew.labourGrade.labourGradeId = :lgId", EstimatedWorkPackageWorkDays.class)
          .setParameter("proId", pro.getProjectNumber())
          .setParameter("lgId", labourGradeId);
      if (query.getResultList().size() > 0) {
        return query.getResultList();
      } else {
        return null;
      }
    } catch (PersistenceException ex) {
      return null;
    }
  }
  
  /**
   * Returns an EstimatedWorkPackageWorkDays that is for the given parameters.
   * @param wp The WorkPackage that the BudgetedHours is in.
   * @param labourGradeId The LabourGrade of the BudgetedHours.
   * @param date The date to be searched.
   * @return A List of EstimatedWorkPackageWorkDays, or null if one is not found.
   */
  public EstimatedWorkPackageWorkDays find(WorkPackage wp, int labourGradeId, Date date) {
    try {
      TypedQuery<EstimatedWorkPackageWorkDays> query
          = em.createQuery("SELECT ew FROM EstimatedWorkPackageWorkDays AS ew WHERE "
          + "ew.labourGrade.labourGradeId = :lgId "
          + "AND ew.workpackage.workPackageId = :wpId "
          + "AND ew.date = :date "
          + "ORDER BY ew.labourGrade.labourGradeId ASC", EstimatedWorkPackageWorkDays.class)
          .setParameter("wpId", wp.getWorkPackageId())
          .setParameter("lgId", labourGradeId)
          .setParameter("date", date);
      if (query.getResultList().size() > 0) {
        return query.getSingleResult();
      } else {
        return null;
      }
    } catch (PersistenceException ex) {
      return null;
    }
  }
  
  /**
   * Returns the latest EstimatedWorkPackageWorkDays that is for the given parameters.
   * @param pro The Project that the EstimatedWorkPackageWorkDays is for.
   * @param labourGradeId The LabourGrade of the BudgetedHours.
   * @return A List of EstimatedWorkPackageWorkDays, or null if one is not found.
   */
  public EstimatedWorkPackageWorkDays findLatest(Project pro, int labourGradeId) {
    try {
      TypedQuery<EstimatedWorkPackageWorkDays> query
          = em.createQuery("SELECT DISTINCT ew FROM EstimatedWorkPackageWorkDays AS ew, "
          + "Project AS proj WHERE "
          + "proj.projectNumber = :proId "
          + "AND ew.workpackage MEMBER OF proj.workPackages "
          + "AND ew.labourGrade.labourGradeId = :lgId "
          + "ORDER BY ew.dateCreated DESC", EstimatedWorkPackageWorkDays.class)
          .setParameter("proId", pro.getProjectNumber())
          .setParameter("lgId", labourGradeId)
          .setMaxResults(1);
      if (query.getResultList().size() > 0) {
        return query.getSingleResult();
      } else {
        return null;
      }
    } catch (PersistenceException ex) {
      return null;
    }
  }
 
  
  public void persist(EstimatedWorkPackageWorkDays ew) {
    em.persist(ew);
  }
  
  public void merge(EstimatedWorkPackageWorkDays ew) {
    em.merge(ew);
  }
  
  /**
   * Returns a List of EstimatedWorkPackageWorkDays associated with the given WorkPackage
   * that have unique dates.  Used to make a list of dates with estimates.
   * @param wp The WorkPackage to be searched.
   * @return A List of EstimatedWorkPackageWorkDays.
   */
  public List<EstimatedWorkPackageWorkDays> getAllWithWorkPackageUniqueDate(WorkPackage wp) {
    TypedQuery<EstimatedWorkPackageWorkDays> query
        = em.createQuery("SELECT ew FROM EstimatedWorkPackageWorkDays AS ew "
        + "WHERE ew.workpackage = :wp GROUP BY ew.dateCreated, ew.estimatedWorkPackageHoursId "
        + "ORDER BY ew.dateCreated DESC", EstimatedWorkPackageWorkDays.class)
        .setParameter("wp", wp);
    return query.getResultList();
  }
  
}
