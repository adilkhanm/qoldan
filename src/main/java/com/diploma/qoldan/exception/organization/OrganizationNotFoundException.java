package com.diploma.qoldan.exception.organization;

public class OrganizationNotFoundException extends Exception {
    public OrganizationNotFoundException(String s) {
        super("Organization was not found.\n" + s);
    }
}
