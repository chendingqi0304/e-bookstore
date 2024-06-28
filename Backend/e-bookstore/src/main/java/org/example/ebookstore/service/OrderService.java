package org.example.ebookstore.service;

import org.example.ebookstore.entity.Order;
import org.example.ebookstore.entity.OrderItem;
import org.example.ebookstore.entity.UserListItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    Page<Order> getOrders(Integer userId, Pageable pageable);

    void addOrderByBookId(Integer bookId, Integer userId, Integer number);

    void addOrderByCartIds(List<Integer> cartIds, Integer userId);

    List<UserListItem> getUserList(Integer time);

    List<OrderItem> getBookList(Integer time);

    Page<Order> getSelectedOrders(Integer userId, LocalDate startTime, LocalDate endTime, String title, Pageable pageable);

    Page<Order> getOrdersByTitle(Integer userId, String title,Pageable pageable);

    Page<Order> getAllOrders(Pageable pageable);

    Page<Order> getAllSelectedOrders(LocalDate startDate, LocalDate endDate, String title,Pageable pageable);

    Page<Order> getAllOrdersByTitle(String title,Pageable pageable);
}
