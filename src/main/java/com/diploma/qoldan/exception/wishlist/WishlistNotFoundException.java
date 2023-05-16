package com.diploma.qoldan.exception.wishlist;

public class WishlistNotFoundException extends Throwable {
    public WishlistNotFoundException(String s) {
        super("Wishlist item was not found\n" + s);
    }
}
