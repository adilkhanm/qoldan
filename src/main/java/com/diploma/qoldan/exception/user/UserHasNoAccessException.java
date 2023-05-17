package com.diploma.qoldan.exception.user;

public class UserHasNoAccessException extends Exception {
    public UserHasNoAccessException(String s) {
        super("The user does not have access to this operation\n" + s);
    }
}
