package org.example.ebookstore.listener;

import lombok.extern.slf4j.Slf4j;
import org.example.ebookstore.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import static java.lang.Thread.sleep;

@Slf4j
@Component
public class OrderResultListener {
    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "Order-Result", groupId = "orderresult-group")
    //@SendTo("/topic/order") 使用sendto会被kafka拦截
    public void OrderBookListener(final String resultString) throws Exception {
        String[] Info = resultString.split("_");
        Integer userId = Integer.parseInt(Info[0]);
        String result = Info[1];
        sleep(1000);
        messagingTemplate.convertAndSendToUser(userId.toString(),"/order" , Result.success(result));
    }
}
