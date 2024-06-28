package org.example.ebookstore.dao;

import org.example.ebookstore.entity.Order;
import org.example.ebookstore.entity.OrderItem;
import org.example.ebookstore.entity.Statistics;
import org.example.ebookstore.entity.UserListItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface OrderDao {
    Page<Order> selectByUserId(Integer userId, Pageable pageable);

    void insertOrder(Order order);

    Statistics getStatistics(Integer userId, Integer time);

    List<UserListItem> getUserList(Integer time);

    List<OrderItem> getBookList(Integer time);

    Page<Order> getSelectedOrders(Integer userId, LocalDate startTime, LocalDate endTime, String title,Pageable pageable);

    Page<Order> getAllSelectedOrders(LocalDate startTime, LocalDate endTime, String title,Pageable pageable);

    Page<Order> getOrdersByTitle(Integer userId, String title,Pageable pageable);

    Page<Order> getAllOrdersByTitle(String title,Pageable pageable);

    Page<Order> getAllOrders(Pageable pageable);
}
