package org.example.ebookstore.listener;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.example.ebookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@Component
public class OrderListener {
    @Autowired
    OrderService orderService;


    @KafkaListener(topics = "Order-BookId",groupId = "order-bookId-group")
    public void OrderBookListener(final String OrderInfo) {
        String[] Info = OrderInfo.split("-");
        Integer bookId = Integer.parseInt(Info[0]);
        Integer userId = Integer.parseInt(Info[1]);
        Integer number = Integer.parseInt(Info[2]);
        orderService.addOrderByBookId(bookId, userId, number);
    }

//    @KafkaListener(topics = "Order-CartId")
//    public void OrderCartListener(final String OrderInfo) {
//
//    }
}
