package com.diploma.qoldan.exception.donation;

public class DonationStatusNotFound extends Exception {
    public DonationStatusNotFound(String s) {
        super("There is no such donation status.\n" + s);
    }
}
