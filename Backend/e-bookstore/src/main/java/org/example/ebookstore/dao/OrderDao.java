package org.example.ebookstore.dao;

import org.example.ebookstore.entity.Order;
import org.example.ebookstore.entity.OrderItem;
import org.example.ebookstore.entity.Statistics;
import org.example.ebookstore.entity.UserListItem;

import java.time.LocalDate;
import java.util.List;

public interface OrderDao {
    List<Order> selectByUserId(Integer userId);

    void insertOrder(Order order);

    Statistics getStatistics(Integer userId, Integer time);

    List<UserListItem> getUserList(Integer time);

    List<OrderItem> getBookList(Integer time);

    List<Order> getSelectedOrders(Integer userId, LocalDate startTime, LocalDate endTime, String title);

    List<Order> getAllSelectedOrders(LocalDate startTime, LocalDate endTime, String title);

    List<Order> getOrdersByTitle(Integer userId, String title);

    List<Order> getAllOrdersByTitle(String title);

    List<Order> getAllOrders();
}
