package com.diploma.qoldan.exception.category;

public class CategoryNotFoundException extends Exception {
    public CategoryNotFoundException(String error) {
        super("Category is not found\n" + error);
    }
}
