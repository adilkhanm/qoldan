package com.diploma.qoldan.exception.wishlist;

public class WishlistExistsException extends Exception {
    public WishlistExistsException(String s) {
        super("The product is already in wishlist\n" + s);
    }
}
