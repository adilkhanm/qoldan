package com.diploma.qoldan.service.wishlist;

import com.diploma.qoldan.dto.product.ProductShortResponseDto;
import com.diploma.qoldan.exception.product.ProductNotFoundException;
import com.diploma.qoldan.exception.wishlist.WishlistExistsException;
import com.diploma.qoldan.exception.wishlist.WishlistNotFoundException;
import com.diploma.qoldan.mapper.product.ProductMapper;
import com.diploma.qoldan.model.product.Product;
import com.diploma.qoldan.model.user.User;
import com.diploma.qoldan.model.wishlist.Wishlist;
import com.diploma.qoldan.repository.wishlist.WishlistRepo;
import com.diploma.qoldan.service.cart.CartSimpleService;
import com.diploma.qoldan.service.user.UserService;
import com.diploma.qoldan.service.product.ProductSimpleService;
import com.diploma.qoldan.service.user.UserSimpleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepo repo;

    private final ProductMapper productMapper;

    private final WishlistSimpleService service;
    private final ProductSimpleService productService;
    private final UserSimpleService userService;
    private final CartSimpleService cartService;

    public List<ProductShortResponseDto> getUsersWishlist(String username, Integer limit, Integer offset) {
        User user = userService.findUserByUsername(username);

        List<Wishlist> wishlist = repo.findAllByUser(user);
        Stream<Wishlist> wishlistStream = wishlist.stream();
        if (offset != null)
            wishlistStream = wishlistStream.skip(offset);
        if (limit != null)
            wishlistStream = wishlistStream.limit(limit);

        return wishlistStream
                .map(Wishlist::getProduct)
                .map(product -> productMapper.mapProductToShortResponse(
                        product,
                        true,
                        cartService.checkProductInCart(user, product)))
                .toList();
    }

    @Transactional
    public void addProductToWishlist(String username, Long productId)
            throws ProductNotFoundException, WishlistExistsException {
        Product product = productService.findProductById(productId);
        User user = userService.findUserByUsername(username);

        Wishlist wishlist = repo.findByUserAndProduct(user, product);
        if (wishlist != null)
            throw new WishlistExistsException("");

        wishlist = Wishlist.builder()
                .user(user)
                .product(product)
                .build();
        repo.save(wishlist);
    }

    @Transactional
    public void deleteProductFromWishlist(String username, Long productId)
            throws ProductNotFoundException, WishlistNotFoundException {
        Product product = productService.findProductById(productId);
        User user = userService.findUserByUsername(username);
        Wishlist wishlist = service.findWishlistByUserAndProduct(user, product);
        repo.delete(wishlist);
    }

}
