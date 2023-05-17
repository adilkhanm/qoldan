package com.diploma.qoldan.exception.user;

public class UserAddressNotFoundException extends Exception {
    public UserAddressNotFoundException(String s) {
        super("Address of the user was not found\n" + s);
    }
}
