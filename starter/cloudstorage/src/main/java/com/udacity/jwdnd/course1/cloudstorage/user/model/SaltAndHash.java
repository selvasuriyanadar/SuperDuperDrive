package com.udacity.jwdnd.course1.cloudstorage.user.model;

public class SaltAndHash {

    private String salt;
    private String hash;

    public SaltAndHash(String salt, String hash) {
        this.salt = salt;
        this.hash = hash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

}
