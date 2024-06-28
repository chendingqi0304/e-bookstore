package org.example.ebookstore.dao.impl;

import org.example.ebookstore.dao.CartDao;
import org.example.ebookstore.entity.Book;
import org.example.ebookstore.entity.Cart;
import org.example.ebookstore.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CartDaoImpl implements CartDao {
    @Autowired
    private CartRepository cartRepository;
    @Override
    public Page<Cart> selectByUserId(Integer userId, Pageable pageable){
        return cartRepository.findByUserIdAndBook_Deleted(userId,false,pageable);
    }

    @Override
    public Cart selectBycartId(Integer cartId){
        return cartRepository.findByCartId(cartId);
    }
    @Override
    public void updateBycartId(Integer cartId, Integer number){
        Cart cart = cartRepository.findByCartId(cartId);
        cart.setNumber(number);
        cartRepository.save(cart);
    }
    @Override
    public void insert(Cart cart){
        cartRepository.save(cart);
    }
    @Override
    public Cart findByBookAndUserId(Book book, Integer userId){

        return cartRepository.findByBookAndUserId(book,userId);
    }
    @Override
    public void deleteBycartId(Integer cartId){
        cartRepository.deleteByCartId(cartId);
    }
}
