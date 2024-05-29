package org.example.ebookstore.service;

import org.example.ebookstore.entity.Cart;

import java.util.List;

public interface CartService {
    List<Cart> getCarts(Integer userId);
    Cart getCartById(Integer cartId);
    void updateCartNumber(Integer cartId, Integer number);
    void deleteCartById(Integer cartId);
    boolean addCart(Cart cart);
}
