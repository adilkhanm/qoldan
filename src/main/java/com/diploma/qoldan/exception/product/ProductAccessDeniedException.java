package com.diploma.qoldan.exception.product;

public class ProductAccessDeniedException extends Exception {
    public ProductAccessDeniedException(String s) {
        super("Access denied to the product\n" + s);
    }
}
