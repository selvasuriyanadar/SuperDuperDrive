package com.udacity.jwdnd.course1.cloudstorage.credential.service;

import com.udacity.jwdnd.course1.cloudstorage.credential.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.credential.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.credential.model.CredentialResponse;
import com.udacity.jwdnd.course1.cloudstorage.encryption.service.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.credential.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.credential.logic.CredentialLogic;

import static selva.oss.lang.CommonValidations.*;
import static selva.oss.lang.Commons.*;

import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
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

    public Nothing editCredential(Integer userId, Integer credentialId, CredentialForm form) {
        Optional<Credential> credential = credentialMapper.getCredential(userId, credentialId);
        if (!credential.isPresent()) {
            throw new InvalidStateException("Could not find the credential.");
        }
        CredentialLogic.validatePassword(form);
        form.transfer(credential.get(), encryptionService.encryptData(form.getPassword(), CredentialLogic.getEncryptionKeyLength()));
        CredentialLogic.validate(credential.get());
        credentialMapper.update(credential.get());
        return new Nothing();
    }

    public Nothing deleteCredential(Integer userId, Integer credentialId) {
        Optional<Credential> credential = credentialMapper.getCredential(userId, credentialId);
        if (!credential.isPresent()) {
            throw new InvalidStateException("Could not find the credential.");
        }
        credentialMapper.delete(userId, credentialId);
        return new Nothing();
    }

    public List<CredentialResponse> getCredentials(Integer userId) {
        return credentialMapper.getCredentials(userId).stream().map(credential -> new CredentialResponse(credential, encryptionService.decryptData(credential.getPassword(), credential.getEncryptionKey()))).collect(Collectors.toList());
    }

}
