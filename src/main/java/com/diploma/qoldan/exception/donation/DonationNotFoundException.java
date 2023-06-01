package com.diploma.qoldan.exception.donation;

public class DonationNotFoundException extends Exception {
    public DonationNotFoundException(String s) {
        super("Donation was not found.\n" + s);
    }
}
