package org.example.ebookstore.listener;

import lombok.extern.slf4j.Slf4j;
import org.example.ebookstore.entity.Result;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderResultListener {
    @KafkaListener(topics = "Order-Result",groupId = "orderresult-group")
    @SendTo("/topic/order")
    public Result OrderBookListener(final String resultString) {
        log.info("OrderResult:"+resultString);
        return Result.success(resultString);
    }
}
