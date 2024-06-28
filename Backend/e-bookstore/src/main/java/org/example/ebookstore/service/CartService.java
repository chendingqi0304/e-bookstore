package org.example.ebookstore.service;

import org.example.ebookstore.entity.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CartService {
    Page<Cart> getCarts(Integer userId, Pageable pageable);
    Cart getCartById(Integer cartId);
    void updateCartNumber(Integer cartId, Integer number);
    void deleteCartById(Integer cartId);
    boolean addCart(Cart cart);
}
