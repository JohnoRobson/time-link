package com.timelink.services.interfaces;

import com.timelink.ejbs.Timesheet;

public interface TimesheetCopyServiceInterface {
  public Timesheet copyTimesheet(Timesheet original);
}
