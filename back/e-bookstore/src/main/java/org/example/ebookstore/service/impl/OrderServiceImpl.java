package org.example.ebookstore.service.impl;

import jakarta.transaction.Transactional;
import org.example.ebookstore.dao.BookDao;
import org.example.ebookstore.dao.CartDao;
import org.example.ebookstore.dao.OrderDao;
import org.example.ebookstore.dao.OrderItemDao;
import org.example.ebookstore.entity.Book;
import org.example.ebookstore.entity.Cart;
import org.example.ebookstore.entity.Order;
import org.example.ebookstore.entity.OrderItem;
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
        Book book = bookDao.getBookById(userId);
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
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setNumber(cart.getNumber());
            orderItem.setName(cart.getBook().getTitle());
            orderItem.setBookId(cart.getBook().getBookId());
            orderItem.setPrice(cart.getBook().getPrice() * cart.getNumber());
            cartDao.deleteBycartId(cartId);
            orderItemDao.addOrderItem(orderItem);
        }
    }
}
