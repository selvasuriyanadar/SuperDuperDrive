package com.udacity.jwdnd.course1.cloudstorage.user.logic;

import com.udacity.jwdnd.course1.cloudstorage.user.model.User;
import com.udacity.jwdnd.course1.cloudstorage.user.model.UserForm;

import static selva.oss.lang.CommonValidations.*;
import static selva.oss.lang.Commons.*;

import java.util.*;

public class UserLogic {

    public static int getSaltLength() {
        return 16;
    }

    public interface DoesUserExist {
        public boolean exist(String userName);
    }

    public static void validatePassword(UserForm form) {
        validateNotNull(form.getPassword(), "Password");

        if (form.getPassword().length() < 5) {
            throw new InvalidStateException("Password cannot be lesser than five characters.");
        }
    }

    public static void validate(User user, DoesUserExist doesUserExist) {
        validateNotNull(user.getUsername(), "Username");
        validateNotNull(user.getFirstName(), "First Name");
        validateNotNull(user.getLastName(), "Last Name");

        if (doesUserExist.exist(user.getUsername())) {
            throw new InvalidStateException("The username already exists.");
        }
    }

}
