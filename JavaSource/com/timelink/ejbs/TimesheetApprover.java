package com.timelink.ejbs;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "ts_approver")
public class TimesheetApprover implements Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "tsa_id")
  private int timesheetApproverId;
  
  @Column(name = "tsa_appr_id")
  private int approverEmployeeId;
  
  @Column(name = "tsa_emp_id")
  private int approveeEmployeeId;
  
  public int getTimesheetApproverId() {
    return timesheetApproverId;
  }

  public void setTimesheetApproverId(int timesheetApproverId) {
    this.timesheetApproverId = timesheetApproverId;
  }

  public int getApproverEmployeeId() {
    return approverEmployeeId;
  }

  public void setApproverEmployeeId(int approverEmployeeId) {
    this.approverEmployeeId = approverEmployeeId;
  }

  public int getApproveeEmployeeId() {
    return approveeEmployeeId;
  }

  public void setApproveeEmployeeId(int approveeEmployeeId) {
    this.approveeEmployeeId = approveeEmployeeId;
  }
}
