package com.udacity.jwdnd.course1.cloudstorage.credential.logic;

import com.udacity.jwdnd.course1.cloudstorage.credential.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.credential.model.CredentialForm;

import static selva.oss.lang.CommonValidations.*;
import static selva.oss.lang.Commons.*;

import java.util.*;
import java.net.URL;
import java.net.MalformedURLException;

public class CredentialLogic {

    public static void validatePassword(CredentialForm form) {
        validateNotNull(form.getPassword(), "Password");
    }

    public static int getEncryptionKeyLength() {
        return 16;
    }

    private static void validateUrl(Credential credential) {
        try {
            new URL(credential.getUrl());
        }
        catch (MalformedURLException e) {
            throw new InvalidStateException("Url is not valid.");
        }
    }

    public static void validate(Credential credential) {
        validateNotNull(credential.getUrl(), "Url");
        validateNotNull(credential.getUserName(), "User Name");

        validateUrl(credential);
    }

}
