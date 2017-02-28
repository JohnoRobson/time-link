package com.timelink.managers;


import com.timelink.ejbs.PlannedHours;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Dependent
@Stateless
public class PlannedHoursManager {
  @PersistenceContext(unitName = "timesheet-jpa") EntityManager em;
  
  public PlannedHours find(int plannedHoursId) {
    return em.find(PlannedHours.class, plannedHoursId);
  }
  
  /**
   * Adds hours to the database.
   * @param hours The Hours to be added to the database.
   */
  public void persist(PlannedHours hours) {
    em.persist(hours);
  }
  
  /**
   * Updates an Hours in the database with hours.
   * @param hours The Hours object that will update the database.
   */
  public void merge(PlannedHours hours) {
    em.merge(hours);
  }
}
