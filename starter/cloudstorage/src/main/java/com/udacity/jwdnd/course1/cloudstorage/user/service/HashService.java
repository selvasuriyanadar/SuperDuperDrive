package com.udacity.jwdnd.course1.cloudstorage.user.service;

import com.udacity.jwdnd.course1.cloudstorage.user.model.SaltAndHash;

import static selva.oss.lang.Commons.*;

import org.springframework.stereotype.Service;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import java.security.spec.*;
import java.util.*;

@Service
public class HashService {

    Logger logger = LoggerFactory.getLogger(HashService.class);

    public static class CouldNotHashException extends RuntimeException {
    }

    public SaltAndHash getSaltedHashedValue(String data, int salt_length) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[salt_length];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);

        return new SaltAndHash(encodedSalt, getHashedValue(data, encodedSalt));
    }

    public String getHashedValue(String data, String salt) {
        validateNotNull(data);
        validateNotNull(salt);
        try {
            final int iterCount = 12288;
            final int derivedKeyLength = 256;

            KeySpec spec = new PBEKeySpec(data.toCharArray(), salt.getBytes(), iterCount, derivedKeyLength * 8);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            byte[] hashedValue = factory.generateSecret(spec).getEncoded();

            return Base64.getEncoder().encodeToString(hashedValue);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {

            logger.error(e.getMessage());
            throw new CouldNotHashException();
        }
    }

}
