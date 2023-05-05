package com.udacity.jwdnd.course1.cloudstorage.user.service;

import com.udacity.jwdnd.course1.cloudstorage.user.model.User;
import com.udacity.jwdnd.course1.cloudstorage.hash.service.HashService;
import com.udacity.jwdnd.course1.cloudstorage.user.mapper.UserMapper;

import org.springframework.stereotype.Service;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.Authentication;

import java.util.*;

@Service
public class AuthenticationService implements AuthenticationProvider {

    private UserMapper userMapper;
    private HashService hashService;

    public AuthenticationService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public static class AuthenticationFailedException extends AuthenticationException {
        public AuthenticationFailedException() {
            super("Failed to authenticate User. User Name or Password is not correct.");
        }
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();

        final Optional<User> user = userMapper.getUser(username);
        if (!user.isPresent()) {
            throw new AuthenticationFailedException();
        }

        final String hashedPassword = hashService.getHashedValue(password, user.get().getSalt());
        if (!user.get().getPassword().equals(hashedPassword)) {
            throw new AuthenticationFailedException();
        }

        return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
