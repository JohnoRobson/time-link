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
@Named("WorkPackageValidator")
@SessionScoped
public class WorkPackageValidator implements Serializable {
    
    public WorkPackageValidator() {}


    /**
     * Validates the password.
     * @param context
     * @param componentToValidate
     * @param value the value to validate.
     * @throws ValidatorException
     */
    public void validateWorkPackageNumber(FacesContext context,
            UIComponent componentToValidate, Object value)
            throws ValidatorException {
        

        String wpNumber = (String) value;

        if (wpNumber == null || wpNumber.length() == 0) {
            throw new ValidatorException(
                    new FacesMessage("You must enter a work package number"));
        }

        if (wpNumber.length() != 1) {
            throw new ValidatorException(
                    new FacesMessage("work package must be only 1 character"));
        }

        Character wpNumberAsChar = wpNumber.charAt(0);

            if (!Character.isLetter(wpNumberAsChar)
                    && !Character.isDigit(wpNumberAsChar)) {

                throw new ValidatorException(
                        new FacesMessage("Only alphabetic, numeric "
                                + "allowed in work package number"));
            }
    }

    /**
     * Validates the username.
     * @param context
     * @param componentToValidate
     * @param value the value to validate.
     * @throws ValidatorException
     */
    public void validateWorkPackageDescription(FacesContext context,
            UIComponent componentToValidate, Object value)
            throws ValidatorException {

        String description = (String) value;

        if (description == null || description.length() == 0) {
            throw new ValidatorException(
                    new FacesMessage("You must enter a description"));
        }

        if (description.length() < 1 || description.length() > 50) {
            throw new ValidatorException(
                    new FacesMessage("description must"
                            + " be between 1 and 50 characters"));
        }
    }


}