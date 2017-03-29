package com.timelink.managers;


import com.timelink.ejbs.BudgetedWorkPackageHours;
import com.timelink.ejbs.WorkPackage;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

@Stateless
@Named
public class BudgetedWorkPackageHoursManager {
  @PersistenceContext(unitName = "timesheet-jpa") EntityManager em;
  
  public BudgetedWorkPackageHours find(int plannedHoursId) {
    return em.find(BudgetedWorkPackageHours.class, plannedHoursId);
  }
  
  /**
   * Returns a BudgetedHours that is for the given parameters.
   * @param wp The WorkPackage that the BudgetedHours is in.
   * @param labourGradeId The LabourGrade of the BudgetedHours.
   * @return A BudgetedHours, or null if one is not found.
   */
  public BudgetedWorkPackageHours find(WorkPackage wp, int labourGradeId) {
    try {
      TypedQuery<BudgetedWorkPackageHours> query
          = em.createQuery("SELECT bh FROM BudgetedWorkPackageHours AS bh WHERE "
          + "bh.labourGrade.labourGradeId = :lgId "
          + "AND bh IN :plhrs", BudgetedWorkPackageHours.class)
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
  public void persist(BudgetedWorkPackageHours hours) {
    em.persist(hours);
  }
  
  /**
   * Updates an Hours in the database with hours.
   * @param hours The Hours object that will update the database.
   */
  public void merge(BudgetedWorkPackageHours hours) {
    em.merge(hours);
  }
  
  /**
   * Removes a BudgetedHours from the database.
   * @param hours The BudgetedHours to be removed.
   */
  public void remove(BudgetedWorkPackageHours hours) {
    em.remove(hours);
  }
  
}
