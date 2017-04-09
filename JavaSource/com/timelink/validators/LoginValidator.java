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
@Named("LoginValidator")
@SessionScoped
public class LoginValidator implements Serializable {
    
    public LoginValidator() {}


    /**
     * Validates the password.
     * @param context
     * @param componentToValidate
     * @param value the value to validate.
     * @throws ValidatorException
     */
    public void validatePassword(FacesContext context,
            UIComponent componentToValidate, Object value)
            throws ValidatorException {
        

        String password = (String) value;

        if (password == null || password.length() == 0) {
            throw new ValidatorException(
                    new FacesMessage("You must enter a password"));
        }

        if (password.length() < 2 || password.length() > 20) {
            throw new ValidatorException(
                    new FacesMessage("password must be between 2 "
                            + "and 20 characters"));
        }

        for (int i = 0; i < password.length(); i++) {
            if (!Character.isLetter(password.charAt(i))
                    && !Character.isDigit(password.charAt(i))
                    && password.charAt(i) != '_') {

                throw new ValidatorException(
                        new FacesMessage("Only alphabetic, numeric "
                                + "and \'_\' characters are "
                                + "allowed in password"));
            }
        }
    }

    /**
     * Validates the username.
     * @param context
     * @param componentToValidate
     * @param value the value to validate.
     * @throws ValidatorException
     */
    public void validateUsername(FacesContext context,
            UIComponent componentToValidate, Object value)
            throws ValidatorException {

        String username = (String) value;

        if (username == null || username.length() == 0) {
            throw new ValidatorException(
                    new FacesMessage("You must enter a username"));
        }

        if (username.length() < 2 || username.length() > 20) {
            throw new ValidatorException(
                    new FacesMessage("username must"
                            + " be between 2 and 20 characters"));
        }

        for (int i = 0; i < username.length(); i++) {
            if (!Character.isLetter(username.charAt(i))
                    && !Character.isDigit(username.charAt(i))
                    && username.charAt(i) != '_') {
                throw new ValidatorException(
                        new FacesMessage("Only alphabetic, "
                                + "numeric and \'_\' characters "
                                + "are allowed in username"));
            }
        }

    }


}