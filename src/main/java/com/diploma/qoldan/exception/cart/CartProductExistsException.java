package com.diploma.qoldan.exception.cart;

public class CartProductExistsException extends Exception {
    public CartProductExistsException(String s) {
        super("The product is already in cart\n" + s);
    }
}
