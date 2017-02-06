package com.timelink.managers;

import com.timelink.ejbs.Employee;
import com.timelink.ejbs.Hours;
import com.timelink.ejbs.Timesheet;
import com.timelink.ejbs.TimesheetRow;

import java.sql.Date;
import java.util.ArrayList;
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
    Timesheet t = em.find(Timesheet.class, id);
    
    for (TimesheetRow row : t.getRows()) {
      if (row.getHours() == null || row.getHours().size() < 6) {
        ArrayList<Hours> hrs = new ArrayList<>();
        for (int i = 0; i < 7; ++i) {
          Hours h = new Hours();
          h.setHour(0);
          h.setTimesheetId(id);
          h.setTimesheetLineId(row.getTimesheetRowId());
          hrs.add(h);
        }
        row.setHours(hrs);
      }
    }
    return t;
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
    for (TimesheetRow row : ts.getRows()) {
      if (row.getHours() != null) {
        for (Hours h : row.getHours()) {
          em.merge(h);
        }
      }
      em.merge(row);
    }
    em.merge(ts);
  }
  
  /**
   * Returns a timesheet that matches the timesheetId and date entered.
   * @param tsId The timesheet ID.
   * @param date The timesheet date.
   * @return A timesheet that matches the timesheetId and date entered.
   */
  public Timesheet findByEmpDate(int tsId, Date date) {
    TypedQuery<Timesheet> query = em.createQuery("SELECT t FROM Timesheet WHERE "
        + "timesheetId = :id AND date = :date", Timesheet.class)
        .setParameter("id", tsId)
        .setParameter("date", date);
    return query.getSingleResult();
  }
  
  public Timesheet findLatest(Employee emp) {
    TypedQuery<Timesheet> query = em.createQuery("SELECT t FROM Timesheet AS t WHERE "
        + "t.employeeId = :empId ORDER BY t.date", Timesheet.class)
        .setParameter("empId", emp.getEmployeeId());
    
    return find(query.getSingleResult().getEmployeeId());
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
