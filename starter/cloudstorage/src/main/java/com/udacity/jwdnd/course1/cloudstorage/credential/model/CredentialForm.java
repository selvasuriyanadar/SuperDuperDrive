package com.udacity.jwdnd.course1.cloudstorage.credential.model;

import com.udacity.jwdnd.course1.cloudstorage.encryption.model.EncryptedData;

public class CredentialForm {

    private String url;
    private String userName;
    private String password;

    public Credential getCredential(EncryptedData encryptedData) {
        return transfer(new Credential(), encryptedData);
    }

    public Credential transfer(Credential credential, EncryptedData encryptedData) {
        credential.setUrl(getUrl());
        credential.setUserName(getUserName());
        credential.setEncryptionKey(encryptedData.getKey());
        credential.setPassword(encryptedData.getData());
        return credential;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
