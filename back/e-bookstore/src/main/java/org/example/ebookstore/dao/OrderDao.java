package org.example.ebookstore.dao;

import org.example.ebookstore.entity.Order;

import java.util.List;

public interface OrderDao {
    List<Order> selectByUserId(Integer userId);
    void insertOrder(Order order);
}
