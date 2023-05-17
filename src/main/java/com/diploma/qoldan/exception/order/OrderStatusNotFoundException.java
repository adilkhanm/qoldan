package com.diploma.qoldan.exception.order;

public class OrderStatusNotFoundException extends Exception {
    public OrderStatusNotFoundException(String s) {
        super("Order status is not found\n" + s);
    }
}
