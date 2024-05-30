package org.example.ebookstore.service.impl;

import jakarta.transaction.Transactional;
import org.example.ebookstore.dao.BookDao;
import org.example.ebookstore.dao.CartDao;
import org.example.ebookstore.dao.OrderDao;
import org.example.ebookstore.dao.OrderItemDao;
import org.example.ebookstore.entity.*;
import org.example.ebookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public List<Order> getOrders(Integer userId) {
        return orderDao.selectByUserId(userId);
    }

    @Override
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
        orderItem.setPrice(book.getPrice() * number);
        orderItem.setName(book.getTitle());
        orderItemDao.addOrderItem(orderItem);
    }

    @Override
    public void addOrderByCartIds(List<Integer> cartIds, Integer userId) {
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderTime(LocalDateTime.now());
        orderDao.insertOrder(order);

        for (Integer cartId : cartIds) {
            Cart cart = cartDao.selectBycartId(cartId);
            Book book = cart.getBook();
            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setNumber(cart.getNumber());;
            orderItem.setName(book.getTitle());
            orderItem.setBookId(book.getBookId());
            orderItem.setPrice(book.getPrice() * cart.getNumber());

            book.setRest(book.getRest() - orderItem.getNumber());
            cartDao.deleteBycartId(cartId);
            orderItemDao.addOrderItem(orderItem);
            bookDao.insert(book);
        }
    }
    @Override
    public List<UserListItem> getUserList(Integer time){
        return orderDao.getUserList(time);
    }

    @Override
    public List<OrderItem> getBookList(Integer time){return orderDao.getBookList(time);}
}
