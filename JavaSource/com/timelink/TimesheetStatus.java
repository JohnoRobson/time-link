package com.timelink;

import java.io.Serializable;

public enum TimesheetStatus implements Serializable {
  NOTSUBMITTED, WAITINGFORAPPROVAL, APPROVED, REJECTED
}
