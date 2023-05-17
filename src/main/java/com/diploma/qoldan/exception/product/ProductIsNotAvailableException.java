package com.diploma.qoldan.exception.product;

public class ProductIsNotAvailableException extends Exception {
    public ProductIsNotAvailableException(String s) {
        super("Product is not available anymore\n" + s);
    }
}
