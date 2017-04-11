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
@Named("HrValidator")
@SessionScoped
public class HrValidator implements Serializable {
    
    public HrValidator() {}


    /**
     * Validates the password.
     * @param context
     * @param componentToValidate
     * @param value the value to validate.
     * @throws ValidatorException
     */
    public void validateUserId(FacesContext context,
            UIComponent componentToValidate, Object value)
            throws ValidatorException {
        

        String userId = (String) value;

        if (userId == null || userId.length() == 0) {
            throw new ValidatorException(
                    new FacesMessage("You must enter a user id"));
        }

        for(int i = 0; i < userId.length(); i++) {
            if (!Character.isLetter(userId.charAt(i))
                    && !Character.isDigit(userId.charAt(i))) {

                throw new ValidatorException(
                        new FacesMessage("Only alphabetic, numeric "
                                + "allowed in user id"));
            }
        }
    }

    public void validateName(FacesContext context,
            UIComponent componentToValidate, Object value)
            throws ValidatorException {

        String  name = (String) value;

        if (name == null || name.length() == 0) {
            throw new ValidatorException(
                    new FacesMessage("You must enter a name"));
        }

        if (name.length() < 1 || name.length() > 30) {
            throw new ValidatorException(
                    new FacesMessage("name must"
                            + " be between 1 and 30 characters"));
        }
    }
    
    public void validateEmail(FacesContext context,
            UIComponent componentToValidate, Object value)
            throws ValidatorException {

        String email = (String) value;

        if (email == null || email.length() == 0) {
            throw new ValidatorException(
                    new FacesMessage("You must enter a name"));
        }

        if (email.length() < 2) {
            throw new ValidatorException(
                    new FacesMessage("enter a valid email"));
        }
        
        boolean found = false;
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@') {
                found = true;
                break;
            }
        }

        if (!found) {
            throw new ValidatorException(
                    new FacesMessage("enter a valid email"));
            
        }
    }


    public void validateVacationAccrual(FacesContext context,
            UIComponent componentToValidate, Object value)
            throws ValidatorException {

        Float vac = (Float) value;

        if (vac < 0 || vac > Double.MAX_VALUE) {
            throw new ValidatorException(
                    new FacesMessage("vacation must"
                            + " be between 1 and " + Integer.MAX_VALUE));
        }
    }

    public void validatePassword(FacesContext context,
            UIComponent componentToValidate, Object value)
            throws ValidatorException {
        String  password = (String) value;

        if (password == null || password.length() == 0) {
            throw new ValidatorException(
                    new FacesMessage("You must enter a password"));
        }

        if (password.length() < 2 || password.length() > 30) {
            throw new ValidatorException(
                    new FacesMessage("password must be between 2 and 30 characters"));
        }

    }

    
    
    
}