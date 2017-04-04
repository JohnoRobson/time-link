package com.timelink.managers;

import com.timelink.ejbs.BudgetedWorkPackageWorkDays;
import com.timelink.ejbs.WorkPackage;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

@Stateless
@Named
public class BudgetedWorkPackageWorkDaysManager {
  @PersistenceContext(unitName = "timesheet-jpa") EntityManager em;
  
  public BudgetedWorkPackageWorkDays find(int plannedHoursId) {
    return em.find(BudgetedWorkPackageWorkDays.class, plannedHoursId);
  }
  
  /**
   * Returns a BudgetedWorkPackageWorkDays that is for the given parameters.
   * @param wp The WorkPackage that the BudgetedWorkPackageWorkDays is in.
   * @param labourGradeId The LabourGrade of the BudgetedWorkPackageWorkDays.
   * @return A BudgetedWorkPackageWorkDays, or null if one is not found.
   */
  public BudgetedWorkPackageWorkDays find(WorkPackage wp, int labourGradeId) {
    try {
      TypedQuery<BudgetedWorkPackageWorkDays> query
          = em.createQuery("SELECT bh FROM BudgetedWorkPackageWorkDays AS bh WHERE "
          + "bh.labourGrade.labourGradeId = :lgId "
          + "AND bh IN :plhrs", BudgetedWorkPackageWorkDays.class)
          .setParameter("plhrs", wp.getPlannedHours())
          .setParameter("lgId", labourGradeId);
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
   * Adds hours to the database.
   * @param hours The Hours to be added to the database.
   */
  public void persist(BudgetedWorkPackageWorkDays hours) {
    em.persist(hours);
  }
  
  /**
   * Updates an Hours in the database with hours.
   * @param hours The Hours object that will update the database.
   */
  public void merge(BudgetedWorkPackageWorkDays hours) {
    em.merge(hours);
  }
  
  /**
   * Removes a BudgetedWorkPackageWorkDays from the database.
   * @param hours The BudgetedWorkPackageWorkDays to be removed.
   */
  public void remove(BudgetedWorkPackageWorkDays hours) {
    em.remove(hours);
  }
  
}
