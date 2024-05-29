package org.example.ebookstore.dao.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.example.ebookstore.dao.OrderDao;
import org.example.ebookstore.entity.Order;
import org.example.ebookstore.entity.OrderItem;
import org.example.ebookstore.entity.Statistics;
import org.example.ebookstore.entity.UserListItem;
import org.example.ebookstore.repository.OrderItemRepository;
import org.example.ebookstore.repository.OrderRepository;
import org.example.ebookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Order> selectByUserId(Integer userId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "orderId");
        return orderRepository.findByUserId(userId, sort);
    }

    @Override
    public void insertOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Statistics getStatistics(Integer userId) {
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = end.minusMonths(1);
        List<Order> orderIdList = orderRepository.getUserStatistics(userId, start, end);
        List<OrderItem> orderStatistics = new ArrayList<>();
        Integer bookCount = 0;
        Integer moneyCount = 0;
        for (Order order : orderIdList) {
            for (OrderItem orderItem : order.getOrderItems()) {
                bookCount += orderItem.getNumber();
                moneyCount += orderItem.getPrice();
                boolean found = false;
                for (OrderItem statistic : orderStatistics) {
                    if (statistic.getBookId() == orderItem.getBookId()) {
                        statistic.setNumber(statistic.getNumber() + orderItem.getNumber());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    OrderItem newOrderItem = new OrderItem();
                    newOrderItem.setName(orderItem.getName());
                    newOrderItem.setBookId(orderItem.getBookId());
                    newOrderItem.setNumber(orderItem.getNumber());
                    orderStatistics.add(newOrderItem);
                }
            }
        }

        // 使用 Comparator 对 List<OrderItem> 按 number 字段降序排序
        orderStatistics.sort((o1, o2) -> {
            return Integer.compare(o2.getNumber(), o1.getNumber()); // 降序排序
        });
        Statistics statistics = new Statistics();
        statistics.setBookCount(bookCount);
        statistics.setMoneyCount(moneyCount);
        statistics.setOrderItems(orderStatistics);
        return statistics;
    }

    @Override
    public List<UserListItem> getUserList() {
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = end.minusMonths(1);
        List<Order> orderList = orderRepository.getOrderList(start, end);
        List<UserListItem> userListItems = new ArrayList<>();
        for (Order order : orderList) {
            boolean found = false;
            for (UserListItem userListItem : userListItems) {
                if (userListItem.getUserId() == order.getUserId()) {
                    for (OrderItem orderItem : order.getOrderItems()) {
                        userListItem.setMoneyCount(userListItem.getMoneyCount() + orderItem.getPrice());
                    }
                    found = true;
                    break;
                }
            }
            if (!found) {
                UserListItem newUserListItem = new UserListItem();
                newUserListItem.setUserId(order.getUserId());
                newUserListItem.setUsername(userRepository.findByUserId(order.getUserId()).getUsername());
                newUserListItem.setMoneyCount(0);
                for (OrderItem orderItem : order.getOrderItems()) {
                    newUserListItem.setMoneyCount(newUserListItem.getMoneyCount() + orderItem.getPrice());
                }
                userListItems.add(newUserListItem);
            }
        }
        return userListItems;
    }
}
