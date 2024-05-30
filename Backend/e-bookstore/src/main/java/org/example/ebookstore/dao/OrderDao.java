package org.example.ebookstore.dao;

import org.example.ebookstore.entity.Order;
import org.example.ebookstore.entity.OrderItem;
import org.example.ebookstore.entity.Statistics;
import org.example.ebookstore.entity.UserListItem;

import java.util.List;

public interface OrderDao {
    List<Order> selectByUserId(Integer userId);
    void insertOrder(Order order);

    Statistics getStatistics(Integer userId,Integer time);

    List<UserListItem> getUserList(Integer time);

    List<OrderItem> getBookList(Integer time);
}
