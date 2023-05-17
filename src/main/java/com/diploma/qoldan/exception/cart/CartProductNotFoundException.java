package com.diploma.qoldan.exception.cart;

public class CartProductNotFoundException extends Exception {
    public CartProductNotFoundException(String s) {
        super("Cart item was not found\n" + s);
    }
}
