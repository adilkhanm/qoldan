package com.diploma.qoldan.exception.product;

public class ProductTypeNotFoundException extends Exception {
    public ProductTypeNotFoundException(String error) {
        super("Product type is not found\n" + error);
    }
}
