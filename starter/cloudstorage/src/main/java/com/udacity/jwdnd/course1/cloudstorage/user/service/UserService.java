package com.udacity.jwdnd.course1.cloudstorage.user.service;

import com.udacity.jwdnd.course1.cloudstorage.hash.model.SaltAndHash;
import com.udacity.jwdnd.course1.cloudstorage.user.model.User;
import com.udacity.jwdnd.course1.cloudstorage.user.model.UserForm;
import com.udacity.jwdnd.course1.cloudstorage.hash.service.HashService;
import com.udacity.jwdnd.course1.cloudstorage.user.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.user.logic.UserLogic;

import static selva.oss.lang.CommonValidations.*;
import static selva.oss.lang.operation.CurdOps.*;
import selva.oss.lang.operation.OpsResult;

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
        UserLogic.validatePassword(form);
        final User user = form.getUser(hashService.getSaltedHashedValue(form.getPassword(), UserLogic.getSaltLength()));

        UserLogic.validate(user, userName -> doesUserExist(userName));
        return userMapper.insert(user);
    }

    public boolean doesUserExist(String userName) {
        return userMapper.getUser(userName).isPresent();
    }

    public interface InsertingForUser {
        public Integer insert(Integer userId);
    }

    public OpsResult insertForUser(String userName, InsertingForUser insertingForUser, String errorMessage) {
        return insert(() -> insertingForUser.insert(userMapper.getUser(userName).get().getUserId()), errorMessage);
    }

    public Integer getUserId(String userName) {
        Optional<User> user = userMapper.getUser(userName);
        if (!user.isPresent()) {
            throw new InvalidStateException("The user does not exist.");
        }
        return user.get().getUserId();
    }

    public static class UserNotLoggedInException extends RuntimeException {
    }

    public static class UnexpectedPrincipalTypeException extends RuntimeException {
    }

    public String getLoggedinUsername() {
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
