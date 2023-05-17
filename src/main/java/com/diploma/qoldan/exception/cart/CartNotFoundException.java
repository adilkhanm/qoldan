package com.diploma.qoldan.exception.cart;

public class CartNotFoundException extends Exception {
    public CartNotFoundException(String s) {
        super("Cart for the user is not found\n" + s);
    }
}
