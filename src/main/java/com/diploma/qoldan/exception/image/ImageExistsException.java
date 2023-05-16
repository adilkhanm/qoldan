package com.diploma.qoldan.exception.image;

public class ImageExistsException extends Exception {
    public ImageExistsException(String error) {
        super("The image with such url already exists\n" + error);
    }
}
