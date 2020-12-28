package com.msg.producer.service;

import com.msg.producer.configuration.RabbitConfiguration;
import com.msg.producer.domain.dto.RabbitMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private final AmqpTemplate template;

    public void pushToEmail(RabbitMessageDto msgBody) {

        log.info("Push to email queue");
//        template.convertAndSend(RabbitConfiguration.TOPIC_EXCHANGE, RabbitConfiguration.ROUTE_EMAIL_QUEUE, "msg to email");
        template.convertAndSend(RabbitConfiguration.TOPIC_EXCHANGE, RabbitConfiguration.ROUTE_EMAIL_QUEUE, msgBody);
    }

    public void pushToSlack(RabbitMessageDto msgBody) {

        log.info("Push to slack queue");
//        template.convertAndSend(RabbitConfiguration.TOPIC_EXCHANGE, RabbitConfiguration.ROUTE_SLACK_QUEUE, "msg to slack");
        template.convertAndSend(RabbitConfiguration.TOPIC_EXCHANGE, RabbitConfiguration.ROUTE_SLACK_QUEUE, msgBody);
    }

    public void pushToSms(RabbitMessageDto msgBody) {

        log.info("Push to sms gateway queue");
//        template.convertAndSend(RabbitConfiguration.TOPIC_EXCHANGE, RabbitConfiguration.ROUTE_SMS_QUEUE, "msg to sms");
        template.convertAndSend(RabbitConfiguration.TOPIC_EXCHANGE, RabbitConfiguration.ROUTE_SMS_QUEUE, msgBody);
    }

    public void pushToAll(RabbitMessageDto msgBody) {

        log.info("Push to all queues");
//        template.convertAndSend(RabbitConfiguration.FANOUT_EXCHANGE, "", "msg to all");
        template.convertAndSend(RabbitConfiguration.FANOUT_EXCHANGE, "", msgBody);
    }
}
