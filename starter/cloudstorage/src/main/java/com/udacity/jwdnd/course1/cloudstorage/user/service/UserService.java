package com.udacity.jwdnd.course1.cloudstorage.user.service;

import com.udacity.jwdnd.course1.cloudstorage.user.model.User;
import com.udacity.jwdnd.course1.cloudstorage.user.model.SaltAndHash;
import com.udacity.jwdnd.course1.cloudstorage.user.model.UserForm;
import com.udacity.jwdnd.course1.cloudstorage.user.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.user.logic.UserLogic;

import static selva.oss.lang.CommonValidations.*;

import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Service
public class UserService {

    private HashService hashService;
    private UserMapper userMapper;

    public UserService(HashService hashService, UserMapper userMapper) {
        this.hashService = hashService;
        this.userMapper = userMapper;
    }

    public int createUser(UserForm form) {
        validateNotNull(form.getPassword(), "Password");
        final User user = form.getUser(hashService.getSaltedHashedValue(form.getPassword(), 16));

        UserLogic.validate(user, u -> doesUserExist(u));
        return userMapper.insert(user);
    }

    public boolean doesUserExist(User user) {
        return userMapper.getUser(user.getUsername()).isPresent();
    }

    public static class UserNotLoggedInException extends RuntimeException {
    }

    public static class UnexpectedPrincipalTypeException extends RuntimeException {
    }

    public String getLoggedInUsername() {
        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal == null) {
            throw new UserNotLoggedInException();
        }

        if (principal instanceof String) {
            return (String) principal;
        }

        if (principal instanceof UserDetails) {
            final UserDetails userDetails = (UserDetails) principal;
            if (userDetails.getUsername() == null) {
                throw new UserNotLoggedInException();
            }

            return userDetails.getUsername();
        }

        throw new UnexpectedPrincipalTypeException();
    }

}
