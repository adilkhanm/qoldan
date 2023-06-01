package com.diploma.qoldan.exception.donation;

public class AnnouncementIsNotActiveException extends Exception {
    public AnnouncementIsNotActiveException(String s) {
        super("Announcement is not active.\n" + s);
    }
}
