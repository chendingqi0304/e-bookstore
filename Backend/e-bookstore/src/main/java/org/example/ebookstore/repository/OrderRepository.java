package org.example.ebookstore.repository;

import org.example.ebookstore.entity.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findByUserId(Integer userId, Sort sort);

    @Query("SELECT o FROM Order o where o.userId=:userId AND o.orderTime between :start and :end" )
    List<Order> getUserStatistics(Integer userId, LocalDateTime start, LocalDateTime end);

    @Query("SELECT o FROM Order o where o.orderTime between :start and :end ")
    List<Order> getOrderList(LocalDateTime start, LocalDateTime end);

    @Query("SELECT o FROM Order o where o.userId=:userId and o.orderTime between :startTime and :endTime")
    List<Order> getSelectedOrders(Integer userId, LocalDateTime startTime, LocalDateTime endTime);

    @Query("SELECT o FROM Order o where o.orderTime between :startTime and :endTime")
    List<Order> getAllSelectedOrders(LocalDateTime startTime, LocalDateTime endTime);
}
