package org.example.ebookstore.service.impl;

import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.ebookstore.dao.BookDao;
import org.example.ebookstore.dao.CartDao;
import org.example.ebookstore.dao.OrderDao;
import org.example.ebookstore.dao.OrderItemDao;
import org.example.ebookstore.entity.*;
import org.example.ebookstore.service.ClientService;
import org.example.ebookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private CartDao cartDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private ClientService clientService;

    @Override
    public Page<Order> getOrders(Integer userId, Pageable pageable) {
        return orderDao.selectByUserId(userId, pageable);
    }

    @Override
    public Page<Order> getAllOrders(Pageable pageable) {
        return orderDao.getAllOrders(pageable);
    }

    @Override
    @Transactional
    public void addOrderByBookId(Integer bookId, Integer userId, Integer number) {
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderTime(LocalDateTime.now());
        orderDao.insertOrder(order);
        OrderItem orderItem = new OrderItem();
        Book book = bookDao.getBookById(bookId);
        book.setRest(book.getRest() - number);
        orderItem.setOrder(order);
        orderItem.setNumber(number);
        orderItem.setBookId(bookId);
        orderItem.setPrice(getBookPrice(book.getPrice(),number));

        orderItem.setName(book.getTitle());
        try {
            orderItemDao.addOrderItem(orderItem);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public boolean addOrderByCartIds(List<Integer> cartIds, Integer userId) {
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderTime(LocalDateTime.now());
        orderDao.insertOrder(order);
        for (Integer cartId : cartIds) {
            Cart cart = cartDao.selectBycartId(cartId);
            Book book = cart.getBook();
            if (book.getRest() < cart.getNumber()) {
                return false;
            }
        }
        for (Integer cartId : cartIds) {
            Cart cart = cartDao.selectBycartId(cartId);
            Book book = cart.getBook();
            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setNumber(cart.getNumber());
            ;
            orderItem.setName(book.getTitle());
            orderItem.setBookId(book.getBookId());

            orderItem.setPrice(getBookPrice(book.getPrice(),cart.getNumber()));

            book.setRest(book.getRest() - orderItem.getNumber());
            cartDao.deleteBycartId(cartId);
            orderItemDao.addOrderItem(orderItem);
            bookDao.insert(book);
        }
        return true;
    }

    @Override
    public List<UserListItem> getUserList(Integer time) {
        return orderDao.getUserList(time);
    }

    @Override
    public List<OrderItem> getBookList(Integer time) {
        return orderDao.getBookList(time);
    }

    @Override
    public Page<Order> getSelectedOrders(Integer userId, LocalDate startTime, LocalDate endTime, String title, Pageable pageable) {
        return orderDao.getSelectedOrders(userId, startTime, endTime, title, pageable);
    }

    @Override
    public Page<Order> getAllSelectedOrders(LocalDate startDate, LocalDate endDate, String title, Pageable pageable) {
        return orderDao.getAllSelectedOrders(startDate, endDate, title, pageable);
    }

    @Override
    public Page<Order> getOrdersByTitle(Integer userId, String title, Pageable pageable) {
        return orderDao.getOrdersByTitle(userId, title, pageable);
    }

    @Override
    public Page<Order> getAllOrdersByTitle(String title, Pageable pageable) {
        return orderDao.getAllOrdersByTitle(title, pageable);
    }

    private Integer getBookPrice(Integer price,Integer number){
        List<List<Integer>> array = new ArrayList<>();
        List<Integer> list;
        list = new ArrayList<>();
        list.add(price);
        list.add(number);
        array.add(list);
        List<Integer> result=clientService.getTotals(array.toString());
        return result.get(0);
    }
}
