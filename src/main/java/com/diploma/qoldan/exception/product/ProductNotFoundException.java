package com.diploma.qoldan.exception.product;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String error) {
        super("Product is not found\n" + error);
    }
}
