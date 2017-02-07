package com.timelink.managers;


import com.timelink.ejbs.Hours;

import java.sql.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Dependent
@Stateless
public class HoursManager {
  @PersistenceContext(unitName = "timesheet-jpa") EntityManager em;
  
  /**
   * Searches the database for an Hours that matches the given composite key.
   * 
   * @param projectId The project ID to be searched for.
   * @param wpId The work package ID to be searched for.
   * @param date The date to be searched for.
   * @return An Hours that matches the given data, or
   *         null if none is found.-
   */
  //TODO fix this
  public Hours find(int projectId, int wpId, Date date) {
    TypedQuery<Hours> query = em.createQuery("SELECT h FROM HOURS AS h WHERE"
        + "h.projectId = :projectId AND h.workPackageId = :wpId AND h.date = :date", Hours.class)
        .setParameter("projectId", projectId)
        .setParameter("upId", wpId)
        .setParameter("date", date);
    return query.getSingleResult();
  }
  
  /**
   * Searches the database for Hours that match a timesheetId.
   * 
   * @param timesheetId to search by
   * @return Hours[] matching the timesheetId
   */
  public List<Hours> findTotalHours(int timesheetId) {
    TypedQuery<Hours> query = em.createQuery("SELECT h FROM Hours h WHERE "
        + "h.timesheetId = :timesheetId", Hours.class)
        .setParameter("timesheetId", timesheetId);
    return query.getResultList();
  }
  
  /**
   * Adds hours to the database.
   * @param hours The Hours to be added to the database.
   */
  public void persist(Hours hours) {
    em.persist(hours);
  }
  
  /**
   * Updates an Hours in the database with hours.
   * @param hours The Hours object that will update the database.
   */
  public void merge(Hours hours) {
    em.merge(hours);
  }
}
