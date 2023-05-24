package com.diploma.qoldan.exception.image;

public class ImageNotFoundException extends Exception {
    public ImageNotFoundException(String s) {
        super("Image not found\n" + s);
    }
}
