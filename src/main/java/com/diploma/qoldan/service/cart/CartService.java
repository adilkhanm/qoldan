package com.diploma.qoldan.service.cart;

import com.diploma.qoldan.dto.cart.CartResponseDto;
import com.diploma.qoldan.dto.product.ProductShortResponseDto;
import com.diploma.qoldan.enums.ProductStatusEnum;
import com.diploma.qoldan.exception.cart.CartIsEmptyException;
import com.diploma.qoldan.exception.cart.CartNotFoundException;
import com.diploma.qoldan.exception.cart.CartProductExistsException;
import com.diploma.qoldan.exception.cart.CartProductNotFoundException;
import com.diploma.qoldan.exception.product.ProductIsNotAvailableException;
import com.diploma.qoldan.exception.product.ProductNotFoundException;
import com.diploma.qoldan.mapper.cart.CartMapper;
import com.diploma.qoldan.mapper.product.ProductMapper;
import com.diploma.qoldan.model.cart.Cart;
import com.diploma.qoldan.model.cart.CartProduct;
import com.diploma.qoldan.model.product.Product;
import com.diploma.qoldan.model.user.User;
import com.diploma.qoldan.repository.cart.CartProductRepo;
import com.diploma.qoldan.repository.cart.CartRepo;
import com.diploma.qoldan.service.product.ProductSimpleService;
import com.diploma.qoldan.service.user.UserSimpleService;
import com.diploma.qoldan.service.wishlist.WishlistSimpleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepo repo;
    private final CartProductRepo cartProductRepo;

    private final CartMapper mapper;
    private final ProductMapper productMapper;

    private final CartSimpleService service;
    private final ProductSimpleService productService;
    private final WishlistSimpleService wishlistService;
    private final UserSimpleService userService;

    public CartResponseDto getUsersCart(String username, Integer limit, Integer offset)
            throws CartNotFoundException {
        User user = userService.findUserByUsername(username);

        Cart cart = service.findCartByUser(user);
        Stream<CartProduct> cartStream = cart.getCartProducts().stream();
        if (offset != null)
            cartStream = cartStream.skip(offset);
        if (limit != null)
            cartStream = cartStream.limit(limit);

        List<ProductShortResponseDto> productDtoList = cartStream
                .map(CartProduct::getProduct)
                .map(product -> productMapper.mapProductToShortResponse(
                        product,
                        wishlistService.checkProductInWishlist(user, product),
                        true))
                .toList();

        return mapper.mapCartAndProductsToResponse(cart, productDtoList);
    }

    @Transactional
    public void addProductToCart(String username, Long productId)
            throws ProductNotFoundException, CartProductExistsException, CartNotFoundException, ProductIsNotAvailableException {
        Product product = productService.findProductById(productId);
        if (!product.getStatus().equals(ProductStatusEnum.ACTIVE.toString()))
            throw new ProductIsNotAvailableException("");

        User user = userService.findUserByUsername(username);
        Cart cart = service.findCartByUser(user);

        CartProduct cartProduct = cartProductRepo.findByCartAndProduct(cart, product);
        if (cartProduct != null)
            throw new CartProductExistsException("");

        cartProduct = CartProduct.builder()
                .cart(cart)
                .product(product)
                .build();

        service.addProductToCart(cartProduct);
    }

    @Transactional
    public void deleteProductFromCart(String username, Long productId)
            throws ProductNotFoundException, CartProductNotFoundException, CartNotFoundException {
        User user = userService.findUserByUsername(username);
        Product product = productService.findProductById(productId);
        Cart cart = service.findCartByUser(user);
        CartProduct cartProduct = service.findCartProductByCartAndProduct(cart, product);
        service.deleteProductFromCart(cartProduct);
    }

    @Transactional
    public void bookProducts(String username)
            throws CartIsEmptyException, CartNotFoundException, ProductIsNotAvailableException {
        User user = userService.findUserByUsername(username);
        Cart cart = service.findCartByUser(user);
        if (CollectionUtils.isEmpty(cart.getCartProducts()))
            throw new CartIsEmptyException("");

        for (CartProduct cartProduct : cart.getCartProducts()) {
            Product product = cartProduct.getProduct();
            if (!product.getStatus().equals(ProductStatusEnum.ACTIVE.toString()))
                throw new ProductIsNotAvailableException("Some product is not active!\nProduct title: " + product.getItem().getTitle());
        }

        for (CartProduct cartProduct : cart.getCartProducts()) {
            Product product = cartProduct.getProduct();
            productService.bookProduct(product);
        }
    }

    @Transactional
    public void unbookProducts(String username)
            throws CartNotFoundException, ProductIsNotAvailableException {
        User user = userService.findUserByUsername(username);
        Cart cart = service.findCartByUser(user);

        for (CartProduct cartProduct : cart.getCartProducts()) {
            Product product = cartProduct.getProduct();
            if (!product.getStatus().equals(ProductStatusEnum.BOOKED.toString()))
                throw new ProductIsNotAvailableException("Product is not booked!\nProduct title: " + product.getItem().getTitle());
        }

        for (CartProduct cartProduct : cart.getCartProducts()) {
            Product product = cartProduct.getProduct();
            productService.unbookProduct(product);
        }
    }
}
