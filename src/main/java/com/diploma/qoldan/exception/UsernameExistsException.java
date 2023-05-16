package com.diploma.qoldan.exception;

public class UsernameExistsException extends Exception {
    public UsernameExistsException(String errorMessage) {
        super(errorMessage);
    }
}
