package org.example.ebookstore.service.impl;

import org.example.ebookstore.dao.CartDao;
import org.example.ebookstore.entity.Cart;
import org.example.ebookstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;

    @Override
    public List<Cart> getCarts(Integer userId) {
        return cartDao.selectByUserId(userId);
    }

    @Override
    public Cart getCartById(Integer cartId) {
        return cartDao.selectBycartId(cartId);
    }

    @Override
    public void updateCartNumber(Integer cartId, Integer number) {
        cartDao.updateBycartId(cartId, number);
    }

    @Override
    public void deleteCartById(Integer cartId) {
        cartDao.deleteBycartId(cartId);
    }

    @Override
    public boolean addCart(Cart cart) {
        if(cartDao.findByBookAndUserId(cart.getBook(),cart.getUserId())!=null){
            return false;
        }
        cartDao.insert(cart);
        return true;
    }
}
