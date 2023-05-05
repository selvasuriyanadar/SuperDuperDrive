package com.udacity.jwdnd.course1.cloudstorage.encryption.service;

import com.udacity.jwdnd.course1.cloudstorage.encryption.model.EncryptedData;

import static selva.oss.lang.Commons.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class EncryptionService {

    private Logger logger = LoggerFactory.getLogger(EncryptionService.class);

    public static class CouldNotEncryptData extends RuntimeException {
    }

    public EncryptedData encryptData(String data, int key_length) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[key_length];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);

        return new EncryptedData(encodedKey, encryptData(data, encodedKey));
    }

    public String encryptData(String data, String key) {
        validateNotNull(data);
        validateNotNull(key);
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedData = cipher.doFinal(data.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
            logger.error(e.getMessage());
            throw new CouldNotEncryptData();
        }
    }

    public static class CouldNotDecryptData extends RuntimeException {
    }

    public String decryptData(String data, String key) {
        validateNotNull(data);
        validateNotNull(key);
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(data));
            return new String(decryptedData);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            logger.error(e.getMessage());
            throw new CouldNotDecryptData();
        }
    }

}
