package com.diploma.qoldan.exception.product;

public class ProductIsNotActiveException extends Exception {
    public ProductIsNotActiveException(String error) {
        super("The product is not active and cannot be updated\n" + error);
    }
}
