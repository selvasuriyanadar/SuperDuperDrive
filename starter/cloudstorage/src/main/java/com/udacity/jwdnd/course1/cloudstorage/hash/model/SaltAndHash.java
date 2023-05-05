package com.udacity.jwdnd.course1.cloudstorage.hash.model;

import static selva.oss.lang.Commons.*;

public class SaltAndHash {

    private String salt;
    private String hash;

    public SaltAndHash(String salt, String hash) {
        validateNotNull(salt);
        validateNotNull(hash);
        this.salt = salt;
        this.hash = hash;
    }

    public String getSalt() {
        return salt;
    }

    public String getHash() {
        return hash;
    }

}
