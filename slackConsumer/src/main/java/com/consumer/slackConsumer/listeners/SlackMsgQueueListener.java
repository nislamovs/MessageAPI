package com.consumer.slackConsumer.listeners;


import com.consumer.slackConsumer.domain.dto.SlackPayload;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

//@RabbitListener(queues = "msg.slack")
public class SlackMsgQueueListener {

//    @RabbitHandler
    public void receive(SlackPayload payload) {
        System.out.println(" [x] Received '" + payload.toString() + "'");
    }
}
