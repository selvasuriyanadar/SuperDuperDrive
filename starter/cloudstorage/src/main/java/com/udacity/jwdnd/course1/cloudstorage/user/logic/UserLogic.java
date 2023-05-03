package com.udacity.jwdnd.course1.cloudstorage.user.logic;

import com.udacity.jwdnd.course1.cloudstorage.user.model.User;

import static selva.oss.lang.CommonValidations.*;
import static selva.oss.lang.Commons.*;

import java.util.*;

public class UserLogic {

    public interface DoesUserExist {
        public boolean exist(User user);
    }

    public static void validate(User user, DoesUserExist doesUserExist) {
        validateNotNull(user.getUsername(), "Username");
        validateNotNull(user.getSalt(), "Salt");
        validateNotNull(user.getPassword(), "Password");
        validateNotNull(user.getFirstName(), "First Name");
        validateNotNull(user.getLastName(), "Last Name");

        if (doesUserExist.exist(user)) {
            throw new InvalidStateException("The username already exists.");
        }
    }

}
