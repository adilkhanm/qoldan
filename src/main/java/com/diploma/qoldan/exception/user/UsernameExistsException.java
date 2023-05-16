package com.diploma.qoldan.exception.user;

public class UsernameExistsException extends Exception {
    public UsernameExistsException(String errorMessage) {
        super("User with such email already exists\n" + errorMessage);
    }
}
