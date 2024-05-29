package org.example.ebookstore.dao;

import org.example.ebookstore.entity.Book;
import org.example.ebookstore.entity.Cart;

import java.util.List;

public interface CartDao {
    List<Cart> selectByUserId(Integer userId);
    Cart selectBycartId(Integer cartId);
    void updateBycartId(Integer cartId, Integer number);

    void insert(Cart cart);

    Cart findByBookAndUserId(Book book, Integer userId);

    void deleteBycartId(Integer cartId);
}
