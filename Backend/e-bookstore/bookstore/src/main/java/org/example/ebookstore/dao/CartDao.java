package org.example.ebookstore.dao;

import org.example.ebookstore.entity.Book;
import org.example.ebookstore.entity.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CartDao {
    Page<Cart> selectByUserId(Integer userId, Pageable pageable);
    Cart selectBycartId(Integer cartId);
    void updateBycartId(Integer cartId, Integer number);

    void insert(Cart cart);

    Cart findByBookAndUserId(Book book, Integer userId);

    void deleteBycartId(Integer cartId);
}
