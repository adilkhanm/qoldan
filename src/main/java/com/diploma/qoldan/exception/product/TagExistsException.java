package com.diploma.qoldan.exception.product;

public class TagExistsException extends Exception {
    public TagExistsException(String errorMessage) {
        super("The tag already exists\n" + errorMessage);
    }
}
