package com.timelink.managers;

import com.timelink.ejbs.Timesheet;

import java.sql.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Dependent
@Stateless
public class TimesheetManager {
  @PersistenceContext(unitName = "timesheet-jpa") EntityManager em;
  
  
  /**
   * Returns a timesheet that has the Timesheet id id.
   * @param id The id to be searched for.
   * @return A timesheet that has id id, or null if one
   *         was not found.
   */
  public Timesheet find(int id) {
    return em.find(Timesheet.class, id);
  }
  
  /**
   * Adds ts to the database.
   * @param ts The timesheet to be added to the database.
   */
  public void persist(Timesheet ts) {
    em.persist(ts);
  }
  
  /**
   * Updates a timesheet in the database with ts.
   * @param ts The timesheet to be updated.
   */
  public void merge(Timesheet ts) {
    em.merge(ts);
  }
  
  /**
   * Returns a timesheet that matches the timesheetId and date entered.
   * @param tsId The timesheet ID.
   * @param date The timesheet date.
   * @return A timesheet that matches the timesheetId and date entered.
   */
  public Timesheet findByEmpDate(int tsId, Date date) {
    //TODO fix this SQL
    TypedQuery<Timesheet> query = em.createQuery("SELECT t FROM Timesheets WHERE"
        + "TimesheetId = :id AND Date = :date", Timesheet.class)
        .setParameter("id", tsId)
        .setParameter("date", date);
    return query.getSingleResult();
  }
  
  /**
   * Returns a list of timesheets that have the timesheet approver whose id
   * matches empId.
   * @param empId The id of the timesheet approver to be searched for.
   * @return the timesheet approver whose id matches empId.
   */
  public List<Timesheet> findByApprover(int empId) {
    TypedQuery<Timesheet> query = em.createQuery("SELECT t FROM Timesheets WHERE"
        + "TimesheetApprover = :empId", Timesheet.class)
        .setParameter("empId", empId);
    return query.getResultList();
  }
}
