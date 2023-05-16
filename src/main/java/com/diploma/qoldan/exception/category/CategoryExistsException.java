package com.diploma.qoldan.exception.category;

public class CategoryExistsException extends Exception {
    public CategoryExistsException(String error) {
        super("The category already exists\n" + error);
    }
}
