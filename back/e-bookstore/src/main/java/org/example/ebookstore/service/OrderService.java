package org.example.ebookstore.service;

import org.example.ebookstore.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getOrders(Integer userId);
    void addOrderByBookId(Integer bookId, Integer userId, Integer number);
    void addOrderByCartIds(List<Integer> cartIds, Integer userId);
}
