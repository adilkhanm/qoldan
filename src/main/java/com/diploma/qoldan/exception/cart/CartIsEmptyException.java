package com.diploma.qoldan.exception.cart;

public class CartIsEmptyException extends Exception {
    public CartIsEmptyException(String s) {
        super("Cart is empty\n" + s);
    }
}
