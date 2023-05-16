package com.diploma.qoldan.exception.product;

public class TagNotFoundException extends Exception {
    public TagNotFoundException(String error) {
        super("Tag is not found\n" + error);
    }
}
