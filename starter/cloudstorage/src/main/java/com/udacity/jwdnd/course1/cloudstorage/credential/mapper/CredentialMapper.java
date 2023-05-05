package com.udacity.jwdnd.course1.cloudstorage.credential.mapper;

import com.udacity.jwdnd.course1.cloudstorage.credential.model.Credential;

import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface CredentialMapper {

    @Insert("INSERT INTO CREDENTIALS (url, username, encryptionkey, password, userid) VALUES(#{url}, #{userName}, #{encryptionKey}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId} and credentialid = #{credentialId}")
    Optional<Credential> getCredential(Integer userId, Integer credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credential> getCredentials(Integer userId);

}
