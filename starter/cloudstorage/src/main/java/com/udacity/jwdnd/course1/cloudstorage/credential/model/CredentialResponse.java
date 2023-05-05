package com.udacity.jwdnd.course1.cloudstorage.credential.model;

public class CredentialResponse {

    private Integer credentialId;
    private String url;
    private String userName;
    private String password;
    private String decryptedPassword;

    public CredentialResponse() {
    }

    public CredentialResponse(Credential credential, String decryptedPassword) {
        this.credentialId = credential.getCredentialId();
        this.url = credential.getUrl();
        this.userName = credential.getUserName();
        this.password = credential.getPassword();
        this.decryptedPassword = decryptedPassword;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
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

    public String getDecryptedPassword() {
        return decryptedPassword;
    }

    public void setDecryptedPassword(String decryptedPassword) {
        this.decryptedPassword = decryptedPassword;
    }

}
