package org.example.ebookstore.dao.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.example.ebookstore.dao.OrderDao;
import org.example.ebookstore.entity.*;
import org.example.ebookstore.repository.BookRepository;
import org.example.ebookstore.repository.OrderItemRepository;
import org.example.ebookstore.repository.OrderRepository;
import org.example.ebookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Order> selectByUserId(Integer userId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "orderId");
        return orderRepository.findByUserId(userId, sort);
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> returnValue = orderRepository.findAll();
        returnValue.sort(((o1, o2) -> Integer.compare(o2.getOrderId(), o1.getOrderId())));
        return returnValue;
    }

    @Override
    public void insertOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Statistics getStatistics(Integer userId, Integer time) {
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start;
        switch (time) {
            case 0:
                start = end.minusMonths(1);
                break;
            case 1:
                start = end.minusWeeks(1);
                break;
            case 2:
                start = end.minusDays(2);
                break;
            default:
                return null;
        }
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
    public List<UserListItem> getUserList(Integer time) {
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start;
        switch (time) {
            case 0:
                start = end.minusMonths(1);
                break;
            case 1:
                start = end.minusWeeks(1);
                break;
            case 2:
                start = end.minusDays(2);
                break;
            default:
                return null;
        }
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
        userListItems.sort((o1, o2) -> Integer.compare(o2.getMoneyCount(), o1.getMoneyCount()));
        return userListItems;
    }

    @Override
    public List<OrderItem> getBookList(Integer time) {
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start;
        switch (time) {
            case 0:
                start = end.minusMonths(1);
                break;
            case 1:
                start = end.minusWeeks(1);
                break;
            case 2:
                start = end.minusDays(2);
                break;
            default:
                return null;
        }
        List<Order> orderIdList = orderRepository.getOrderList(start, end);
        List<OrderItem> orderStatistics = new ArrayList<>();
        for (Order order : orderIdList) {
            for (OrderItem orderItem : order.getOrderItems()) {
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
        return orderStatistics;
    }

    @Override
    public List<Order> getSelectedOrders(Integer userId, LocalDate startTime, LocalDate endTime, String title) {
        if (title == "") {
            return orderRepository.getSelectedOrders(userId, startTime.atTime(0, 0, 0), endTime.atTime(23, 59, 59));
        } else {
            List<Order> orderList = orderRepository.getSelectedOrders(userId, startTime.atTime(0, 0, 0), endTime.atTime(23, 59, 59));
            List<Book> bookList = bookRepository.findByTitleContaining(title);
            List<Order> returnValue = new ArrayList<>();
            for (Order order : orderList) {
                boolean found = false;
                for (OrderItem orderItem : order.getOrderItems()) {
                    for (Book book : bookList) {
                        if (book.getBookId() == orderItem.getBookId()) {
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        break;
                    }
                }
                if (found) {
                    returnValue.add(order);
                }
            }
            returnValue.sort((o1, o2) -> Integer.compare(o2.getOrderId(), o1.getOrderId()));
            return returnValue;
        }
    }

    @Override
    public List<Order> getAllSelectedOrders(LocalDate startTime, LocalDate endTime, String title) {
        if (title == "") {
            return orderRepository.getAllSelectedOrders(startTime.atTime(0, 0, 0), endTime.atTime(23, 59, 59));
        } else {
            List<Order> orderList = orderRepository.getAllSelectedOrders(startTime.atTime(0, 0, 0), endTime.atTime(23, 59, 59));
            List<Book> bookList = bookRepository.findByTitleContaining(title);
            List<Order> returnValue = new ArrayList<>();
            for (Order order : orderList) {
                boolean found = false;
                for (OrderItem orderItem : order.getOrderItems()) {
                    for (Book book : bookList) {
                        if (book.getBookId() == orderItem.getBookId()) {
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        break;
                    }
                }
                if (found) {
                    returnValue.add(order);
                }
            }
            returnValue.sort((o1, o2) -> Integer.compare(o2.getOrderId(), o1.getOrderId()));
            return returnValue;
        }
    }

    @Override
    public List<Order> getOrdersByTitle(Integer userId, String title) {
        Sort sort = Sort.by(Sort.Direction.DESC, "orderId");
        List<Order> orderList = orderRepository.findByUserId(userId, sort);
        List<Book> bookList = bookRepository.findByTitleContaining(title);
        List<Order> returnValue = new ArrayList<>();
        for (Order order : orderList) {
            boolean found = false;
            for (OrderItem orderItem : order.getOrderItems()) {
                for (Book book : bookList) {
                    if (book.getBookId() == orderItem.getBookId()) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }
            if (found) {
                returnValue.add(order);
            }
        }
        returnValue.sort((o1, o2) -> Integer.compare(o2.getOrderId(), o1.getOrderId()));
        return returnValue;
    }

    @Override
    public List<Order> getAllOrdersByTitle(String title) {
        List<Order> orderList = orderRepository.findAll();
        List<Book> bookList = bookRepository.findByTitleContaining(title);
        List<Order> returnValue = new ArrayList<>();
        for (Order order : orderList) {
            boolean found = false;
            for (OrderItem orderItem : order.getOrderItems()) {
                for (Book book : bookList) {
                    if (book.getBookId() == orderItem.getBookId()) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }
            if (found) {
                returnValue.add(order);
            }
        }
        returnValue.sort((o1, o2) -> Integer.compare(o2.getOrderId(), o1.getOrderId()));
        return returnValue;
    }
}
