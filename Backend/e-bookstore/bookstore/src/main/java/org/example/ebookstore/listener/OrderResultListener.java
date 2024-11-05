package org.example.ebookstore.listener;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.example.ebookstore.WebSocket.WebSocketServer;
import org.example.ebookstore.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class OrderResultListener {
    @Autowired
    private WebSocketServer ws;

    @KafkaListener(topics = "Order-Result", groupId = "orderresult-group")
    //@SendTo("/topic/order") 使用sendto会被kafka拦截
    public void OrderBookListener(final String resultString) throws Exception {
        Gson gson=new Gson();
        String[] Info = resultString.split("_");//Info[0]=userId,Info[1]=result
        ws.sendMessageToUser(Info[0], gson.toJson(Result.success(Info[1])));
    }
}
