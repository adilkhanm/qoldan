package com.diploma.qoldan.exception.product;

public class ProductTypeExistsException extends Exception {
    public ProductTypeExistsException(String error) {
        super("The product type already exists\n" + error);
    }
}
