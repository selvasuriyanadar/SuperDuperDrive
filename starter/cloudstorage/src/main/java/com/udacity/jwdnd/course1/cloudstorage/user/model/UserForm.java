package com.udacity.jwdnd.course1.cloudstorage.user.model;

import com.udacity.jwdnd.course1.cloudstorage.hash.model.SaltAndHash;

public class UserForm {

    private String username;
    private String password;
    private String firstName;
    private String lastName;

    public User getUser(SaltAndHash saltAndHash) {
        User user = new User();
        user.setUsername(getUsername());
        user.setFirstName(getFirstName());
        user.setLastName(getLastName());
        user.setSalt(saltAndHash.getSalt());
        user.setPassword(saltAndHash.getHash());
        return user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
