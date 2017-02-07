package com.timelink.ejbs;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Dependent
@Table(name = "ts_line")
public class TimesheetRow {
  
  @Transient
  @PersistenceContext(unitName = "timesheet-jpa") EntityManager em;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "tsl_id")
  private int timesheetRowId;
  
  @Transient
  private Integer projectId = 0;
  
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "tsl_tsh_id",
      referencedColumnName = "tsh_id")
  private Timesheet timesheet;
  
  @Transient
  private Integer workPackageId = 0;
  
  @OneToMany(fetch = FetchType.EAGER,
      cascade = CascadeType.ALL)
  @JoinColumn(name = "tsl_id",
      referencedColumnName = "tsl_id")
  private List<Hours> hours;
  
  @Column(name = "tsl_note")
  private String note;
  
  //TODO Fix this stub.
  public boolean validate() {
    return true;
  }
  
  /**
   * The constructor for TimesheetRow.
   */
  public TimesheetRow() {
    hours = new ArrayList<Hours>();
    for (int i = 0; i < 7; ++i) {
      Hours hrs = new Hours();
      hrs.setTimesheetLineId(timesheetRowId);
      if (timesheet != null) {
        hrs.setTimesheetId(timesheet.getTimesheetId());
      }
      hours.add(hrs);
    }
  }
  
  /**
   * The constructor for TimesheetRow used when added a new timesheet row
   * to a timesheet.
   */
  public TimesheetRow(Timesheet ts) {
    timesheet = ts;
    hours = new ArrayList<Hours>();
    for (int i = 0; i < 7; ++i) {
      Hours hrs = new Hours();
      System.out.println("timesheetID: " + ts.getTimesheetId());
      hrs.setTimesheetLineId(timesheetRowId);
      hrs.setTimesheetId(ts.getTimesheetId());
      hours.add(hrs);
    }
  }
  
  /**
   * Returns the projectId.
   * @return the projectId.
   */
  public Integer getProjectId() {
    if (projectId == 0 && getHours().size() > 0) {
      projectId = getHours().get(0).getProjectId();
    }
    return projectId;
  }
  
  /**
   * Sets projectId to projectId.
   * Also sets the projectId in the hours to projectId.
   * @param projId The projectId to be set.
   */
  public void setProjectId(Integer projId) {
    projectId = projId;
    for (Hours hrs : hours) {
      hrs.setProjectId(projectId);
    }
  }
  
  /**
   * Returns the workpackageId, or initializes the workPackageId
   * to the value of the hours stored in this TimesheetRow.
   */
  public Integer getWorkPackageId() {
    if (workPackageId == 0 && getHours().size() > 0) {
      workPackageId = getHours().get(0).getWorkPackageId();
    }
    return workPackageId;
  }
  
  /**
   * Sets the workPackageId to wpId.
   * Also sets the workPackageId in the hours to workPackageId.
   * @param wpId The workPackageId to be set.
   */
  public void setWorkPackageId(Integer wpId) {
    workPackageId = wpId;
    for (Hours hrs : hours) {
      hrs.setWorkPackageId(workPackageId);
    }
  }
  
  /**
   * Returns timesheetRowId.
   * @return the timesheetRowId
   */
  public int getTimesheetRowId() {
    return timesheetRowId;
  }
  
  /**
   * Set timesheetRowId to timesheetRowId.
   * @param timesheetRowId the timesheetRowId to set
   */
  public void setTimesheetRowId(int timesheetRowId) {
    this.timesheetRowId = timesheetRowId;
  }
  
  /**
   * Returns project.
   * @return the project
   */
  /*public Project getProject() {
    return project;
  }*/
  
  /**
   * Sets project to project.
   * @param project the project to set
   */
  /*public void setProject(Project project) {
    this.project = project;
  }*/
  
  /**
   * Returns timesheet.
   * @return the timesheet
   */
  public Timesheet getTimesheet() {
    return timesheet;
  }
  
  /**
   * Sets timesheet to timesheet.
   * @param timesheet the timesheet to set
   */
  public void setTimesheet(Timesheet timesheet) {
    this.timesheet = timesheet;
  }
  
  /**
   * Returns workpackage.
   * @return the workPackage
   */
  /*public WorkPackage getWorkPackage() {
    return workPackage;
  }*/
  
  /**
   * Sets workpackage to workpackage.
   * @param workPackage the workPackage to set
   */
  /*public void setWorkPackage(WorkPackage workPackage) {
    this.workPackage = workPackage;
  }*/
  
  /**
   * Returns hours.
   * @return the hours
   */
  public List<Hours> getHours() {
    return hours;
  }
  
  /**
   * Sets hours to hours.
   * @param hours the hours to set
   */
  public void setHours(List<Hours> hours) {
    this.hours = hours;
  }
  
  public String getNote() {
    return note;
  }
  
  public void setNote(String note) {
    this.note = note;
  }
  
}
