package com.diploma.qoldan.exception.order;

import jakarta.validation.constraints.NotBlank;

public class OrderRowNotFoundException extends Exception {
    public OrderRowNotFoundException(@NotBlank String s) {
        super("Order row was not found\n" + s);
    }
}
