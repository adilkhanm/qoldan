package com.diploma.qoldan.service.wishlist;

import com.diploma.qoldan.exception.wishlist.WishlistNotFoundException;
import com.diploma.qoldan.model.product.Product;
import com.diploma.qoldan.model.user.User;
import com.diploma.qoldan.model.wishlist.Wishlist;
import com.diploma.qoldan.repository.wishlist.WishlistRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WishlistSimpleService {

    private final WishlistRepo repo;

    public Wishlist findWishlistByUserAndProduct(User user, Product product) throws WishlistNotFoundException {
        Wishlist wishlist = repo.findByUserAndProduct(user, product);
        if (wishlist == null)
            throw new WishlistNotFoundException("");
        return wishlist;
    }

    public boolean checkProductInWishlist(User user, Product product) {
        if (user == null)
            return false;

        try {
            findWishlistByUserAndProduct(user, product);
            return true;
        } catch (WishlistNotFoundException e) {
            return false;
        }
    }

}
