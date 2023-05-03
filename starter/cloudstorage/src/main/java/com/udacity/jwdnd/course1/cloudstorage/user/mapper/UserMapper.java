package com.udacity.jwdnd.course1.cloudstorage.user.mapper;

import com.udacity.jwdnd.course1.cloudstorage.user.model.User;

import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    Optional<User> getUser(String username);

    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(User user);
}
