package com.diploma.qoldan.service.cart;

import com.diploma.qoldan.exception.cart.CartNotFoundException;
import com.diploma.qoldan.exception.cart.CartProductNotFoundException;
import com.diploma.qoldan.model.cart.Cart;
import com.diploma.qoldan.model.cart.CartProduct;
import com.diploma.qoldan.model.product.Product;
import com.diploma.qoldan.model.user.User;
import com.diploma.qoldan.repository.cart.CartProductRepo;
import com.diploma.qoldan.repository.cart.CartRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CartSimpleService {

    private final CartRepo repo;
    private final CartProductRepo cartProductRepo;

    @Transactional
    public void createCartForNewUser(User user) {
        Cart cart = Cart.builder()
                .user(user)
                .total(0)
                .totalProducts(0)
                .build();
        repo.save(cart);
    }

    public Cart findCartByUser(User user) throws CartNotFoundException {
        Cart cart = repo.findByUser(user);
        if (cart == null)
            throw new CartNotFoundException("");
        return cart;
    }

    public CartProduct findCartProductByCartAndProduct(Cart cart, Product product) throws CartProductNotFoundException {
        CartProduct cartProduct = cartProductRepo.findByCartAndProduct(cart, product);
        if (cartProduct == null)
            throw new CartProductNotFoundException("");
        return cartProduct;
    }

    public boolean checkProductInCart(User user, Product product) {
        if (user == null)
            return false;

        try {
            Cart cart = findCartByUser(user);
            findCartProductByCartAndProduct(cart, product);
            return true;
        } catch (CartNotFoundException | CartProductNotFoundException e) {
            return false;
        }
    }

    @Transactional
    public void emptyCartOfUser(User user) {
        Cart cart = repo.findByUser(user);
        cart.setTotalProducts(0);
        cart.setTotal(0);
        cartProductRepo.deleteAllByCart(cart);
    }
}
