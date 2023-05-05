package com.udacity.jwdnd.course1.cloudstorage.credential.service;

import com.udacity.jwdnd.course1.cloudstorage.credential.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.credential.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.encryption.service.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.credential.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.credential.logic.CredentialLogic;

import static selva.oss.lang.CommonValidations.*;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int createCredential(Integer userId, CredentialForm form) {
        CredentialLogic.validatePassword(form);
        Credential credential = form.getCredential(encryptionService.encryptData(form.getPassword(), CredentialLogic.getEncryptionKeyLength()));
        CredentialLogic.validate(credential);
        credential.setUserId(userId);
        return credentialMapper.insert(credential);
    }

}
