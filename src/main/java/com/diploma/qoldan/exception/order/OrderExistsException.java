package com.diploma.qoldan.exception.order;

public class OrderExistsException extends Exception {
    public OrderExistsException(String s) {
        super("Active order is already created and waiting for approval\n" + s);
    }
}
