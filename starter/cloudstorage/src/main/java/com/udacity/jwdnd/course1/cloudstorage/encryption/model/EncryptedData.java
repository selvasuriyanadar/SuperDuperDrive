package com.udacity.jwdnd.course1.cloudstorage.encryption.model;

import static selva.oss.lang.Commons.*;

public class EncryptedData {

    private String key;
    private String data;

    public EncryptedData(String key, String data) {
        validateNotNull(key);
        validateNotNull(data);
        this.key = key;
        this.data = data;
    }

    public String getKey() {
        return key;
    }

    public String getData() {
        return data;
    }

}
