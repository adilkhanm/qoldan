package com.diploma.qoldan.exception.order;

public class OrderAlreadyConfirmedException extends Exception {
    public OrderAlreadyConfirmedException(String s) {
        super("Order was already confirmed\n" + s);
    }
}
