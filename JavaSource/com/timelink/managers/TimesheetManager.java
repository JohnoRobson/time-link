package com.timelink.managers;

import com.timelink.ejbs.Employee;
import com.timelink.ejbs.Timesheet;

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
    Timesheet ts = em.find(Timesheet.class, id);
    return ts;
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
    /*for (TimesheetRow row : ts.getRows()) {
      if (row.getHours() != null) {
        for (Hours h : row.getHours()) {
          h.setProjectId(row.getProjectId());
          h.setWorkPackageId(row.getWorkPackageId());
          em.merge(h);
        }
      }
      em.merge(row);
    }*/
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
  
  /**
   * Returns the latest timesheet in the database for the Employee given.
   * @param emp The Employee whose timesheet will be returned.
   * @return The latest timesheet in the database for the given Employee.
   */
  public Timesheet findLatest(Employee emp) {
    TypedQuery<Timesheet> query = em.createQuery("SELECT t FROM Timesheet AS t WHERE "
        + "t.employee = :emp ORDER BY t.date DESC", Timesheet.class)
        .setParameter("emp", emp);
    
    //If no timesheets are found
    if (query.getResultList().size() == 0) {
      Timesheet tm = new Timesheet(emp);
      persist(tm);
      return tm;
    }
    
    query.setFirstResult(0);
    query.setMaxResults(1);
    
    return query.getSingleResult();
  }
  
  /**
   * Returns a list of timesheets that have the timesheet approver whose id
   * matches empId.
   * @param empId The id of the timesheet approver to be searched for.
   * @return the timesheet approver whose id matches empId.
   */
  public List<Timesheet> findByApprover(int empId) {
    ArrayList<Timesheet> result = new ArrayList<Timesheet>();
    TypedQuery<Employee> queryOne = em.createQuery("SELECT e FROM Employee AS e, "
        + "TimesheetApprover AS tsa "
        + "WHERE e.employeeId = tsa.approveeEmployeeId "
        + "AND :empid = tsa.approverEmployeeId", Employee.class)
        .setParameter("empid", empId);
    
    for (Employee e : queryOne.getResultList()) {
      TypedQuery<Timesheet> query = em.createQuery("SELECT t FROM Timesheet AS t "
          + "WHERE t.employee = :emp", Timesheet.class)
          .setParameter("emp", e);
      List<Timesheet> res = query.getResultList();
      for (Timesheet t : res) {
        result.add(t);
      }
    }
    return result;
  }
  
  /**
   * Returns a list of all Timesheets that an employee has.
   * @param emp The employee to be searched
   * @return A List of all Timesheets belonging to emp.
   */
  public List<Timesheet> findByEmployee(Employee emp) {
    TypedQuery<Timesheet> query = em.createQuery("SELECT t FROM Timesheet As t "
        + "WHERE t.employee = :emp "
        + "ORDER BY t.date DESC", Timesheet.class)
        .setParameter("emp", emp);
    return query.getResultList();
  }
  
  public void detach(Timesheet ts) {
    em.detach(ts);
  }
}
