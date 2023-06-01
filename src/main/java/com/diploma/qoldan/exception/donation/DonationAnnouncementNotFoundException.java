package com.diploma.qoldan.exception.donation;

public class DonationAnnouncementNotFoundException extends Exception {
    public DonationAnnouncementNotFoundException(String s) {
        super("Donation announcement was not found.\n" + s);
    }
}
