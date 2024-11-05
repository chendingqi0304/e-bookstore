package org.example.ebookstore.repository;

import org.example.ebookstore.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    Page<Order> findByUserId(Integer userId, Pageable pageable);
    List<Order> findByUserId(Integer userId);

    List<Order> getOrdersByUserIdAndOrderTimeBetween(Integer userId, LocalDateTime start, LocalDateTime end);

    List<Order> getOrdersByOrderTimeBetween(LocalDateTime start, LocalDateTime end);

    Page<Order> getOrdersByUserIdAndOrderTimeBetween(Integer userId, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);

    //Page<Order> findAll(Pageable pageable);
}
