package com.timelink.validators;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;


/**
 * A class for validating input.
 */
@Named("TimesheetValidator")
@SessionScoped
public class TimesheetValidator implements Serializable {
    
    public TimesheetValidator() {}


    /**
     * Validates the password.
     * @param context
     * @param componentToValidate
     * @param value the value to validate.
     * @throws ValidatorException
     */
    public void validateAddTimesheetYear(FacesContext context,
            UIComponent componentToValidate, Object value)
            throws ValidatorException {
        
        Integer year = (Integer) value;

        if (year <= 0 || year > Integer.MAX_VALUE) {
            throw new ValidatorException(
                    new FacesMessage("Invalid year"));
        }

    }

    /**
     * Validates the username.
     * @param context
     * @param componentToValidate
     * @param value the value to validate.
     * @throws ValidatorException
     */
    public void validateAddTimesheetWeekNumber(FacesContext context,
            UIComponent componentToValidate, Object value)
            throws ValidatorException {

        Integer week = (Integer) value;


        if (week <= 0 || week > 52) {
            throw new ValidatorException(
                    new FacesMessage("Invalid week"));
        }


    }


}