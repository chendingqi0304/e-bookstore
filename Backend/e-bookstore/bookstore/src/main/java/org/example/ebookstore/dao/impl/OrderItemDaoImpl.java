package org.example.ebookstore.dao.impl;

import org.example.ebookstore.dao.OrderItemDao;
import org.example.ebookstore.entity.OrderItem;
import org.example.ebookstore.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class OrderItemDaoImpl implements OrderItemDao {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
        //int a=10/0;
    }
}
