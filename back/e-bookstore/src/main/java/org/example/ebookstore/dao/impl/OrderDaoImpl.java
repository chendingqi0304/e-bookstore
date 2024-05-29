package org.example.ebookstore.dao.impl;

import org.example.ebookstore.dao.OrderDao;
import org.example.ebookstore.entity.Order;
import org.example.ebookstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> selectByUserId(Integer userId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "orderId");
        return orderRepository.findByUserId(userId,sort);
    }

    @Override
    public void insertOrder(Order order) {
        orderRepository.save(order);
    }
}
